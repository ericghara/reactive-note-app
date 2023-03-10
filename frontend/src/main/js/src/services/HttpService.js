import axios from "axios";
import UserService from "./UserService";

let root;

// not currently implementing method keys
const endpoints = {
    getItems : {relPath: "/item", method: 'GET'},
    deleteItem : {relPath: "/item", method: 'DELETE'},
    postItem : {relPath: "/item", method: 'POST'},
    putItem : {relPath: "/item", method: 'PUT'},
    postList : {relPath: "/list", method: 'POST'},
    putList : {relPath: "/list", method: 'PUT'},
    deleteList : {relPath: "/list", method: 'DELETE'},
    getLists :  {relPath: "/list", method: 'GET'}
}

const client = axios.create({timeout : 7500});

const configure = () => {
    root = getRoot();
    client.interceptors.request.use((config) => {
      if (UserService.isLoggedIn()) {
        const cb = () => {
          config.headers.Authorization = `Bearer ${UserService.getToken()}`;
          return Promise.resolve(config);
        };
        return UserService.updateToken(cb);
      }
      else {
        return Promise.resolve(config);
      }
    });
  };

const getClient = () => client

const getEndpoint = (name) => {
    if (endpoints[name] === undefined) {
        console.warn(`Unknown endpoint name: ${name}`)
        return ""
    }
    return root + endpoints[name].relPath
}

const getRoot = () => {
    let root = ""
    if (process.env.NODE_ENV === 'development' && process.env?.REACT_APP_ROOT) {
        // Change REACT_APP_ROOT env var to point to a different backend (watch CORS)
        console.log('Started in dev')
        root = process.env.REACT_APP_ROOT
        console.log(`REACT_APP_ROOT is ${root}`)
    }
    else {
        root = window.location.origin
    }
    return root + "/api"
}

const HttpService = {
    getClient: getClient,
    getEndpoint: getEndpoint,
    configure: configure
}

export default HttpService
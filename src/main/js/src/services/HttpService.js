import axios from "axios";
import UserService from "./UserService";

const root = "http://localhost:8080/api/"

// not currently implementing method keys
const endpoints = {
    getItems : {relPath: "item/", method: 'GET'},
    deleteItem : {relPath: "item/", method: 'DELETE'},
    postItem : {relPath: "item/", method: 'POST'},
    putItem : {relPath: "item/", method: 'PUT'},
    postList : {relPath: "list/", method: 'POST'},
    putList : {relPath: "list/", method: 'PUT'},
    deleteList : {relPath: "list/", method: 'DELETE'},
    getLists :  {relPath: "list/", method: 'GET'}
}

const client = axios.create({timeout : 2500});

const configure = () => {
    client.interceptors.request.use((config) => {
      if (UserService.isLoggedIn()) {
        const cb = () => {
          config.headers.Authorization = `Bearer ${UserService.getToken()}`;
          return Promise.resolve(config);
        };
        return UserService.updateToken(cb);
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

const HttpService = {
    getClient: getClient,
    getEndpoint: getEndpoint,
    configure: configure
}

export default HttpService
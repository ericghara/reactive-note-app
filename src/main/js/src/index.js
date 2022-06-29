import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import UserService from "./services/UserService"
import HttpService from "./services/HttpService"

const root = ReactDOM.createRoot(document.getElementById("root") );
UserService.initKeycloak(root);


root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);

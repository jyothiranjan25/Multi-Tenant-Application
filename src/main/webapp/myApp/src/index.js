import React from "react";
import ReactDOM from "react-dom/client";
import { StyledEngineProvider } from "@mui/material/styles";
import { BrowserRouter } from "react-router-dom";
import App from "./App.js";

const basename = process.env.REACT_APP_HOME_PATH_NAME || "/";


const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <StyledEngineProvider injectFirst>
      <BrowserRouter future={{
          v7_startTransition: true,
          v7_relativeSplatPath: true,
      }} basename={basename}>
        <App />
      </BrowserRouter>
    </StyledEngineProvider>
  </React.StrictMode>
);
// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals

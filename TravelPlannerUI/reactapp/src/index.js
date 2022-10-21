import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import './index.css';

import MainPage from "./MainPage";
import App from "./App";

ReactDOM.createRoot(document.getElementById("root")).render(
  <BrowserRouter basename="/">
    <Routes>
      <Route path="/" element={<App />} />
      <Route path="/MainPage" element={<MainPage />} />
    </Routes>
  </BrowserRouter>
);



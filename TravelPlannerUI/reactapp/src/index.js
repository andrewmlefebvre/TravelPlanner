import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import './index.css';

import MainPage from "./MainPage";
import App from "./App";
import Account from "./Account";

ReactDOM.createRoot(document.getElementById("root")).render(
  <BrowserRouter basename="/">
    <Routes>
      <Route path="/" element={<Account />} />
      <Route path="/Account" element={<Account />} />
      <Route path="/App" element={<App />} />
      <Route path="/MainPage" element={<MainPage />} />
    </Routes>
  </BrowserRouter>
);



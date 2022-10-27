import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import './index.css';

import LoginScreen from "./pages/LoginScreen";

ReactDOM.createRoot(document.getElementById("root")).render(
  <BrowserRouter basename="/">
    <Routes>
      <Route path="/" element={<LoginScreen />} />
      <Route path="/LoginScreen" element={<LoginScreen />} />
    </Routes>
  </BrowserRouter>
);



import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import './index.css';

import UserScreen from "./pages/UserScreen";

ReactDOM.createRoot(document.getElementById("root")).render(
  <BrowserRouter basename="/">
    <Routes>
      <Route path="/" element={<UserScreen />} />
      <Route path="/UserScreen" element={<UserScreen />} />
    </Routes>
  </BrowserRouter>
);



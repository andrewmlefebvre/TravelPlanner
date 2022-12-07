import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import './index.css';

import UserScreen from "./pages/UserScreen";
import TripScreen from "./pages/TripScreen";
import NewUser from "./pages/NewUser";
import Admin from "./pages/Admin";

ReactDOM.createRoot(document.getElementById("root")).render(
  <BrowserRouter basename="/">
    <Routes>
      <Route path="/" element={<UserScreen />} />
      <Route path="/UserScreen" element={<UserScreen />} />
      <Route path="/TripScreen" element={<TripScreen />} />
      <Route path="/NewUser" element={<NewUser />} />
      <Route path="/Admin" element={<Admin />} />
    </Routes>
  </BrowserRouter>
);



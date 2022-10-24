import React from "react";
import {Link } from "react-router-dom";
//import styled from "styled-components";
import './App.css';
import ToolBar from "./ToolBar";

//npm i react-router-dom
//npm i styled-components
function App() {
  return (
    <div>
      <ToolBar />
      <header className="App-header">
        <p>Travel Planner</p>
        <br />
        <Link to="/MainPage">
          <button type="button" className="noselect">Hello</button>
        </Link>
      </header>
    </div>
  ); 
}

export default App;



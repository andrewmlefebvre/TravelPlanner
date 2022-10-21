import React from "react";
import {Link } from "react-router-dom";
//import styled from "styled-components";
import './App.css';

//npm i react-router-dom
//npm i styled-components
function App() {
  return (
    <div className="App">
      <header className="App-header">
        <p>Travel Planner</p>
        <Link to="/MainPage">
          <button type="button">Start</button>
        </Link>
      </header>
    </div>
  );
}

export default App;



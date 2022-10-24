import React from "react";
import {Link } from "react-router-dom";
//import styled from "styled-components";
import './ToolBar.css';


//npm i react-router-dom
//npm i styled-components
//npm i nav-bar
function ToolBar(){
        return (
            <div className='tool'>
                <ul >
                    <li>
                    <Link to="/App">App</Link>
                    </li>
                    <li>
                    <Link to="/MainPage">MainPage</Link>
                    </li>
                    <li>
                    <Link to="/MainPage">MainPage</Link>
                    </li>
                </ul>
            </div>
          )
}

export default ToolBar;
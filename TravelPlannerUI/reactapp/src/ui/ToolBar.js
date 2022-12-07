import React from "react";
import {Link } from "react-router-dom";
//import styled from "styled-components";
import './ToolBar.css';


//npm i react-router-dom
//npm i styled-components
//npm i nav-bar
function ToolBar(){
        return (
            <div className='tb'>
                <ul >
                    <li>
                    <Link to="/UserScreen">My Profile</Link>
                    </li>
                    <li>
                    <Link to="/TripScreen">Trip Creator</Link>
                    </li>
                </ul>
            </div>
          )
}

export default ToolBar;
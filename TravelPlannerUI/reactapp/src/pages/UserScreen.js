import React from "react";

import ToolBar from "../ui/ToolBar";

import './UserScreen.css';
import LogInForm from "./LogInForm";
import { useState } from 'react';




function UserScreen(){
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [user, setUser] = useState(null);
    const [trips, setTrips]  = useState(null);

    async function loginHandler(loginInfo){
        var response = await fetch("http://localhost:8080/api/get/login/".concat(loginInfo.userName).concat("/").concat(loginInfo.password), {method:'GET'}, {headers:{'Content-Type': 'application/json', 'Access-Control-Allow-Origin':'*'}});
        var json = await response.json();
        if(response.ok && json[0] != null){
            //alert(JSON.stringify(json));
            setUser(json);
            setIsLoggedIn(true);
            localStorage.setItem("userInformation", {"userName": loginInfo.userName, "password": loginInfo.password});
            tripListHandler();
        }else{
            alert("Username or password is incorrect ");
        }
    }

    async function tripListHandler(){
        alert("http://localhost:8080/api/get/tripsbyuser/".concat(user.ID));
        var response = await fetch("http://localhost:8080/api/get/tripsbyuser/".concat(user.ID), {method:'GET'}, {headers:{'Content-Type': 'application/json', 'Access-Control-Allow-Origin':'*'}});
        var json = await response.json();
        if(response.ok && json[0] != null){
            //alert(JSON.stringify(json));
            setTrips(json);
            alert(trips);
        }
        alert("Shit");
    }

    localStorage.clear();
    if(!isLoggedIn){
        return(
            <div>
                <ToolBar />
                <LogInForm onLogin={loginHandler}/>
            </div>
        );
    }else{
        return(
            <div>
                <ToolBar />
                <div className='split topleft'>
                    <div className='quarterscreen'>
                        <h2>Friends</h2>
                    </div>
                </div>
                <div className='split bottomleft'>
                    <div className='quarterscreen'>
                        <h2>User Information</h2>
                    </div>
                </div>
                <div className='split right'>
                    <div className='halfscreen'>
                        <h2>Trips</h2>
                    </div>
                </div>
            </div>
        );
    }
}

export default UserScreen;
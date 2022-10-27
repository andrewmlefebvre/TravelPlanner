import React from "react";

import ToolBar from "../ui/ToolBar";

import './UserScreen.css';
import LogInForm from "./LogInForm";
import { useState, useRef } from 'react';
import BlurCard from  '../ui/BlurCard';




function UserScreen(){
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [user, setUser] = useState(null);

    async function loginHandler(loginInfo){
        var response = await fetch("http://localhost:8080/api/get/login/".concat(loginInfo.userName).concat("/").concat(loginInfo.password), {method:'GET'}, {headers:{'Content-Type': 'application/json', 'Access-Control-Allow-Origin':'*'}});
        var json = await response.json();
        if(response.ok && json[0] != null){
            //alert(JSON.stringify(json));
            setUser(json);
            setIsLoggedIn(true);
            localStorage.setItem("userInformation", {"userName": loginInfo.userName, "password": loginInfo.password});
        }else{
            alert("Username or password is incorrect ");
        }
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
                <div className='split left'>
                    <div className='halfscreen'>
                        <h1>Friends</h1>
                    </div>
                    
                </div>
                <div className='split right'>
                    <div className='halfscreen'>
                        <h1>Trips</h1>
                    </div>
                </div>
            </div>
        );
    }
}

export default UserScreen;
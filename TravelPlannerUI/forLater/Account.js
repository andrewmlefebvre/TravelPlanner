


import React from "react";
import { useState, useRef } from 'react';
import './Account.css';
import ToolBar from "./ui/ToolBar";


function Account(){
    const [userChoosen, setUserChoosen] = useState();

    const userNameInputRef = useRef();

    var returnedUser;

    async function userLookUp(){
        var response = await fetch("http://localhost:8080/api/get/userswithusername/".concat(userNameInputRef.current.value), {method:'GET'}, {headers:{'Content-Type': 'application/json', 'Access-Control-Allow-Origin':'*'}});
        var json = await response.json();
        if(response.ok){
            returnedUser = json;
            setUserChoosen(true);
        }else{
            alert("No user found with username: ".concat(userNameInputRef.current.value));
        }
    }


    return (
        <div>
            <ToolBar />
            <input type="text" id='userInput' ref={userNameInputRef}/>
            <div>
                <button className="button" onClick={userLookUp}></button>
            </div>
            
        </div>
    );

} export default Account;


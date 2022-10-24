import React from "react";
import './Account.css';
import ToolBar from "./ToolBar";


export default class Account extends React.Component{

    #userName;

    async userLookUp(userName_) {
        //alert("http://localhost:8080/api/get/userswithusername/".concat(this.userName));
        const response = await fetch("http://localhost:8080/api/get/userswithusername/".concat(userName_), {method:'GET'}, {headers:{'Content-Type': 'application/json', 'Access-Control-Allow-Origin':'*'}});
        const json = await response.json();
        alert(json.count);
    
    }


    setUserName = () => {
        this.userName = document.getElementById("userInput").value;
    }


    render() {
        if(localStorage.getItem("userName") == null){
            return (
            <div>
                <ToolBar />
                <input 
          type="text" 
          value={this.userName}
          id='userInput'
          onChange={this.setUserName}
        />
            <div>
                <button className="button" onClick={() => this.userLookUp(this.userName)}></button>
            </div>
            </div>
            );
        }else{
            //TODO Account Information
        }
    }



}   
import React from "react";
import './Account.css';
import ToolBar from "./ToolBar";


export default class Account extends React.Component{

    #userNameLookUp

    constructor(){
        super();
        this.userNameLookUp = {};
    }

    async userLookUp() {
        //alert("http://localhost:8080/api/get/userswithusername/".concat(this.userName));
        const response = await fetch("http://localhost:8080/api/get/userswithusername/".concat(document.getElementById("userInput").value), {method:'GET'}, {headers:{'Content-Type': 'application/json', 'Access-Control-Allow-Origin':'*'}});
        const json = await response.json();
        alert(JSON.stringify(json));

        this.userNameLookUp = json;
    
    }



    render() {
        return (
            <div>
                <ToolBar />
                <input type="text" value={this.userName} id='userInput'/>
                <div>
                    <button className="button" onClick={() => this.userLookUp()}></button>
                </div>
                
            </div>
        );
    }



}   
import React from "react";

import ToolBar from "../ui/ToolBar";


import './UserScreen.css';
import LogInForm from "./LogInForm";
import { useState } from 'react';

import { Table } from "semantic-ui-react"; 
//npm install semantic-ui-react 
//npm install semantic-ui-css




function UserScreen(){
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [myTrips, setMyTrips] = useState([])
    let cUser = {};

    async function loginHandler(loginInfo){
        var response = await fetch("http://localhost:8080/api/get/login/".concat(loginInfo.userName).concat("/").concat(loginInfo.password), {method:'GET'}, {headers:{'Content-Type': 'application/json', 'Access-Control-Allow-Origin':'*'}});
        var json = await response.json();
        if(response.ok && json[0] != null){
            cUser = json
            setIsLoggedIn(true);
            localStorage.setItem("userInformation", {"userName": loginInfo.userName, "password": loginInfo.password, "id": json.id});
            tripListHandler();
        }else{
            alert("Username or password is incorrect ");
        }
    }
//[{"firstName":"Test1","lastName":"Lefebvre","dob":"3899-06-03","address":"21 Test Ave Kingston RI 02882","userName":"User2","password":"apple","id":1}]

    async function tripListHandler(){
        //alert("http://localhost:8080/api/get/tripsbyuser/".concat(cUser[0].id));
        var response = await fetch("http://localhost:8080/api/get/tripsbyuser/".concat(cUser[0].id), {method:'GET'}, {headers:{'Content-Type': 'application/json', 'Access-Control-Allow-Origin':'*'}});
        var json = await response.json();
        if(response.ok && json[0] != null){
            for(let i = 0; i<json.length; i++){
                let t = json
                setMyTrips(myTrips => [...myTrips, t[i]])
            }
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
                        <Table className='userTable'>
                            <Table.Header>
                                <Table.Row>
                                    <Table.HeaderCell>Name</Table.HeaderCell>
                                    <Table.HeaderCell>Start Date</Table.HeaderCell>
                                    <Table.HeaderCell>End Date</Table.HeaderCell>
                                </Table.Row>
                            </Table.Header>
                            <Table.Body>
                                {myTrips?.map(el => {
                                    return (
                                    <Table.Row key={el.id}>
                                        <Table.Cell>{el.name}</Table.Cell>
                                        <Table.Cell>{el.startDate}</Table.Cell>
                                        <Table.Cell>{el.endDate}</Table.Cell>
                                    </Table.Row>
                                    );
                                })}
                            </Table.Body>
                        </Table>
                    </div>
                </div>
            </div>
        );
    }
}

export default UserScreen;
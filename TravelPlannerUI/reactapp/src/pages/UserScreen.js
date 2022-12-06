import React from "react";

import ToolBar from "../ui/ToolBar";


import './UserScreen.css';
import LogInForm from "./LogInForm";
import { useState, useRef } from 'react';

import { Table } from "semantic-ui-react"; 
//npm install semantic-ui-react 
//npm install semantic-ui-css




function UserScreen(){
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [myTrips, setMyTrips] = useState([])
    const [myFriends, setMyFriends] = useState([])
    const [loadedUser, setLoadedUser] = useState(false);

    const userNameInputRef = useRef();
    const firstNameInputRef = useRef();
    const lastNameInputRef = useRef();
    const birthDayInputRef = useRef();

    const [user, setUser] = useState([]);

    const delay = ms => new Promise(
        resolve => setTimeout(resolve, ms)
      );

    function logOut(){
        setIsLoggedIn(false);
        setLoadedUser(false);
        setMyTrips([]);
        setMyFriends([]);
        localStorage.setItem("userInformation", null);
    }

    async function loginHandler(loginInfo){
        var response = await fetch("http://localhost:8080/api/get/login/".concat(loginInfo.userName).concat("/").concat(loginInfo.password), {method:'GET'}, {headers:{'Content-Type': 'application/json', 'Access-Control-Allow-Origin':'*'}});
        var json = await response.json();
        if(response.ok && json[0] != null){
            const lUser = json;
            setUser([]);
            setUser(user => [...user, json[0]]);
            await delay(100);
            
            setIsLoggedIn(true);
            localStorage.setItem("userInformation", JSON.stringify(lUser));
            //localStorage.setItem("test", "testValue");
            tripListHandler(lUser);
            friendListHandler(lUser);

        }else{
            alert("Username or password is incorrect ");
        }
    }
//[{"firstName":"Test1","lastName":"Lefebvre","dob":"3899-06-03","address":"21 Test Ave Kingston RI 02882","userName":"User2","password":"apple","id":1}]

    async function tripListHandler(u){
        //alert(user);
        //alert("http://localhost:8080/api/get/tripsbyuser/".concat(user[0].id));
        var response = await fetch("http://localhost:8080/api/get/tripsbyuser/".concat(u[0].id), {method:'GET'}, {headers:{'Content-Type': 'application/json', 'Access-Control-Allow-Origin':'*'}});
        var json = await response.json();
        setMyTrips([]);
        if(response.ok && json[0] != null){
            for(let i = 0; i<json.length; i++){
                let t = json
                setMyTrips(myTrips => [...myTrips, t[i]])
            }
        }
        
    }

    async function friendListHandler(u){
        //alert("http://localhost:8080/api/get/tripsbyuser/".concat(cUser[0].id));
        var response = await fetch("http://localhost:8080/api/get/friendsbyuser/".concat(u[0].id), {method:'GET'}, {headers:{'Content-Type': 'application/json', 'Access-Control-Allow-Origin':'*'}});
        var json = await response.json();
        setMyFriends([]);
        if(response.ok && json[0] != null){
            for(let i = 0; i<json.length; i++){
                let f = json
                setMyFriends(myFriends => [...myFriends, f[i]])
            }
        }
    }

    async function updateUserHandler(userName, first, last, bDay){
        //alert(user[0]);
        //alert("http://localhost:8080/api/update/user/".concat(user[0].id).concat("/").concat(userName).concat("/").concat(first).concat("/").concat(last).concat("/").concat(bDay));
        var response = await fetch("http://localhost:8080/api/update/user/".concat(user[0].id).concat("/").concat(userName).concat("/").concat(first).concat("/").concat(last).concat("/").concat(bDay), {method:'PUT'}, {headers:{'Content-Type': 'application/json', 'Access-Control-Allow-Origin':'*'}});
        if(response.ok){
            tripListHandler(user);
            friendListHandler(user);
        }
    }
    

    function userSubmitHandler(event){
        event.preventDefault();

        updateUserHandler(userNameInputRef.current.value, firstNameInputRef.current.value, lastNameInputRef.current.value, birthDayInputRef.current.value);
        alert("Information Updated");
    }

    const [checkedLogIn, setCheckedLogIn] = useState(false);
    if(localStorage.getItem("userInformation") != null && !checkedLogIn){
        setIsLoggedIn(true);
        setCheckedLogIn(true);
    }
    
    if(!isLoggedIn && !loadedUser){
        return(
            <div  >
                <ToolBar />
                <div className='centered'>                
                    <LogInForm onLogin={loginHandler}/>
                    <button type='button'>New User</button>
                </div>
            </div>
        );
    }else{
        if(!loadedUser){
            setUser([]);
            setUser(setUser(user => [...user, JSON.parse(localStorage.getItem("userInformation"))]));
            setIsLoggedIn(true);
            setLoadedUser(true);
            tripListHandler(JSON.parse(localStorage.getItem("userInformation")));
            friendListHandler(JSON.parse(localStorage.getItem("userInformation")));
        }
        return(
            <div className={"uBase"}>
                <ToolBar />
                <div className='split topleft'>
                    <div className='quarterscreen'>
                        <h2>Friends</h2>
                        <Table className='userTable'>
                            <Table.Header>
                                <Table.Row>
                                    <Table.HeaderCell>First Name</Table.HeaderCell>
                                    <Table.HeaderCell>Last Name</Table.HeaderCell>
                                </Table.Row>
                            </Table.Header>
                            <Table.Body>
                                {myFriends?.map(el => {
                                    return (
                                    <Table.Row key={el.id}>
                                        <Table.Cell>{el.firstName}</Table.Cell>
                                        <Table.Cell>{el.lastName}</Table.Cell>
                                    </Table.Row>
                                    );
                                })}
                            </Table.Body>
                        </Table>
                    </div>
                </div>
                <div className='split bottomleft'>
                    <div className='quarterscreen'>
                        <h2>User Information</h2>
                        <form onSubmit={userSubmitHandler}>
                            <div>
                                <div>
                                    <label htmlFor='userName'>UserName: </label>
                                    <input type='text' id='userName' ref={userNameInputRef}></input>
                                </div>
                                <div>
                                    <label htmlFor='firstName'>First Name: </label>
                                    <input type='text' id='firstName' ref={firstNameInputRef}></input>
                                </div>
                                <div>
                                    <label htmlFor='lastName'>Last Name </label>
                                    <input type='text' id='lastName' ref={lastNameInputRef}></input>
                                </div>
                                <div>
                                    <label htmlFor='birthDay'>Birthday: </label>
                                    <input type='text' id='birthDay' ref={birthDayInputRef}></input>
                                </div>
                                <div>
                                    <button>Update Information</button>
                                </div>    
                            </div>
                        </form>
                        <button onClick={logOut}>LogOut</button>
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
                                        <Table.Cell><button onClick={event =>  {window.location.href='/TripScreen'; localStorage.setItem("trip", JSON.stringify(el));}}>Open</button></Table.Cell>
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
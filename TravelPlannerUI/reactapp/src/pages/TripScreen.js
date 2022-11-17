import React from "react";

import ToolBar from "../ui/ToolBar";
import './UserScreen.css';
import { useState, useRef } from 'react';

import { Table } from "semantic-ui-react"; 
import TripStartForm from "./TripStartForm";

function TripScreen(){
    const [tripStarted, setTripStarted] = useState(false);
    const [tripEvents, setTripEvents] = useState([]);

    async function tripStartHandler(TripInfo){
        //alert("http://localhost:8080/api/create/trip/".concat(TripInfo.name).concat("/").concat(TripInfo.start).concat("/").concat(TripInfo.end).concat("/").concat(JSON.parse(localStorage.getItem("userInformation"))[0].id));
        var response = await fetch("http://localhost:8080/api/create/trip/".concat(TripInfo.name).concat("/").concat(TripInfo.start).concat("/").concat(TripInfo.end).concat("/").concat(JSON.parse(localStorage.getItem("userInformation"))[0].id), {method:'POST'}, {headers:{'Content-Type': 'application/json', 'Access-Control-Allow-Origin':'*'}});
        var json = await response.json();
        if(response.ok && json != null){
            alert("Trip Created")
            setTripStarted(true);
        }else{
            alert("Trip Creation Failed");
        }
    }








    if(!tripStarted){
        return(
            <div>
                <ToolBar />
                <TripStartForm onStart={tripStartHandler}/>
            </div>
        );
    }else{
        return(
            <div>
                <ToolBar />
            </div>
        );
    }

}
export default TripScreen;
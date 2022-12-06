import React from "react";

import ToolBar from "../ui/ToolBar";

import './TripScreen.css';
import { useState } from 'react';

//import { Table } from "semantic-ui-react"; 
import TripStartForm from "./TripStartForm";
import NewEventForm from "./NewEventForm";

import BlurCard from "../ui/BlurCard";

import downArrow from "../downarrow.png";
import sunny from "../sunny.png";
import partly from "../partly.png";
import rainy from "../rainy.png";
import windy from "../windy.png";

function TripScreen(){
    const [tripStarted, setTripStarted] = useState(false);
    const [tripEvents, setTripEvents] = useState([]);
    const [thisTrip, setThisTrip] = useState([]);

    async function tripStartHandler(TripInfo){
        //alert("http://localhost:8080/api/create/trip/".concat(TripInfo.name).concat("/").concat(TripInfo.start).concat("/").concat(TripInfo.end).concat("/").concat(JSON.parse(localStorage.getItem("userInformation"))[0].id));
        var response = await fetch("http://localhost:8080/api/create/trip/".concat(TripInfo.name).concat("/").concat(TripInfo.start).concat("/").concat(TripInfo.end).concat("/").concat(JSON.parse(localStorage.getItem("userInformation"))[0].id), {method:'POST'}, {headers:{'Content-Type': 'application/json', 'Access-Control-Allow-Origin':'*'}});
        var json = await response.json();
        if(response.ok && json != null){
            //alert(JSON.stringify(json));
            setThisTrip(json);
            setTripStarted(true);
        }else{
            alert("Trip Creation Failed");
        }
    }

    async function addEventHandler(eventInfo){
        //alert("http://localhost:8080/api/create/event/".concat(eventInfo.subtype).concat("/").concat(eventInfo.name).concat("/").concat(eventInfo.street).concat("/").concat(eventInfo.city).concat("/").concat(eventInfo.state).concat("/").concat(eventInfo.postal).concat("/").concat(eventInfo.country).concat("/").concat(thisTrip[0].id));
        var response = await fetch("http://localhost:8080/api/create/event/".concat(eventInfo.subtype).concat("/").concat(eventInfo.name).concat("/").concat(eventInfo.street).concat("/").concat(eventInfo.city).concat("/").concat(eventInfo.state).concat("/").concat(eventInfo.postal).concat("/").concat(eventInfo.country).concat("/").concat(thisTrip[0].id).concat("/").concat(eventInfo.startDate).concat("/").concat(eventInfo.endDate), {method:'GET'}, {headers:{'Content-Type': 'application/json', 'Access-Control-Allow-Origin':'*'}});
        var json = await response.json();
        if(response.ok && json != null){
            //alert(JSON.stringify(json));
            setTripEvents([]);
            setTripEvents(json);
        }else{
            alert("Event Creation Failed");
        }
    }

    async function getEventsFromTrip(id){
        var response = await fetch("http://localhost:8080/api/get/eventsfromtrip/".concat(id), {method:'GET'}, {headers:{'Content-Type': 'application/json', 'Access-Control-Allow-Origin':'*'}});
        var json = await response.json();
        if(response.ok && json != null){
            //alert(JSON.stringify(json));
            setTripEvents([]);
            setTripEvents(json);
        }else{
            alert("Trip Failed to Load");
        }
    }

    function IconRenderSwitch(props){
        switch(props.des){
            case 'Partly cloudy':
            case 'Overcast':
                return <img src={partly} alt="Logo" width='50' height='50'/>;
            case 'Windy':
                return <img src={windy} alt="Logo" width='50' height='50'/>;
            case 'Rain':
            case 'Light rain':
            case 'Heavy rain':
            case 'Snow':
            case 'Hail':               
                return <img src={rainy} alt="Logo" width='50' height='50'/>;
            default:
                return <img src={sunny} alt="Logo" width='50' height='50'/>;
        }
    }


    if(!tripStarted && localStorage.getItem("trip") === 'null'){
        return(
            <div>
                <ToolBar />
                <div className='centered'><TripStartForm onStart={tripStartHandler}/></div>
            </div>
        );
    }else if(!tripStarted && localStorage.getItem("trip") !== 'null'){
        //alert(localStorage.getItem("trip"));
        setThisTrip([JSON.parse(localStorage.getItem("trip"))]);
        setTripStarted(true);
        getEventsFromTrip(JSON.parse(localStorage.getItem("trip")).id);
        //localStorage.setItem("trip", null);
    }else{
        localStorage.setItem("trip", null);
        return(
            <div>
                <ToolBar />
                    <div className='centered'>
                        {tripEvents?.map(el => {
                            return (
                                    <dl key={el.id}>
                                        <BlurCard>
                                            <h3>{el.subtype}: {el.name} &nbsp; &nbsp; &nbsp;<IconRenderSwitch des={el.api.des}/> {el.api.temp}F</h3>
                                            <p>{el.api.des}</p>
                                            <p>Date: {el.startDate} - {el.endDate}</p>
                                            <p>{el.location.street} {el.location.city} {el.location.state} {el.location.postal} {el.location.country}</p>
                                        </BlurCard>
                                        <img src={downArrow} alt="Logo" width='50' height='50'/>
                                    </dl>
                            );
                        })}
                        <NewEventForm onAdd={addEventHandler}/>
                    </div>
            </div>
        );
    }

}
export default TripScreen;
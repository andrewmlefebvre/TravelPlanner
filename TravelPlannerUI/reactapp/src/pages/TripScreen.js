import React from "react";

import ToolBar from "../ui/ToolBar";

import './TripScreen.css';
import { useState } from 'react';

//import { Table } from "semantic-ui-react"; 
import TripStartForm from "./TripStartForm";
import NewEventForm from "./NewEventForm";

import BlurCard from "../ui/BlurCard";

import downArrow from "../downarrow.png";

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
        var response = await fetch("http://localhost:8080/api/create/event/".concat(eventInfo.subtype).concat("/").concat(eventInfo.name).concat("/").concat(eventInfo.street).concat("/").concat(eventInfo.city).concat("/").concat(eventInfo.state).concat("/").concat(eventInfo.postal).concat("/").concat(eventInfo.country).concat("/").concat(thisTrip[0].id), {method:'GET'}, {headers:{'Content-Type': 'application/json', 'Access-Control-Allow-Origin':'*'}});
        var json = await response.json();
        if(response.ok && json != null){
            //alert(JSON.stringify(json));
            setTripEvents([]);
            setTripEvents(json);
        }else{
            alert("Event Creation Failed");
        }
    }



    if(!tripStarted){
        return(
            <div className='tripclass'>
                <ToolBar />
                <TripStartForm onStart={tripStartHandler}/>
            </div>
        );
    }else{
        return(
            <div>
                <ToolBar />
                    <div className='tripclass'>
                        {tripEvents?.map(el => {
                            return (
                                    <dl key={el.id}>
                                        <BlurCard>
                                            {el.location.street}
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
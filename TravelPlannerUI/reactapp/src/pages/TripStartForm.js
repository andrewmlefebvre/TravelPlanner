import BlurCard from "../ui/BlurCard";

import { useRef } from 'react';

function TripStartForm(props){
    const tripNameInputRef = useRef();
    const startDateRef = useRef();
    const endDateRef = useRef();

    function submitHandler(event){
        event.preventDefault();

        const enteredTripName = tripNameInputRef.current.value;
        const enteredStartDate = startDateRef.current.value;
        const enteredEndDate = endDateRef.current.value;

        const TripInfo = {
            name: enteredTripName,
            start: enteredStartDate,
            end: enteredEndDate
        }
        
        props.onStart(TripInfo);
    }
    return(
        <BlurCard>
            <form onSubmit={submitHandler}>
                <div>
                    <div>
                        <h1>New Trip</h1>
                    </div>
                    <div>
                        <label htmlFor='tripName'>Trip Name: </label>
                        <input type='text' id='triprName' ref={tripNameInputRef}></input>
                    </div>
                    <div>
                        <label htmlFor='startDate'>StartDate: </label>
                        <input type='startDate' id='startDate' ref={startDateRef}  ></input>
                    </div>    
                    <div>
                        <label htmlFor='endDate'>EndDate: </label>
                        <input type='endDate' id='endDate' ref={endDateRef}  ></input>
                    </div>  
                    <div>
                        <button>Start</button>
                    </div>    
                </div>
            </form>
        </BlurCard>
    );
}

export default TripStartForm;
import BlurCard from "../ui/BlurCard";

import { useRef } from 'react';

function NewEventForm(props){
    const subtypeRef = useRef();
    const nameRef = useRef();
    const streetRef = useRef();
    const cityRef = useRef();
    const stateRef = useRef();
    const postalRef = useRef();
    const countryRef = useRef();
    const startDateRef = useRef();
    const endDateRef = useRef();
    const flightRef = useRef();


    function submitHandler(event){
        event.preventDefault();

        const enteredSubtypeRef = subtypeRef.current.value;
        const enteredNameRef = nameRef.current.value;
        const enteredStreetRef = streetRef.current.value;
        const enteredCityRef = cityRef.current.value;
        const enteredStateRef = stateRef.current.value;
        const enteredPostalRef = postalRef.current.value;
        const enteredCountryRef= countryRef.current.value;
        const enteredStartDateRef = startDateRef.current.value;
        const enteredEndDateRef= endDateRef.current.value;
        const enteredFlightRef = flightRef.current.value;

        const EventInfo = {
            subtype: enteredSubtypeRef,
            name: enteredNameRef,
            street: enteredStreetRef,
            city: enteredCityRef,
            state: enteredStateRef,
            postal: enteredPostalRef,
            country: enteredCountryRef,
            startDate: enteredStartDateRef,
            endDate: enteredEndDateRef,
            flight: enteredFlightRef
        }

        props.onAdd(EventInfo); 
    }


    return(
        <BlurCard>
            <form onSubmit={submitHandler}>
                <div>
                    <div>
                        <h1>New Event</h1>
                    </div>
                    <div>
                        <label htmlFor='type'>Type: </label>
                        <input type='text' id='type' ref={subtypeRef}></input>
                    </div> 
                    <div>
                        <label htmlFor='name'>Name: </label>
                        <input type='text' id='name' ref={nameRef}></input>
                    </div> 
                    <div>
                        <label htmlFor='street'>Street: </label>
                        <input type='text' id='street' ref={streetRef}></input>
                    </div> 
                    <div>
                        <label htmlFor='city'>City: </label>
                        <input type='text' id='city' ref={cityRef}></input>
                    </div> 
                    <div>
                        <label htmlFor='state'>State: </label>
                        <input type='text' id='state' ref={stateRef}></input>
                    </div> 
                    <div>
                        <label htmlFor='postal'>Postal: </label>
                        <input type='text' id='postal' ref={postalRef}></input>
                    </div> 
                    <div>
                        <label htmlFor='country'>Country: </label>
                        <input type='text' id='country' ref={countryRef}></input>
                    </div> 
                    <div>
                        <label htmlFor='startDate'>Start Date: </label>
                        <input type='text' id='startDate' ref={startDateRef}></input>
                    </div> 
                    <div>
                        <label htmlFor='endDate'>End Date: </label>
                        <input type='text' id='endDate' ref={endDateRef}></input>
                    </div> 
                    <div>
                        <label htmlFor='flight'>Flight Number: </label>
                        <input type='text' id='flight' ref={flightRef}></input>
                    </div> 
                    <div>
                        <button  className='button-4'>Add</button>
                    </div>    
                </div>
            </form>
        </BlurCard>
    );
}

export default NewEventForm;
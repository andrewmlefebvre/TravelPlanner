import BlurCard from "../ui/BlurCard";
import './UserScreen.css';
import { useRef } from 'react';

import ToolBar from "../ui/ToolBar";

function NewUser(props){
    const userNameInputRef = useRef();
    const passwordRef = useRef();
    const firstRef = useRef();
    const lastRef = useRef();
    const addrRef = useRef();
    const dobRef = useRef();

    async function submitHandler(event){
        event.preventDefault();

        //alert("http://localhost:8080/api/create/user/".concat(firstRef.current.value).concat("/").concat(lastRef.current.value).concat("/").concat(dobRef.current.value).concat("/").concat(addrRef.current.value).concat("/").concat(userNameInputRef.current.value).concat("/").concat(passwordRef.current.value));
        var response = await fetch("http://localhost:8080/api/create/user/".concat(firstRef.current.value).concat("/").concat(lastRef.current.value).concat("/").concat(dobRef.current.value).concat("/").concat(addrRef.current.value).concat("/").concat(userNameInputRef.current.value).concat("/").concat(passwordRef.current.value), {method:'GET'}, {headers:{'Content-Type': 'application/json', 'Access-Control-Allow-Origin':'*'}});
        var json = await response.json();
        if(response.ok && json[0] != null){
            alert("User created")
            window.location.href='/UserScreen'
        }else{
            alert("Invalid Information")
        }


    }

    return(
        <div>
            <ToolBar />
            <div className="centered">
                <BlurCard>
                    <div>
                        <form onSubmit={submitHandler}>
                            <div>
                                <div>
                                    <h1>Create User</h1>
                                </div>
                                <div>
                                    <label htmlFor='userName'>UserName: </label>
                                    <input type='text' id='userName' ref={userNameInputRef}></input>
                                </div>
                                <div>
                                    <label htmlFor='password'>Password: </label>
                                    <input type='text' id='password' ref={passwordRef}></input>
                                </div>
                                <div>
                                    <label htmlFor='first'>First Name: </label>
                                    <input type='text' id='first' ref={firstRef}></input>
                                </div>   
                                <div>
                                    <label htmlFor='last'>Last Name: </label>
                                    <input type='text' id='last' ref={lastRef}></input>
                                </div>   
                                <div>
                                    <label htmlFor='addr'>Address: </label>
                                    <input type='text' id='addr' ref={addrRef}></input>
                                </div>   
                                <div>
                                    <label htmlFor='dob'>Date of Birth: </label>
                                    <input type='text' id='dob' ref={dobRef}></input>
                                </div>       
                                <div>
                                    <button className='button-4' type='submit' id='submit'>Create</button>
                                </div>    
                            </div>
                        </form>
                    </div>
                </BlurCard> 
            </div>
        </div>
    );
}

export default NewUser;


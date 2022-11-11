import BlurCard from "../ui/BlurCard";

import { useRef } from 'react';

function LogInForm(props){
    const userNameInputRef = useRef();
    const passwordRef = useRef();

    function submitHandler(event){
        event.preventDefault();

        const enteredUserName = userNameInputRef.current.value;
        const enteredPassWord = passwordRef.current.value;

        const loginInfo = {
            userName: enteredUserName,
            password: enteredPassWord

        }
        props.onLogin(loginInfo);
    }
    return(
        <BlurCard>
            <form onSubmit={submitHandler}>
                <div>
                    <div>
                        <h1>LogIn</h1>
                    </div>
                    <div>
                        <label htmlFor='userName'>UserName: </label>
                        <input type='text' id='userName' ref={userNameInputRef}></input>
                    </div>
                    <div>
                        <label htmlFor='password'>Password: </label>
                        <input type='password' id='password' ref={passwordRef} securetextentry="true" ></input>
                    </div>    
                    <div>
                        <button>Login</button>
                    </div>    
                </div>
            </form>
        </BlurCard>
    );
}

export default LogInForm;
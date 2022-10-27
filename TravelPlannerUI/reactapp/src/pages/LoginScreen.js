import React from "react";

import ToolBar from "../ui/ToolBar";
import Card from "../ui/Card";

import classes from './LoginScreen.css';



function LoginScreen(){
    return(
        <div>
            <ToolBar />
            <li className={classes.item}>
                <Card><p>Hello</p></Card>
            </li>
        </div>
    );
}

export default LoginScreen;
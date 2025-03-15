/*
import { useState } from "react";

function Counter(props){
    let [count, setCount] = useState(props.count || 0);
    return <>
        <div>{count}</div>
        <button onClick={() => setCount(count + 1)}>Count</button>
        <button onClick={() => setCount(0)}>Reset</button>
    </>

}

export default Counter;

*/


import Home from "./Home";
import Dashboard from "./Dashboard.jsx";
import UserProfile from "./UserProfile.jsx";
import { useReducer } from "react";

const initialState = {
    main: <Home />,
    };

function reducer(state, action){
    switch (action.type){
        case 1:
            return {main: <Home />}

        case 2:
            return {main: <Dashboard />}
        
        case 3:
            return {main: <UserProfile />}
        
        default:
            console.log("Invalid option");
            return {main: state.body};

    }
}

function Counter(){
    const [state, dispatch] = useReducer(reducer, initialState);

    return <>
        <div>{state.main}</div>
        <div>
            <button onClick = {() => dispatch({type: 1})}>home</button>
            <button onClick = {() => dispatch({type: 2})}>Dashboard</button>    
            <button onClick = {() => dispatch({type: 3})}>profile</button>
        </div>
    </>

}

export default Counter;
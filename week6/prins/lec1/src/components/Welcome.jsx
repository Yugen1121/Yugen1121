import { useContext } from "react";
import { Context } from "../App";

function Welcome(){
    const x = useContext(Context);
    console.log(x);
    return <> 
        <h1>Hello, {x?.name ? x.name: "World"}</h1>
        <p>This is some text!</p>
    </>
}


export default Welcome;
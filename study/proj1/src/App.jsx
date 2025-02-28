import Button from "./components/Button.jsx";
import Student from "./components/student.jsx";

function App(){
    return <>
    
    <h1><Student name="Yugen" rollno={123} isAStudent={false} /></h1>
    <h1><Student name="john"/></h1>
    </>
}

export default App;
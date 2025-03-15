import './App.css'
import Counter from "./components/Counter.jsx";
import { createContext, useState } from 'react';
import Welcome from './components/Welcome.jsx';


export const Context = createContext();
function App(){
  
  const [context, contextSetter] = useState("Yugen");

  return <>
    <Context.Provider value = {{name: context, setter: contextSetter}}>
      <Welcome />
    </Context.Provider>
    <Counter />
  </>
}

export default App;
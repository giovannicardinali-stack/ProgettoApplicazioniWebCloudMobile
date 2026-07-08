const { useState } = require("react");


function App() {
    const[RuoloUser, setRuoloUser] = useState(() => {
        return localStorage.getItem("ruolo");
    });
    
}
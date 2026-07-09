import { useState } from "react";
import DashboardAdmin from "./components/DashboardAdmin";
import LoginForm from "./components/LoginForm";

function App() {
    const[RuoloUser, setRuoloUser] = useState(() => {
        return localStorage.getItem("ruolo");
    });

    const handleLoginSuccess = (ruolo) => {
        setRuoloUser(ruolo);
    }

    //gestione del logout
    const handleLogout = () => {
        //rimuove dal local storage token e ruolo
        localStorage.removeItem("token");
        localStorage.removeItem("ruolo");
        //imposta il ruolo dell'utente a null
        setRuoloUser(null);
    }

    //se il ruolo è null carica la pagina di login
    if(!RuoloUser){
        return <LoginForm onLoginSuccess={handleLoginSuccess} />
    }

    //se il ruolo è admin carica la pagina dell'admin
    if(RuoloUser === "ADMIN"){
        console.log("Caricamento Dashboard Admin...");
        return <DashboardAdmin onLogout={handleLogout} />
    }

    return <div>Ruolo non autorizzato</div>;
    
}

export default App;
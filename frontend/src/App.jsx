import { useState } from "react";
import DashboardAdmin from "./components/DashboardAdmin";
import LoginForm from "./components/LoginForm";
import DashboardDipendente from "./components/DashboardDipendente";
import api from "./services/api";

function App() {
    const[RuoloUser, setRuoloUser] = useState(() => {
        return localStorage.getItem("ruolo");
    });

    const handleLoginSuccess = (ruolo) => {
        setRuoloUser(ruolo);
    }

    //gestione del logout
    const handleLogout = async () => {

        try{
            await api.post("/api/v1/auth/logout");
        }
        catch(error){
            console.error("Errore durante il logout dal server:", error);
        }
        finally{
            localStorage.removeItem("ruolo");
            localStorage.removeItem("nomeUtenteLoggato");
        
        // 3. Reindirizziamo al login
        window.location.href = "/login";
        }
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

    if(RuoloUser ==="DIPENDENTE"){
        console.log("Caricamento Dashboard Dipendente...");
        return <DashboardDipendente onLogout={handleLogout}/>
    }

    return <div>Ruolo non autorizzato</div>;
    
}

export default App;
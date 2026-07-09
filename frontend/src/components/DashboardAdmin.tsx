import { useState } from "react";
import Sidebar from "./Sidebar";

interface AdminProps{
    onLogout: () => void;
}

const DashboardAdmin = ({onLogout}: AdminProps) => {
  const [vistaCorrente, setVistaCorrente] = useState("HOME");
  const [idProgettoSelezionato, setIdProgettoSelezionato] = useState<number | null>(null);

  const

  return (
    <div className="d-flex">
      <Sidebar onLogout={onLogout} adminName="admin" onNavigate={setVistaCorrente} />

      <main className="flex-grow-1 p-5" style={{ backgroundColor: "#f8f9fa", minHeight: "100vh" }}>
        {vistaCorrente == "HOME" && (<div>
            <h1> Dashboard amministratore </h1>
            <p>Benvenuto nell'area di gestione. Usa la sidebar per navigare. </p>
        </div>)}

      </main>
    </div>
  );
};

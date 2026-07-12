import {useState } from "react";
import Sidebar from "./Sidebar";
import GestoreProgetti from "./GestoreProgetti";
import GestoreDipendenti from "./GestoreDipendenti";

interface AdminProps {
  onLogout: () => void;
}

const DashboardAdmin = ({ onLogout }: AdminProps) => {
  const [vistaCorrente, setVistaCorrente] = useState("HOME");
  const [idProgettoSelezionato, setIdProgettoSelezionato] = useState<
    number | null
  >(null);
  const [nomeUtenteLoggato, setNomeUtenteLoggato] = useState(
    localStorage.getItem("nomeUtenteLoggato"),
  );

  const vaiAlDettaglio = (id: number) => {
    setIdProgettoSelezionato(id);
    setVistaCorrente("DETTAGLIO_PROGETTO");
  };

  return (
    <div className="d-flex">
      <Sidebar
        onLogout={onLogout}
        adminName="admin"
        onNavigate={setVistaCorrente}
      />

      <main
        className="flex-grow-1 p-5"
        style={{ backgroundColor: "#f8f9fa", minHeight: "100vh" }}
      >
        {vistaCorrente === "HOME" && (
          <div>
            <h1> Dashboard amministratore </h1>
            <p>Benvenuto nell'area di gestione. Usa la sidebar per navigare.</p>
          </div>
        )}

        {vistaCorrente === "PROGETTI" && (
          <div>
            <h1> Gestione Progetti</h1>
            <GestoreProgetti />
          </div>
        )}

        {vistaCorrente === "DIPENDENTI" && (
          <div>
            <h1>Gestione Dipendenti</h1>
            <GestoreDipendenti />
          </div>
        )}
      </main>
    </div>
  );
};

export default DashboardAdmin;

import { useState } from "react";
import SidebarDipendente from "./SidebarDipendente";
import DettagliProgettoDipendente from "./DettagliProgettoDipendente";
import ElencoTaskDipendente from "./ElencoTaskDipendente";

interface Props {
  onLogout: () => void;
  username: string;
}

const DashboardDipendente = ({ onLogout, username }: Props) => {
  // Stato per gestire la vista attiva
  const [vistaCorrente, setVistaCorrente] = useState("HOME");

  return (
    <div className="d-flex">
      {/* Sidebar fissa a sinistra */}
      <SidebarDipendente
        onLogout={onLogout}
        username={username}
        onNavigate={(vista) => setVistaCorrente(vista)}
      />

      {/* Area contenuti a destra */}
      <main className="flex-grow-1 p-4">
        {vistaCorrente === "HOME" && (
          <div className="card p-4">
            <h4>Benvenuto, {username}!</h4>
            <p className="text-muted">
              Seleziona una sezione dal menu per iniziare.
            </p>
          </div>
        )}
        {vistaCorrente === "PROGETTO" && <DettagliProgettoDipendente />}
        {vistaCorrente === "TASK" && (<ElencoTaskDipendente />
        )}
        {/*TODO altre viste*/}
      </main>
    </div>
  );
};

export default DashboardDipendente;

import { useEffect, useState } from "react";
import ListaTask from "./ListaTask";
import api from "../services/api";

interface props {
  idProgetto: string;
  onBack: () => void;
}

interface User {
  id: string;
  username: string;
}

interface Progetto {
  id: string;
  nomeProgetto: string;
  admin: User;
  dipendenti: User[];
  taskInCorso: task[];
}

interface task {
  id: string;
  titolo: string;
  obiettivo: string;
}

const DettagliProgetto = ({ idProgetto, onBack }: props) => {
  const [data, setData] = useState<Progetto | null>(null);
  const [activeTab, setActiveTab] = useState<"info" | "tasks">("info");

  const ricaricaDati = () => {
    api
      .get(`/api/v1/admin/progetti/${idProgetto}`)
      .then((res) => setData(res.data))
      .catch((err) => console.error("Errore ricaricamento:", err));
  };

  useEffect(() => {
    ricaricaDati();
  }, [idProgetto]);

  if (!data) return <p>Caricamento dettagli...</p>;

  return (
    <div className="p-4 bg-white shadow rounded">
      <button className="btn btn-secondary mb-3" onClick={onBack}>
        &larr; Torna alla lista
      </button>
      <h2>Progetto: {data.nomeProgetto}</h2>

      {/* Button Group di Bootstrap */}
      <div className="btn-group mb-4" role="group">
        <button
          className={`btn ${activeTab === "info" ? "btn-primary" : "btn-outline-primary"}`}
          onClick={() => setActiveTab("info")}
        >
          Informazioni
        </button>
        <button
          className={`btn ${activeTab === "info" ? "btn-outline-primary" : "btn-primary"}`}
          onClick={() => setActiveTab("tasks")}
        >
          Task
        </button>
      </div>

      {/* Switcher dei contenuti */}
      {activeTab === "info" ? (
        <div>
          <p>
            <strong>Responsabile:</strong> {data.admin?.username}
          </p>
          <h4>Dipendenti:</h4>
          <ul>
            {data.dipendenti?.map((d) => (
              <li key={d.id}>{d.username}</li>
            ))}
          </ul>
        </div>
      ) : (
        <div className="mt-3">
          <h4>Task per questo progetto:</h4>

          <ListaTask
            idProgetto={idProgetto}
            tasks={data.taskInCorso || []}
            onTaskCreated={ricaricaDati}
          />
        </div>
      )}
    </div>
  );
};

export default DettagliProgetto;

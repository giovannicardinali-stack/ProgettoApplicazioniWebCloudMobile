import { useEffect, useState } from "react";
import ListaTask from "../ListaTask";
import api from "../../services/api";
import { GanttView } from "../GanttView";

interface Props {
  idProgetto: string;
  onBack: () => void;
}

interface User {
  id: string;
  username: string;
}

interface Task {
  id: string;
  titolo: string;
  obiettivo: string;
}

interface Progetto {
  id: string;
  nomeProgetto: string;
  admin?: User | null;
  dipendenti?: User[] | null;
  taskInCorso?: Task[] | null;
}

const DettagliProgetto = ({ idProgetto, onBack }: Props) => {
  const [data, setData] = useState<Progetto | null>(null);
  const [activeTab, setActiveTab] = useState<"info" | "tasks">("info");
  const [hasError, setHasError] = useState(false);

  const ricaricaDati = () => {
    setHasError(false);
    api
      .get(`/api/v1/admin/progetti/${idProgetto}`)
      .then((res) => {
        if (res.data) {
          setData(res.data);
        } else {
          setHasError(true);
        }
      })
      .catch((err) => {
        console.error("Errore ricaricamento:", err);
        setHasError(true);
      });
  };

  useEffect(() => {
    if (idProgetto) {
      ricaricaDati();
    }
  }, [idProgetto]);

  if (hasError) {
    return (
      <div className="p-4 bg-white shadow rounded">
        <button className="btn btn-secondary mb-3" onClick={onBack}>
          &larr; Torna alla lista
        </button>
        <div className="alert alert-danger mb-0">
          Si è verificato un errore durante il recupero del progetto.
        </div>
      </div>
    );
  }

  if (!data) return <div className="p-4">Caricamento dettagli...</div>;

  const listaDipendenti = Array.isArray(data.dipendenti) ? data.dipendenti : [];

  return (
    <div
      style={{
        width: "100%",
        maxWidth: "100%",
        minWidth: 0,
        overflow: "hidden",
      }}
    >
      {/* Riquadro superiore */}
      <div className="p-4 bg-white shadow rounded mb-4">
        <button className="btn btn-secondary mb-3" onClick={onBack}>
          &larr; Torna alla lista
        </button>
        <h2>Progetto: {data.nomeProgetto || "Senza nome"}</h2>

        <div className="btn-group mb-4" role="group">
          <button
            className={`btn ${activeTab === "info" ? "btn-primary" : "btn-outline-primary"}`}
            onClick={() => setActiveTab("info")}
          >
            Informazioni
          </button>
          <button
            className={`btn ${activeTab === "tasks" ? "btn-primary" : "btn-outline-primary"}`}
            onClick={() => setActiveTab("tasks")}
          >
            Task
          </button>
        </div>

        {activeTab === "info" ? (
          <div>
            <p>
              <strong>Responsabile:</strong>{" "}
              {data.admin?.username || "Nessun responsabile assegnato"}
            </p>
            <h4>Dipendenti:</h4>
            {listaDipendenti.length > 0 ? (
              <ul>
                {listaDipendenti.map((d, index) => (
                  <li key={d?.id || index}>
                    {d?.username || "Utente non specificato"}
                  </li>
                ))}
              </ul>
            ) : (
              <p className="text-muted">
                Nessun dipendente assegnato a questo progetto.
              </p>
            )}
          </div>
        ) : (
          <div className="mt-3">
            <h4>Task per questo progetto:</h4>
            <ListaTask idProgetto={idProgetto} />
          </div>
        )}
      </div>

      {/* Riquadro inferiore: finestra di contenimento del Gantt */}
      {/* Riquadro inferiore */}
      <div
        className="p-4 bg-white shadow rounded"
        style={{ width: "100%", maxWidth: "100%", minWidth: 0 }}
      >
        <GanttView projectId={idProgetto} />
      </div>
    </div>
  );
};

export default DettagliProgetto;

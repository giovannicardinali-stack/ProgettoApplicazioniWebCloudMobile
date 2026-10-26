import { useEffect, useState } from "react";
import api from "../../services/api";

interface Task {
  titolo: string;
  obiettivo: string;
  dataInizio: string;
  dataFine: string;
  validato: boolean;
  statoTask:
    | "NONINIZIATO"
    | "INCORSO"
    | "INRITARDO"
    | "TERMINATO"
    | "ARCHIVIATO";
  nomeDipendente: string;
}

const getBadgeColor = (stato: string) => {
  switch (stato) {
    case "TERMINATO":
      return "bg-success"; // Verde (completato e convalidato)
    case "INCORSO":
      return "bg-primary"; // Blu (in corso)
    case "INRITARDO":
      return "bg-danger"; // Rosso (scaduto senza convalida)
    case "ARCHIVIATO":
      return "bg-secondary"; // Grigio (archiviato)
    case "NONINIZIATO":
    default:
      return "bg-warning"; // Giallo/Arancione (non iniziato)
  }
};

const formattaStato = (stato: string) => {
  switch (stato) {
    case "NONINIZIATO":
      return "Non iniziato";
    case "INCORSO":
      return "In corso";
    case "INRITARDO":
      return "In ritardo";
    case "TERMINATO":
      return "Terminato e Approvato";
    case "ARCHIVIATO":
      return "Archiviato";
    default:
      return stato;
  }
};

const ElencoTaskDipendente = () => {
  const [tasks, setTasks] = useState<Task[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    api
      .get("/api/v1/dipendente/tasks")
      .then((res) => setTasks(res.data))
      .catch((err) => console.error("Errore nel recupero task:", err))
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <div>Caricamento task...</div>;

  return (
    <div>
      <h3 className="mb-4">Le mie Task</h3>
      {tasks.length === 0 ? (
        <p>Nessuna task assegnata.</p>
      ) : (
        <div className="row">
          {tasks.map((task, index) => (
            <div key={index} className="col-12 mb-3">
              <div className="card shadow-sm">
                <div className="card-body">
                  <div className="d-flex justify-content-between align-items-center mb-2">
                    <h5 className="card-title mb-0">{task.titolo}</h5>
                    <span className={`badge ${getBadgeColor(task.statoTask)}`}>
                      {formattaStato(task.statoTask)}
                    </span>
                  </div>

                  <p className="card-text">{task.obiettivo}</p>

                  <div className="d-flex gap-3 text-muted small">
                    <span>
                      <strong>Inizio:</strong>{" "}
                      {new Date(task.dataInizio).toLocaleDateString()}
                    </span>
                    <span>
                      <strong>Scadenza:</strong>{" "}
                      {new Date(task.dataFine).toLocaleDateString()}
                    </span>
                    <span>
                      <strong>Stato Validazione:</strong>{" "}
                      {task.validato ? "Sì" : "No"}
                    </span>
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default ElencoTaskDipendente;

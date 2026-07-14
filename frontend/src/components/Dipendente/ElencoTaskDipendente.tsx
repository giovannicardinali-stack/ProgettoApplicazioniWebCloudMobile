import { useEffect, useState } from "react";
import api from "../../services/api";

// Aggiorniamo l'interfaccia per rispecchiare DettagliTaskDTO del backend
interface Task {
  titolo: string;
  obiettivo: string;      // Corrisponde a descrizione
  dataInizio: string;
  dataFine: string;
  validato: boolean;
  statoTask: "APERTO" | "IN_CORSO" | "COMPLETATO"; // Verifica che i valori coincidano
  nomeDipendente: string;
}

const ElencoTaskDipendente = () => {
  const [tasks, setTasks] = useState<Task[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    api.get("/api/v1/dipendente/task")
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
            // Usiamo l'indice come key se non hai un ID univoco nel DTO
            <div key={index} className="col-12 mb-3">
              <div className="card shadow-sm">
                <div className="card-body">
                  <div className="d-flex justify-content-between align-items-center mb-2">
                    <h5 className="card-title mb-0">{task.titolo}</h5>
                    <span className={`badge ${task.statoTask === 'COMPLETATO' ? 'bg-success' : 'bg-warning'}`}>
                      {task.statoTask}
                    </span>
                  </div>
                  
                  <p className="card-text">{task.obiettivo}</p>
                  
                  <div className="d-flex gap-3 text-muted small">
                    <span><strong>Inizio:</strong> {new Date(task.dataInizio).toLocaleDateString()}</span>
                    <span><strong>Scadenza:</strong> {new Date(task.dataFine).toLocaleDateString()}</span>
                    <span><strong>Stato Validazione:</strong> {task.validato ? "Sì" : "No"}</span>
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
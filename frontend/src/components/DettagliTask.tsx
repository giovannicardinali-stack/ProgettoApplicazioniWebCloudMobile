import { useEffect, useState } from "react";
import api from "../services/api";

interface Props {
  idTask: string;
  onBack: () => void;
}

interface Dipendente {
  id: string;
  username: string;
}

interface Task {
  titolo: string;
  obiettivo: string;
  nomeDipendente: string;
  dataInizio: string | null;
  dataFine: string | null;
  statoTask:
    | "NONINIZIATO"
    | "INCORSO"
    | "INRITARDO"
    | "TERMINATO"
    | "ARCHIVIATO";
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

const DettagliTask = ({ idTask, onBack }: Props) => {
  const [task, setTask] = useState<any>(null);
  const [dipendenti, setDipendenti] = useState<Dipendente[]>([]);
  const [showAssign, setShowAssign] = useState(false);
  const [dipendenteSelezionato, setDipendenteSelezionato] = useState("");

  useEffect(() => {
    // Carica prima la task per ricavare il progettoId
    api.get(`/api/v1/progetti/tasks/${idTask}`)
      .then((resTask) => {
        const taskData = resTask.data;
        setTask(taskData);

        const progettoId = taskData.idProgetto;

        if (progettoId) {
          //Chiama il nuovo endpoint filtrato per progetto
          return api.get(`/api/v1/admin/progetti/${progettoId}/dipendenti`);
        } else {
          console.error("Progetto ID non trovato nella task");
          return null;
        }
      })
      .then((resDip) => {
        if (resDip) {
          setDipendenti(resDip.data);
        }
      })
      .catch((err) => console.error("Errore caricamento dati:", err));
  }, [idTask]);

  //funzione per formattare la data
  const formattaData = (dateString: string | null) => {
    if (!dateString) return "Non definita";
    return new Date(dateString).toLocaleDateString("it-IT");
  };

  const assegnaDipendente = async () => {
    if (!dipendenteSelezionato) return;

    try {
      await api.put(`/api/v1/admin/tasks/${idTask}/assegna?dipendenteId=${dipendenteSelezionato}`);
      alert("Dipendente assegnato con successo!");
      setShowAssign(false);
      //ricarica task per aggiornare l'UI
      const res = await api.get(`/api/v1/progetti/tasks/${idTask}`);
      setTask(res.data);
    } catch (err) {
      console.error("Errore assegnazione:", err);
      alert("Errore nell'assegnazione.");
    }
  };

  const validaTask = async () => {
    try {
      await api.put(`/api/v1/admin/tasks/${idTask}/convalida`);
      alert("taks validata con successo!");
      setShowAssign(false);
      //ricarica task per aggiornare l'UI
      const res = await api.get(`/api/v1/progetti/tasks/${idTask}`);
      setTask(res.data);
    } catch (err) {
      console.error("Errore validazione:", err);
      alert("Errore nella validazione della task.");
    }
  };

  if (!task) return <div> Caricamento task...</div>;

  return (
    <div className="card p-4">
      <button className="btn btn-secondary btn-sm mb-3" onClick={onBack}>
        Torna alle task
      </button>

      {/* Titolo e Badge dello Stato puliti e unificati */}
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h2>{task.titolo}</h2>
        <span className={`badge ${getBadgeColor(task.statoTask)}`}>
          {formattaStato(task.statoTask)}
        </span>
      </div>

      <p>
        <strong>Obiettivo:</strong> {task.obiettivo}
      </p>
      <p>
        <strong>Assegnato a:</strong>{" "}
        {task.nomeDipendente || <span className="text-muted">Nessuno</span>}
      </p>
      <div className="row mb-3">
        <div className="col-md-6">
          <p>
            <strong>Inizio: </strong> {formattaData(task.dataInizio)}
          </p>
        </div>
        <div className="col-md-6">
          <p>
            <strong>Fine: </strong> {formattaData(task.dataFine)}
          </p>
        </div>
      </div>

      {/* Sezione Assegnazione */}
      <div className="mt-4 p-3 border rounded">
        {!showAssign ? (
          <button
            className="btn btn-primary"
            onClick={() => setShowAssign(true)}
          >
            Assegna a un dipendente
          </button>
        ) : (
          <div className="d-flex gap-2">
            <select
              className="form-select"
              value={dipendenteSelezionato}
              onChange={(e) => setDipendenteSelezionato(e.target.value)}
            >
              <option value="">Seleziona un dipendente...</option>
              {dipendenti.map((d) => (
                <option key={d.id} value={d.id}>
                  {d.username}
                </option>
              ))}
            </select>
            <button className="btn btn-success" onClick={assegnaDipendente}>
              Conferma
            </button>
            <button
              className="btn btn-secondary"
              onClick={() => setShowAssign(false)}
            >
              Annulla
            </button>
          </div>
        )}
      </div>

      {/* Sezione Validazione */}
      <div className="mt-4 p-3 border rounded">
        <button className="btn btn-success" onClick={validaTask}>
          Valida task
        </button>
      </div>
    </div>
  );
};

export default DettagliTask;

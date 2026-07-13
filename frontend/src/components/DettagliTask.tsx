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

const DettagliTask = ({ idTask, onBack }: Props) => {
  const [task, setTask] = useState<any>(null);
  const [dipendenti, setDipendenti] = useState<Dipendente[]>([]);
  const [showAssign, setShowAssign] = useState(false);
  const [dipendenteSelezionato, setDipendenteSelezionato] = useState("");

  useEffect(() => {
    // Carichiamo task e lista dipendenti in parallelo
    Promise.all([
      api.get(`/api/v1/progetti/task/${idTask}`),
      api.get(`/api/v1/admin/dipendenti`),
    ])
      .then(([resTask, resDip]) => {
        setTask(resTask.data);
        setDipendenti(resDip.data);
      })
      .catch((err) => console.error("Errore caricamento dati:", err));
  }, [idTask]);

  const assegnaDipendente = async () => {
    if (!dipendenteSelezionato) return;

    try {
      await api.post(`/api/v1/admin/${idTask}/assegna`, {
        dipendenteId: dipendenteSelezionato,
      });
      alert("Dipendente assegnato con successo!");
      setShowAssign(false);
      //ricarica task per aggiornare l'UI
      const res = await api.get(`/api/v1/progetti/task/${idTask}`);
      setTask(res.data);
    } catch (err) {
      console.error("Errore assegnazione:", err);
      alert("Errore nell'assegnazione.");
    }
  };

  if (!task) return <div> Caricamento task...</div>;

  return (
    <div className="card p-4">
      <button className="btn btn-secondary btn-sm mb-3" onClick={onBack}>
        Torna alle task
      </button>

      <h2>{task.titolo}</h2>
      <p>
        <strong>Obiettivo:</strong> {task.obiettivo}
      </p>
      <p>
        <strong>Assegnato a:</strong>{" "}
        {task.nomeDipendente || <span className="text-muted">Nessuno</span>}
      </p>

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

      {/* TODO: bottone cancellazione */}
    </div>
  );
};

export default DettagliTask;

import { useEffect, useState } from "react";
import api from "../services/api";

interface Props {
  idTask: string;
  onBack: () => void;
}

const DettagliTask = ({ idTask, onBack }: Props) => {
  const [task, setTask] = useState<any>(null);

  useEffect(() => {
    api
      .get(`/api/v1/progetti/task/${idTask}`)
      .then((res) => setTask(res.data))
      .catch((err) => console.error("errore caricamento dettagli...", err));
  }, [idTask]);

  if (!task) return <div> Caricamento task...</div>;

  return (
    <div className="card p-4">
      <button className="btn btn-secondary btn-sm mb-3" onClick={onBack}>
        Torna alle task
      </button>
      <h2> {task.titolo}</h2>
      <p>
        <strong>Obiettivo:</strong> {task.obiettivo}
      </p>
      <p>
        <strong>Inizio:</strong> {task.dataInizio}
      </p>
      <p>
        <strong>Fine:</strong> {task.dataFine}
      </p>

      {/*TODO bottone cancellazione */}
    </div>
  );
};

export default DettagliTask;

import { useEffect, useState } from "react";
import api from "../services/api";
import CreatoreTask from "./CreatoreTask";
import DettagliTask from "./DettagliTask";

interface Task {
  id: string;
  titolo: string;
  obiettivo: string;
}

interface Props {
  idProgetto: string;
  //tasks: Task[];
  //onTaskCreated: () => void;
}

const ListaTask = ({ idProgetto }: Props) => {
  const [showForm, setShowForm] = useState(false);
  const [tasks, setTasks] = useState<Task[]>([]);
  const [idTaskSelezionata, setIdTaskSelezionata] = useState<string | null>(
    null,
  );

  const recuperaTask = () => {
    api
      .get(`/api/v1/admin/progetti/${idProgetto}/task`)
      .then((res) => {
        console.log("Dati ricevuti:", res.data);
        setTasks(res.data);
      })

      .catch((err) => console.error("errore caricamento task", err));
  };

  useEffect(() => {
    recuperaTask();
  }, [idProgetto]);

  return (
    <div>
      {/* Se una task è selezionata mostriamo il dettaglio, altrimenti la lista */}
      {idTaskSelezionata ? (
        <DettagliTask
          idTask={idTaskSelezionata}
          onBack={() => {
            setIdTaskSelezionata(null);
            recuperaTask();
          }}
        />
      ) : (
        <>
          <button
            className="btn btn-success mb-3"
            onClick={() => setShowForm(!showForm)}
          >
            {showForm ? "Annulla" : "+ Aggiungi Nuova Task"}
          </button>

          {showForm && (
            <CreatoreTask
              idProgetto={idProgetto}
              onTaskCreated={() => {
                setShowForm(false);
                recuperaTask();
              }}
            />
          )}

          <ul className="list-group">
            {tasks && tasks.length > 0 ? (
              tasks.map((task) => (
                <li
                  key={task.id}
                  className="list-group-item"
                  onClick={() => setIdTaskSelezionata(task.id)}
                  style={{ cursor: "pointer" }}
                  onMouseEnter={(e) =>
                    (e.currentTarget.style.backgroundColor = "#f8f9fa")
                  }
                  onMouseLeave={(e) =>
                    (e.currentTarget.style.backgroundColor = "white")
                  }
                >
                  <strong>{task.titolo}</strong> - {task.obiettivo}
                </li>
              ))
            ) : (
              <li className="list-group-item text-muted">
                Nessun task presente per questo progetto.
              </li>
            )}
          </ul>
        </>
      )}
    </div>
  );
};

export default ListaTask;

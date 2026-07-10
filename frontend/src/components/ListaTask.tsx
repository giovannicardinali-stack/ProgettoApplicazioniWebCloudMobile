import { use, useState } from "react";
import api from "../api";

interface Task {
  id: string;
  titolo: string;
  obiettivo: string;
}

interface Props {
  idProgetto: string;
  tasks: Task[];
  onTaskCreated: () => void;
}

const ListaTask = ({ idProgetto, tasks, onTaskCreated }: Props) => {
  const [titolo, setTitolo] = useState("");
  const [obiettivo, setObiettivo] = useState("");
  const [dataInizio, setDataInizio] = useState("");
  const [dataFine, setDataFine] = useState("");

  const [showForm, setShowForm] = useState(false);

  const creaTask = () => {
    api
      .post(`/api/v1/admin/progetti/${idProgetto}/task`, {
        titolo,
        obiettivo,
        dataInizio,
        dataFine,
      })
      .then(() => {
        setTitolo("");
        setObiettivo("");
        setDataInizio("");
        setDataFine("");
        setShowForm(false);
        onTaskCreated();
      })
      .catch((err) => console.error("Errore creazione Task: ", err));
  };

  return (
    <div>
      <button
        className="btn btn-success mb-3"
        onClick={() => setShowForm(!showForm)}
      />

      {showForm && (
        <div className="card p-4 mb-4 bg-light">
          <h5>Nuova Task</h5>
          <div className="mb-3">
            <label>Titolo</label>
            <input
              className="form-control"
              value={titolo}
              onChange={(e) => setTitolo(e.target.value)}
            />
          </div>
          <div className="mb-3">
            <label>Obiettivo</label>
            <input
              className="form-control"
              value={obiettivo}
              onChange={(e) => setObiettivo(e.target.value)}
            />
          </div>
          <div className="row">
            <div className="col">
              <label>Data Inizio</label>
              <input
                type="date"
                className="form-control"
                value={dataInizio}
                onChange={(e) => setDataInizio(e.target.value)}
              />
            </div>
            <div className="col">
              <label>Data Fine</label>
              <input
                type="date"
                className="form-control"
                value={dataFine}
                onChange={(e) => setDataFine(e.target.value)}
              />
            </div>
          </div>
          <button className="btn btn-primary mt-3" onClick={creaTask}>
            Salva Task
          </button>
        </div>
      )}

      <ul className="list-group">
        {tasks.map((task) => (
          <li key={task.id} className="list-group-item">
            {task.titolo || task.obiettivo}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ListaTask;

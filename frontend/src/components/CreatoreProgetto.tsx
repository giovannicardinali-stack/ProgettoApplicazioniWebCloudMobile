import { useEffect, useState } from "react";
import api from "../services/api";

const CreatoreProgetto = ({
  idProgetto,
  onTaskCreated,
}: {
  idProgetto: string;
  onTaskCreated: () => void;
}) => {
  const [titolo, setTitolo] = useState("");
  const [obiettivo, setObiettivo] = useState("");
  const [dataInizio, setDataInizio] = useState("");
  const [dataFine, setDataFine] = useState("");

  const handleSalva = () => {
    api
      .post(`/api/v1/admin/progetti`, {
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
        onTaskCreated();
      })
      .catch((err) => console.error("Errore creazione Task: ", err));
  };

  return (
    <div className="card p-3 mb-4 bg-light">
      <h5> Nuova Task</h5>
      <input
        className="form-control mb-2"
        placeholder="Titolo"
        value={titolo}
        onChange={(e) => setTitolo(e.target.value)}
      />
      <input
        className="form-control mb-2"
        placeholder="Obiettivo"
        value={obiettivo}
        onChange={(e) => setObiettivo(e.target.value)}
      />

      <div className="row">
        <div className="col">
          <input
            type="date"
            className="form-control"
            onChange={(e) => setDataInizio(e.target.value)}
          />
        </div>
        <div className="col">
          <input
            type="date"
            className="form-control"
            onChange={(e) => setDataFine(e.target.value)}
          />
        </div>
      </div>
      <button className="btn btn-primary mt-3" onClick={handleSalva}>
        Salva Task
      </button>
    </div>
  );
};
export default CreatoreTask;

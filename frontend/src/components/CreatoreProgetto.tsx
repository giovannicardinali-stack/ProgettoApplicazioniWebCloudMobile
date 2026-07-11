import { useState } from "react";
import api from "../services/api";

const CreatoreProgetto = ({
  onProgettoCreated,
}: {
  onProgettoCreated: () => void;
}) => {

  const [nome, setNome] = useState("");

  const handleSalva = () => {
    api
      .post(`/api/v1/admin/progetti`, {
        nome,
      })
      .then(() => {
        setNome("");
        onProgettoCreated();
      })
      .catch((err) => console.error("Errore creazione Task: ", err));
  };

  return (
    <div className="card p-3 mb-4 bg-light">
      <h5> Nuova Task</h5>
      <input
        className="form-control mb-2"
        placeholder="Nome Progetto"
        value={nome}
        onChange={(e) => setNome(e.target.value)}
      />

      <div className="row"></div>
      <button className="btn btn-primary mt-3" onClick={handleSalva}>
        Salva Progetto
      </button>
    </div>
  );
};
export default CreatoreProgetto;

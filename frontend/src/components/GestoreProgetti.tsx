import { useEffect, useState } from "react";
import DettagliProgetto from "./DettagliProgetto";
import api from "../services/api";
import CreatoreProgetto from "./CreatoreProgetto";

interface Progetto {
  id: string;
  nome: string;
}

const GestoreProgetti = () => {
  const [progetti, setProgetti] = useState<Progetto[]>([]);
  const [loading, setLoading] = useState(true);
  const [showForm, setShowForm] = useState(false);

  const [idProgettoSelezionato, setIdProgettoSelezionato] = useState<
    string | null
  >(null);

  const caricaProgetti = () => {
    api
      .get("/api/v1/admin/progetti")
      .then((res) => {
        setProgetti(res.data);
        setLoading(false);
      })
      .catch((err) => console.error("Errore nel recupero dei progetti: ", err));
  };

  useEffect(() => {
    caricaProgetti();
  }, []);

  if (idProgettoSelezionato !== null) {
    return (
      <DettagliProgetto
        idProgetto={idProgettoSelezionato}
        onBack={() => setIdProgettoSelezionato(null)}
      />
    );
  }

  return (
    <div className="container mt-4">
      <h3> Lista Progetti</h3>
      <table className="table">
        <thead>
          <tr>
            <th> Nome Progetto</th>
          </tr>
        </thead>
        <tbody>
          {progetti.map((p) => (
            <tr
              key={p.id}
              onClick={() => {
                setIdProgettoSelezionato(p.id);
              }}
              style={{ cursor: "pointer", color: "blue" }}
            >
              <td> {p.nome}</td>
            </tr>
          ))}
        </tbody>
      </table>

      <button
        className="btn btn-primary mb-3"
        onClick={() => setShowForm(!showForm)}
      >
        {showForm ? "Annulla" : "Crea nuovo progetto"}
      </button>

      {showForm && (
        <CreatoreProgetto
          onProgettoCreated={() => {
            setShowForm(false);
            caricaProgetti();
          }}
        />
      )}
    </div>
  );
};

export default GestoreProgetti;

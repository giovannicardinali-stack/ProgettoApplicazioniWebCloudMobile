import { useEffect, useState } from "react";
import api from "../services/api";

interface Dipendente {
  id: string;
  username: string;
  nomeProgetto: string;
}

const GestoreDipendenti = () => {
  const [dipendenti, setDipendenti] = useState<Dipendente[]>([]);
  const [loading, setLoading] = useState(true);

  const caricaDipendenti = () => {
    api.get(`/api/v1/admin/dipendenti`)
      .then((res) => {
        setDipendenti(res.data);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Errore recupero dipendenti:", err);
        setLoading(false);
      });
  };

  useEffect(() => {
    caricaDipendenti();
  }, []);

  return (
    <div className="container mt-4">
      <h3>Lista Dipendenti</h3>
      
      {loading ? (
        <p>Caricamento in corso...</p>
      ) : (
        <table className="table table-hover">
          <thead className="table-light">
            <tr>
              <th>Nome</th>
              <th>Progetto</th>
            </tr>
          </thead>
          <tbody>
            {dipendenti.length > 0 ? (
              dipendenti.map((d) => (
                <tr key={d.id}>
                  <td>{d.username}</td>
                  <td>{d.nomeProgetto ? d.nomeProgetto : "Nessun Progetto"}</td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan={4} className="text-center">Nessun dipendente trovato.</td>
              </tr>
            )}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default GestoreDipendenti;
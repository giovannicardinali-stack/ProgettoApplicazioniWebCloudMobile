import { useEffect, useState } from "react";
import api from "../../services/api";

interface Progetto {
  id: string;
  nomeProgetto: string;
  dipendenti: { username: string }[];
  admin: { username: string };
}

const DettagliProgettoDipendente = () => {
  const [progetto, setProgetto] = useState<Progetto | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // recupera il progetto assegnato all'utente loggato
    api
      .get("/api/v1/dipendente/progetto")
      .then((res) => {
        setProgetto(res.data);
      })
      .catch((err) => {
        console.error("Nessun progetto o errore:", err);
        setProgetto(null);
      })
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <div>Caricamento in corso...</div>;

  if (!progetto) {
    return (
      <div className="alert alert-warning">
        <i className="bi bi-info-circle"></i> Non sei stato assegnato a nessun
        progetto.
      </div>
    );
  }

  return (
    <div className="card p-4 shadow-sm">
      <h2 className="mb-3 text-primary">{progetto.nomeProgetto}</h2>

      <div className="mb-3">
        <strong>Gestito da:</strong> {progetto.admin?.username || "N/D"}
      </div>

      <div className="mb-3">
        <strong>Team assegnato:</strong>
        {progetto.dipendenti && progetto.dipendenti.length > 0 ? (
          <ul className="mt-2">
            {progetto.dipendenti.map((d, index) => (
              <li key={index}>{d.username}</li>
            ))}
          </ul>
        ) : (
          <p className="text-muted">Nessun altro dipendente assegnato.</p>
        )}
      </div>
    </div>
  );
};


export default DettagliProgettoDipendente;
import { useEffect, useState } from "react";
import api from "../services/api";

interface Dipendente {
  id: string;
  username: string;
  nomeProgetto: string | null;
}

interface Progetto {
  id: number;
  nome: string;
}

const GestoreDipendenti = () => {
  const [dipendenti, setDipendenti] = useState<Dipendente[]>([]);
  const [progetti, setProgetti] = useState<Progetto[]>([]);
  const [loading, setLoading] = useState(true);
  
  const [selectedDipendenteId, setSelectedDipendenteId] = useState<string | null>(null);
  const [progettoScelto, setProgettoScelto] = useState<string>("");

  useEffect(() => {
    caricaDati();
  }, []);

  const caricaDati = async () => {
    try {
      const [resDip, resProg] = await Promise.all([
        api.get(`/api/v1/admin/dipendenti`),
        api.get(`/api/v1/admin/progetti`)
      ]);
      setDipendenti(resDip.data);
      setProgetti(resProg.data);
      setLoading(false);
    } catch (err) {
      console.error("Errore recupero dati:", err);
      setLoading(false);
    }
  };

  const assegnaProgetto = async (dipendente: Dipendente) => {
    if (!progettoScelto) {
      alert("Seleziona un progetto valido!");
      return;
    }

    // Conferma se il dipendente ha già un progetto
    if (dipendente.nomeProgetto) {
      const conferma = window.confirm(
        `Il dipendente ${dipendente.username} è già assegnato a "${dipendente.nomeProgetto}". Vuoi cambiare il suo progetto?`
      );
      if (!conferma) return;
    }
    
    try {
      await api.post(`/api/v1/admin/progetti/${progettoScelto}/dipendenti`, {
        dipendenteId: dipendente.id
      });
      
      setSelectedDipendenteId(null);
      setProgettoScelto(""); 
      caricaDati(); 
    } catch (err) {
      console.error("Errore durante l'assegnazione:", err);
      alert("Errore nell'assegnazione del progetto.");
    }
  };

  return (
    <div className="container mt-4">
      <h3>Lista Dipendenti</h3>
      {loading ? <p>Caricamento...</p> : (
        <table className="table table-hover">
          <thead className="table-light">
            <tr>
              <th>Nome</th>
              <th>Progetto</th>
              <th>Azioni</th>
            </tr>
          </thead>
          <tbody>
            {dipendenti.map((d) => (
              <tr key={d.id}>
                <td>{d.username}</td>
                <td>{d.nomeProgetto || "Nessun Progetto"}</td>
                <td>
                  {selectedDipendenteId === d.id ? (
                    <div className="d-flex gap-2">
                      <select 
                        className="form-select form-select-sm" 
                        onChange={(e) => setProgettoScelto(e.target.value)}
                        value={progettoScelto}
                      >
                        <option value="">Seleziona...</option>
                        {progetti.map(p => <option key={p.id} value={p.id}>{p.nome}</option>)}
                      </select>
                      <button className="btn btn-sm btn-success" onClick={() => assegnaProgetto(d)}>OK</button>
                      <button className="btn btn-sm btn-secondary" onClick={() => setSelectedDipendenteId(null)}>X</button>
                    </div>
                  ) : (
                    <button className="btn btn-sm btn-primary" onClick={() => setSelectedDipendenteId(d.id)}>
                      {d.nomeProgetto ? "Cambia Progetto" : "Assegna Progetto"}
                    </button>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default GestoreDipendenti;
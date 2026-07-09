import { useEffect, useState } from "react";
import api from "../api";
import DettagliProgetto from "./DettagliProgetto";

interface Progetto {
  id: string;
  nome: string;
}

const GestoreProgetti = () => {
  const [progetti, setProgetti] = useState<Progetto[]>([]);
  const [loading, setLoading] = useState(true);

  const [idProgettoSelezionato, setIdProgettoSelezionato] = useState<
    string | null
  >(null);

  useEffect(() => {
    api
      .get("/api/v1/admin/progetti")
      .then((res) => {
        setProgetti(res.data);
        setLoading(false);
      })
      .catch((err) => console.error("Errore nel recupero dei progetti: ", err));
  }, []);

  if(idProgettoSelezionato !== null){
    return(
        <DettagliProgetto idProgetto={idProgettoSelezionato} onBack={() => setIdProgettoSelezionato}  />
    )
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
    </div>
  );
};

export default GestoreProgetti;

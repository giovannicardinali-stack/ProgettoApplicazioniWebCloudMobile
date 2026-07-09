import React, { useState } from "react";
import axios from "axios";
import api from "../services/api";
import { useAuth } from "../services/AuthContext";

//per usare loginform bisogna passare una funzione chiamata onLoginSuccess
interface LoginProps {
  onLoginSuccess: (ruolo: string) => void;
}

//dichiarazione componente LoginForm
const LoginForm = ({ onLoginSuccess }: LoginProps) => {
  const [username, setUsername] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  //variabile per mettere il button di login in fase di "caricamento"
  const [isLoading, setIsLoading] = useState<boolean>(false);
  //sfrutta la funzione login di authcontext
  const { login } = useAuth();

  //comportamento attivato al click del button
  const handleLogin = async (e: React.FormEvent) => {
    //evita che il browser ricarichi automaticamente la pagina, lasciando lavorare React (single page application)
    e.preventDefault();
    //imposta la variabile a true, disabilitando il bottone
    setIsLoading(true);

    try {
      const response = await api.post("/api/v1/auth/login", {
        username,
        password,
      });

      const { token, ruolo, nomeUtenteLoggato} = response.data;

      if (token && ruolo) {
        login(token);
        localStorage.setItem("ruolo", ruolo);
        localStorage.setItem("nomeUtenteLoggato", nomeUtenteLoggato);
        onLoginSuccess(ruolo);
      } else {
        alert("Impossibile trovare ruolo");
      }
    } catch (error) {
      console.error("errore durante il login: ", error);
      alert("Credenziali errate o server non raggiungibile.");
    } finally {
      setIsLoading(false); //riabilita il bottone, così se il login non è andato a buon fine l'utente può riprovare
    }
  };

  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-4">
          <div className="card shadow p-4">
            <h2 className="text-center mb-4"> Login </h2>
            <form onSubmit={handleLogin}>
              <div className="mb-3">
                <label className="form-label"> Username </label>

                <input //input username
                  type="text"
                  className="form-control"
                  value={username}
                  //salva l'username nella variabile
                  onChange={(e) => setUsername(e.target.value)}
                  required
                />
              </div>

              <div className="mb-3">
                <label className="form-label"> Password </label>

                <input //input password
                  type="text"
                  className="form-control"
                  value={password}
                  //salva la password nella variabile
                  onChange={(e) => setPassword(e.target.value)}
                  required
                />
              </div>
              <button //se isLoading è true, nel bottone sarà scritto accedi, altrimenti, caricamento
                type="submit"
                className="btn btn-primary w-100"
                disabled={isLoading}
              >
                {isLoading ? "Caricamento..." : "Accedi"}
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LoginForm;
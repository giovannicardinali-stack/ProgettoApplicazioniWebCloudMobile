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

  const [isRegistering, setIsRegistering] = useState<boolean>(false);

  //comportamento attivato al click del button
  const handleLogin = async (e: React.FormEvent) => {
    //evita che il browser ricarichi automaticamente la pagina, lasciando lavorare React (single page application)
    e.preventDefault();
    //imposta la variabile a true, disabilitando il bottone
    setIsLoading(true);

    try {
      if (isRegistering) {
        await api.post("/api/v1/auth/register", { username, password });
        alert("Registrazione completata! Ora puoi effettuare il login.");
        setIsRegistering(false);
      } else {
        const response = await api.post("/api/v1/auth/login", {
          username,
          password,
        });

        const { ruolo, nomeUtenteLoggato } = response.data;

        if (ruolo) {
          localStorage.setItem("ruolo", ruolo);
          localStorage.setItem("nomeUtenteLoggato", nomeUtenteLoggato);
          login();
          onLoginSuccess(ruolo);
        } else {
          alert("Impossibile trovare ruolo");
        }
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
            {/* Titolo dinamico */}
            <h2 className="text-center mb-4">{isRegistering ? "Registrati" : "Login"}</h2>

            {/* Aggiunta del Group Button */}
            <div className="btn-group w-100 mb-4" role="group">
              <button
                type="button"
                className={`btn ${!isRegistering ? "btn-primary" : "btn-outline-primary"}`}
                onClick={() => setIsRegistering(false)}
              >
                Login
              </button>
              <button
                type="button"
                className={`btn ${isRegistering ? "btn-primary" : "btn-outline-primary"}`}
                onClick={() => setIsRegistering(true)}
              >
                Registrati
              </button>
            </div>

            <form onSubmit={handleLogin}>
              <div className="mb-3">
                <label className="form-label">Username</label>
                <input
                  type="text"
                  className="form-control"
                  value={username}
                  onChange={(e) => setUsername(e.target.value)}
                  required
                />
              </div>

              <div className="mb-3">
                <label className="form-label">Password</label>
                <input
                  type="password"
                  className="form-control"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  required
                />
              </div>

              {/* Bottone di submit dinamico */}
              <button
                type="submit"
                className="btn btn-primary w-100"
                disabled={isLoading}
              >
                {isLoading 
                  ? "Caricamento..." 
                  : (isRegistering ? "Registrati" : "Accedi")}
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LoginForm;

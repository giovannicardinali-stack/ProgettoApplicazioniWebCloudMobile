

interface Props {
  onLogout: () => void;
}

const DashboardDipendente = ({ onLogout }: Props) => {
  // Recuperiamo il nome per personalizzare il saluto
  const nomeUtente = localStorage.getItem("nomeUtenteLoggato") || "Dipendente";

  return (
    <div className="container mt-5">
      <div className="card shadow">
        <div className="card-header bg-primary text-white">
          <h3 className="mb-0">Dashboard Dipendente</h3>
        </div>
        <div className="card-body">
          <h4>Benvenuto, {nomeUtente}!</h4>
          <p className="text-muted">Questa è la tua area riservata aziendale.</p>
          
          <hr />
          
          <div className="alert alert-info">
            Qui verranno mostrate le funzionalità dedicate ai dipendenti (es. visualizzazione turni, richieste ferie, ecc.).
          </div>

          {/* Il click scatena direttamente la funzione handleLogout di App.tsx */}
          <button 
            className="btn btn-danger mt-3" 
            onClick={onLogout}
          >
            Disconnetti (Logout)
          </button>
        </div>
      </div>
    </div>
  );
};

export default DashboardDipendente;
interface Props {
  onLogout: () => void;
  username: string;
  onNavigate: (vista: string) => void;
}

const SidebarDipendente = ({ onLogout, username, onNavigate }: Props) => {
  return (
    <div
      className="d-flex flex-column flex-shrink-0 p-3 bg-light"
      style={{ width: "250px", height: "100vh" }}
    >
      <span className="fs-4 mb-3">Dashboard</span>
      <hr />
      
      <ul className="nav nav-pills flex-column mb-auto">
        <li className="nav-item">
          <button
            className="nav-link link-dark w-100 text-start"
            onClick={() => onNavigate("HOME")}
          >
            Home
          </button>
        </li>
        <li className="nav-item">
          <button
            className="nav-link link-dark w-100 text-start"
            onClick={() => onNavigate("PROGETTO")}
          >
            Il mio Progetto
          </button>
        </li>
      </ul>

      <hr />
      
      <div className="mt-auto">
        <p className="small mb-2 text-muted">
          Utente: <strong>{username}</strong>
        </p>
        <button className="btn btn-outline-danger w-100" onClick={onLogout}>
          Logout
        </button>
      </div>
    </div>
  );
};

export default SidebarDipendente;
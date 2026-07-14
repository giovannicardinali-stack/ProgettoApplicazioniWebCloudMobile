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
            className="nav-link link-dark w-100 text-start" // Cambiato text-white in link-dark
            onClick={() => onNavigate("PROGETTO")}
          >
            Il mio Progetto
          </button>
        </li>
      </ul>

      {/* Aggiungi qui sotto il logout se vuoi che sia visibile */}
      <hr />
      <button className="btn btn-outline-danger btn-sm" onClick={onLogout}>
        Logout
      </button>
    </div>
  );
};

export default SidebarDipendente;
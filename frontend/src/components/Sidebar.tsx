interface SidebarProps {
  onLogout: () => void;
  adminName: string;
  onNavigate: (vista: string) => void;
}

const Sidebar = ({ onLogout, adminName, onNavigate }: SidebarProps) => {
  return (
    <nav
      className="d-flex flex-column p-3 bg-dark text-white vh-100"
      style={{ width: "250px" }}
    >
      <div className="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-white">
        <span className="fs-4"> Admin panel</span>
      </div>

      <hr />

      <ul className="nav nav-pills flex-column mb-auto">
        <li className="nav-item">
          <button
            className="nav-link text-white w-100 text-start"
            onClick={() => onNavigate("HOME")}
          ></button>
        </li>
        <li>
          <button
            className="nav-link text-white w-100 text-start"
            onClick={() => onNavigate("PROGETTI")}
          >
            Gestione Progetti
          </button>
        </li>
      </ul>

      <hr />

      <div className="mt-auto">
        <p className="small"> Loggato come: <strong> {adminName || "admin"}</strong></p>
        <button className="btn btn-outline-danger w-100" onClick={onLogout}>
            Logout
        </button>

      </div>
    </nav>
  );
};

export default Sidebar;
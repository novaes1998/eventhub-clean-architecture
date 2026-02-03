import { NavLink, Route, Routes } from "react-router-dom";
import EventosPage from "./pages/EventosPage";
import ParticipantesPage from "./pages/ParticipantesPage";
import IngressosPage from "./pages/IngressosPage";
import "./styles.css";

export default function App() {
  return (
    <div className="container">
      <header className="header">
        <h1>EventHub</h1>

        <nav className="nav">
          <NavLink to="/eventos" className={({ isActive }) => (isActive ? "active" : "")}>
            Eventos
          </NavLink>
          <NavLink to="/participantes" className={({ isActive }) => (isActive ? "active" : "")}>
            Participantes
          </NavLink>
          <NavLink to="/ingressos" className={({ isActive }) => (isActive ? "active" : "")}>
            Comprar
          </NavLink>
        </nav>
      </header>

      <main className="card">
        <Routes>
          <Route path="/" element={<EventosPage />} />
          <Route path="/eventos" element={<EventosPage />} />
          <Route path="/participantes" element={<ParticipantesPage />} />
          <Route path="/ingressos" element={<IngressosPage />} />
        </Routes>
      </main>
    </div>
  );
}

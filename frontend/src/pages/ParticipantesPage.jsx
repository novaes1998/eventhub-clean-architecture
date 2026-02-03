import { useEffect, useState } from "react";
import { http, normalizeError } from "../api/http";
import ResponseBox from "../ui/ResponseBox";

export default function ParticipantesPage() {
  const [participantes, setParticipantes] = useState([]);
  const [form, setForm] = useState({ nome: "", email: "" });
  const [resp, setResp] = useState(null);

  async function carregar() {
    try {
      const r = await http.get("/participantes");
      setParticipantes(r.data);
      setResp({ ok: true, status: r.status, data: r.data });
    } catch (e) {
      setResp(normalizeError(e));
    }
  }

  async function criar(e) {
    e.preventDefault();
    setResp(null);

    try {
      const r = await http.post("/participantes", form);
      setResp({ ok: true, status: r.status, data: r.data });
      setForm({ nome: "", email: "" });
      await carregar();
    } catch (e2) {
      setResp(normalizeError(e2));
    }
  }

  async function deletar(id) {
    if (!confirm(`Deletar participante ${id}?`)) return;

    try {
      const r = await http.delete(`/participantes/${id}`);
      setResp({ ok: true, status: r.status, data: { message: "Participante deletado" } });
      await carregar();
    } catch (e) {
      setResp(normalizeError(e));
    }
  }

  useEffect(() => {
    carregar();
  }, []);

  return (
    <div>
      <h2>Participantes</h2>

      <form className="form" onSubmit={criar}>
        <div className="grid2">
          <label>
            Nome
            <input value={form.nome} onChange={(e) => setForm({ ...form, nome: e.target.value })} />
          </label>

          <label>
            Email
            <input value={form.email} onChange={(e) => setForm({ ...form, email: e.target.value })} />
          </label>
        </div>

        <div className="row">
          <button type="submit">Criar</button>
          <button type="button" className="secondary" onClick={carregar}>
            Recarregar
          </button>
        </div>
      </form>

      <h3>Lista</h3>
      <ul className="list">
        {participantes.map((p) => (
          <li key={p.id} className="listItem">
            <div className="between">
              <div>
                <b>{p.nome}</b> — {p.email} <br />
                <small>ID: {p.id}</small>
              </div>
              <button className="danger" onClick={() => deletar(p.id)}>
                Deletar
              </button>
            </div>
          </li>
        ))}
      </ul>

      <ResponseBox resp={resp} />
    </div>
  );
}

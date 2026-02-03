import { useEffect, useState } from "react";
import { http, normalizeError } from "../api/http";
import ResponseBox from "../ui/ResponseBox";

export default function EventosPage() {
  const [eventos, setEventos] = useState([]);
  const [form, setForm] = useState({ nome: "", data: "", local: "", capacidade: 1 });
  const [resp, setResp] = useState(null);

  async function carregar() {
    try {
      const r = await http.get("/eventos");
      setEventos(r.data);
      setResp({ ok: true, status: r.status, data: r.data });
    } catch (e) {
      setResp(normalizeError(e));
    }
  }

  async function criar(e) {
    e.preventDefault();
    setResp(null);

    try {
      const payload = {
        nome: form.nome,
        data: form.data,
        local: form.local,
        capacidade: Number(form.capacidade),
      };

      const r = await http.post("/eventos", payload);
      setResp({ ok: true, status: r.status, data: r.data });
      setForm({ nome: "", data: "", local: "", capacidade: 1 });
      await carregar();
    } catch (e2) {
      setResp(normalizeError(e2));
    }
  }

  async function deletar(id) {
    if (!confirm(`Deletar evento ${id}?`)) return;

    try {
      const r = await http.delete(`/eventos/${id}`);
      setResp({ ok: true, status: r.status, data: { message: "Evento deletado" } });
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
      <h2>Eventos</h2>

      <form className="form" onSubmit={criar}>
        <div className="grid">
          <label>
            Nome
            <input value={form.nome} onChange={(e) => setForm({ ...form, nome: e.target.value })} />
          </label>

          <label>
            Data (YYYY-MM-DD)
            <input value={form.data} onChange={(e) => setForm({ ...form, data: e.target.value })} />
          </label>

          <label>
            Local
            <input value={form.local} onChange={(e) => setForm({ ...form, local: e.target.value })} />
          </label>

          <label>
            Capacidade
            <input
              type="number"
              min="0"
              value={form.capacidade}
              onChange={(e) => setForm({ ...form, capacidade: e.target.value })}
            />
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
        {eventos.map((ev) => (
          <li key={ev.id} className="listItem">
            <div className="between">
              <div>
                <b>{ev.nome}</b> — {ev.data} — {ev.local} — capacidade: {ev.capacidade} <br />
                <small>ID: {ev.id}</small>
              </div>
              <button className="danger" onClick={() => deletar(ev.id)}>
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

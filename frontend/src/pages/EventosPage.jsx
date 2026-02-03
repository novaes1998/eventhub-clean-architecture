import { useEffect, useState } from "react";
import { http, normalizeError } from "../api/http";
import ResponseBox from "../ui/ResponseBox";

const initialForm = { nome: "", data: "", local: "", capacidade: 1 };

export default function EventosPage() {
  const [eventos, setEventos] = useState([]);
  const [form, setForm] = useState(initialForm);
  const [editId, setEditId] = useState(null); // <- quando não for null, está editando
  const [resp, setResp] = useState(null);
  const [loading, setLoading] = useState(false);

  async function carregar() {
    setLoading(true);
    try {
      const r = await http.get("/eventos");
      setEventos(r.data);
      setResp({ ok: true, status: r.status, data: r.data });
    } catch (e) {
      setResp(normalizeError(e));
    } finally {
      setLoading(false);
    }
  }

  function iniciarEdicao(ev) {
    setEditId(ev.id);
    setForm({
      nome: ev.nome ?? "",
      data: ev.data ?? "",
      local: ev.local ?? "",
      capacidade: ev.capacidade ?? 0,
    });
    setResp(null);
    window.scrollTo({ top: 0, behavior: "smooth" });
  }

  function cancelarEdicao() {
    setEditId(null);
    setForm(initialForm);
    setResp(null);
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
      setForm(initialForm);
      await carregar();
    } catch (e2) {
      setResp(normalizeError(e2));
    }
  }

  async function salvar(e) {
    e.preventDefault();
    setResp(null);

    try {
      const payload = {
        nome: form.nome,
        data: form.data,
        local: form.local,
        capacidade: Number(form.capacidade),
      };

      const r = await http.put(`/eventos/${editId}`, payload);
      setResp({ ok: true, status: r.status, data: r.data ?? { message: "Evento atualizado" } });
      setEditId(null);
      setForm(initialForm);
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

      // Se estava editando esse evento, limpa o form
      if (editId === id) cancelarEdicao();

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

      <form className="form" onSubmit={editId ? salvar : criar}>
        <div style={{ marginBottom: 8, opacity: 0.9 }}>
          {editId ? (
            <>
              ✏️ Editando evento: <code>{editId}</code>
            </>
          ) : (
            <>➕ Criar novo evento</>
          )}
        </div>

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
          <button type="submit" disabled={loading}>
            {editId ? "Salvar" : "Criar"}
          </button>

          {editId ? (
            <button type="button" className="secondary" onClick={cancelarEdicao} disabled={loading}>
              Cancelar edição
            </button>
          ) : (
            <button type="button" className="secondary" onClick={carregar} disabled={loading}>
              Recarregar
            </button>
          )}
        </div>
      </form>

      <h3>Lista</h3>
      <ul className="list">
        {eventos.map((ev) => (
          <li key={ev.id} className="listItem">
            <div className="between">
              <div>
                <b>{ev.nome}</b> — {ev.data} — {ev.local} — capacidade: {ev.capacidade}
                <br />
                <small>ID: {ev.id}</small>
              </div>

              <div className="row">
                <button className="secondary" type="button" onClick={() => iniciarEdicao(ev)} disabled={loading}>
                  Editar
                </button>
                <button className="danger" type="button" onClick={() => deletar(ev.id)} disabled={loading}>
                  Deletar
                </button>
              </div>
            </div>
          </li>
        ))}
      </ul>

      <ResponseBox resp={resp} />
    </div>
  );
}

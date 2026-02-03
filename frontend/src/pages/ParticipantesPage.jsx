import { useEffect, useState } from "react";
import { http, normalizeError } from "../api/http";
import ResponseBox from "../ui/ResponseBox";

const initialForm = { nome: "", email: "" };

export default function ParticipantesPage() {
  const [participantes, setParticipantes] = useState([]);
  const [form, setForm] = useState(initialForm);
  const [editId, setEditId] = useState(null);
  const [resp, setResp] = useState(null);
  const [loading, setLoading] = useState(false);

  async function carregar() {
    setLoading(true);
    try {
      const r = await http.get("/participantes");
      setParticipantes(r.data);
      setResp({ ok: true, status: r.status, data: r.data });
    } catch (e) {
      setResp(normalizeError(e));
    } finally {
      setLoading(false);
    }
  }

  function iniciarEdicao(p) {
    setEditId(p.id);
    setForm({
      nome: p.nome ?? "",
      email: p.email ?? "",
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
      const r = await http.post("/participantes", form);
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
      const r = await http.put(`/participantes/${editId}`, form);
      setResp({ ok: true, status: r.status, data: r.data ?? { message: "Participante atualizado" } });
      setEditId(null);
      setForm(initialForm);
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
      <h2>Participantes</h2>

      <form className="form" onSubmit={editId ? salvar : criar}>
        <div style={{ marginBottom: 8, opacity: 0.9 }}>
          {editId ? (
            <>
              ✏️ Editando participante: <code>{editId}</code>
            </>
          ) : (
            <>➕ Criar novo participante</>
          )}
        </div>

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
        {participantes.map((p) => (
          <li key={p.id} className="listItem">
            <div className="between">
              <div>
                <b>{p.nome}</b> — {p.email}
                <br />
                <small>ID: {p.id}</small>
              </div>

              <div className="row">
                <button className="secondary" type="button" onClick={() => iniciarEdicao(p)} disabled={loading}>
                  Editar
                </button>
                <button className="danger" type="button" onClick={() => deletar(p.id)} disabled={loading}>
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

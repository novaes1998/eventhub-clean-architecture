import { useEffect, useMemo, useState } from "react";
import { http, normalizeError } from "../api/http";
import ResponseBox from "../ui/ResponseBox";

function formatDateTime(iso) {
  if (!iso) return "-";
  // Seu backend retorna: "2026-02-03T19:59:47.298274" (sem Z)
  // Adicionamos Z só para evitar inconsistências de parse em alguns browsers.
  const safeIso = iso.includes("Z") ? iso : `${iso}Z`;
  const d = new Date(safeIso);
  if (isNaN(d.getTime())) return iso;
  return d.toLocaleString("pt-BR");
}

export default function IngressosPage() {
  const [participantes, setParticipantes] = useState([]);
  const [eventos, setEventos] = useState([]);

  const [participanteId, setParticipanteId] = useState("");
  const [eventoId, setEventoId] = useState("");

  const [ingressos, setIngressos] = useState([]);
  const [resp, setResp] = useState(null);
  const [loading, setLoading] = useState(false);

  async function carregarListas() {
    setLoading(true);
    try {
      const [pRes, eRes] = await Promise.all([
        http.get("/participantes"),
        http.get("/eventos"),
      ]);

      setParticipantes(pRes.data ?? []);
      setEventos(eRes.data ?? []);

      // Defaults
      if (!participanteId && pRes.data?.length) setParticipanteId(String(pRes.data[0].id));
      if (!eventoId && eRes.data?.length) setEventoId(String(eRes.data[0].id));

      setResp({
        ok: true,
        status: 200,
        data: { participantes: pRes.data?.length ?? 0, eventos: eRes.data?.length ?? 0 },
      });
    } catch (e) {
      setResp(normalizeError(e));
    } finally {
      setLoading(false);
    }
  }

  async function listarIngressos(pid = participanteId) {
    if (!pid) {
      setResp({ ok: false, status: 400, message: "Selecione um participante para listar ingressos.", raw: null });
      return;
    }

    setLoading(true);
    try {
      const r = await http.get(`/participantes/${pid}/ingressos`);
      // Seu endpoint pode retornar um array ou objeto; garantimos array:
      const data = Array.isArray(r.data) ? r.data : [r.data];
      setIngressos(data.filter(Boolean));
      setResp({ ok: true, status: r.status, data: r.data });
    } catch (e) {
      setResp(normalizeError(e));
      setIngressos([]);
    } finally {
      setLoading(false);
    }
  }

  const participanteSelecionado = useMemo(
    () => participantes.find((p) => String(p.id) === String(participanteId)),
    [participantes, participanteId]
  );

  const eventoSelecionado = useMemo(
    () => eventos.find((ev) => String(ev.id) === String(eventoId)),
    [eventos, eventoId]
  );

  async function comprar(e) {
    e.preventDefault();
    setResp(null);

    if (!participanteId || !eventoId) {
      setResp({ ok: false, status: 400, message: "Selecione um participante e um evento.", raw: null });
      return;
    }

    setLoading(true);
    try {
      const r = await http.post(`/participantes/${participanteId}/ingressos/${eventoId}`);
      setResp({ ok: true, status: r.status, data: r.data ?? { message: "Ingresso comprado!" } });

      // Atualiza listas para refletir capacidade decrementada e lista ingressos do participante
      await carregarListas();
      await listarIngressos(participanteId);
    } catch (err) {
      setResp(normalizeError(err));
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    carregarListas();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  // Auto-listar ingressos quando trocar o participante
  useEffect(() => {
    if (participanteId) listarIngressos(participanteId);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [participanteId]);

  return (
    <div>
      <h2>Ingressos</h2>

      <form className="form" onSubmit={comprar}>
        <div className="grid2">
          <label>
            Participante
            <select
              value={participanteId}
              onChange={(e) => setParticipanteId(e.target.value)}
              className="select"
              disabled={loading}
            >
              {participantes.map((p) => (
                <option key={p.id} value={p.id}>
                  {p.nome} ({p.email})
                </option>
              ))}
            </select>
          </label>

          <label>
            Evento
            <select
              value={eventoId}
              onChange={(e) => setEventoId(e.target.value)}
              className="select"
              disabled={loading}
            >
              {eventos.map((ev) => (
                <option key={ev.id} value={ev.id}>
                  {ev.nome} — {ev.data} — capacidade: {ev.capacidade}
                </option>
              ))}
            </select>
          </label>
        </div>

        <div className="row">
          <button type="submit" disabled={loading}>
            Comprar ingresso
          </button>

          <button type="button" className="secondary" onClick={carregarListas} disabled={loading}>
            Recarregar listas
          </button>

          <button type="button" className="secondary" onClick={() => listarIngressos(participanteId)} disabled={loading}>
            Listar ingressos
          </button>
        </div>
      </form>

      <h3 style={{ marginTop: 18 }}>Resumo da seleção</h3>

      <div className="summaryGrid">
        <div className="summaryCard">
          <div className="summaryTitle">Participante</div>
          {participanteSelecionado ? (
            <div className="summaryBody">
              <div className="summaryMain">{participanteSelecionado.nome}</div>
              <div className="summaryMeta">
                <span>
                  <b>Email:</b> {participanteSelecionado.email}
                </span>
                <span>
                  <b>ID:</b> {participanteSelecionado.id}
                </span>
              </div>
            </div>
          ) : (
            <div className="summaryBody">Nenhum participante selecionado</div>
          )}
        </div>

        <div className="summaryCard">
          <div className="summaryTitle">Evento</div>
          {eventoSelecionado ? (
            <div className="summaryBody">
              <div className="summaryMain">{eventoSelecionado.nome}</div>
              <div className="summaryMeta">
                <span>
                  <b>Data:</b> {eventoSelecionado.data ?? "-"}
                </span>
                <span>
                  <b>Local:</b> {eventoSelecionado.local ?? "-"}
                </span>
                <span>
                  <b>Capacidade:</b> {eventoSelecionado.capacidade ?? "-"}
                </span>
                <span>
                  <b>ID:</b> {eventoSelecionado.id}
                </span>
              </div>
            </div>
          ) : (
            <div className="summaryBody">Nenhum evento selecionado</div>
          )}
        </div>
      </div>

      <h3 style={{ marginTop: 18 }}>Ingressos do participante</h3>

      {ingressos.length === 0 ? (
        <p style={{ opacity: 0.8 }}>Nenhum ingresso encontrado para este participante.</p>
      ) : (
        <div className="tableWrap">
          <table className="table">
            <thead>
              <tr>
                <th>ID Ingresso</th>
                <th>Evento</th>
                <th>Data do Evento</th>
                <th>Local</th>
                <th>Data da Compra</th>
              </tr>
            </thead>
            <tbody>
              {ingressos.map((i) => (
                <tr key={i.id}>
                  <td>{i.id}</td>
                  <td>{i.nomeEvento}</td>
                  <td>{i.dataEvento}</td>
                  <td>{i.localEvento}</td>
                  <td>{formatDateTime(i.dataCompra)}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      <p style={{ marginTop: 10, opacity: 0.8 }}>
        Endpoints:{" "}
        <code>POST /participantes/{`{participanteId}`}/ingressos/{`{eventoId}`}</code>
        {" • "}
        <code>GET /participantes/{`{participanteId}`}/ingressos</code>
      </p>

      <ResponseBox resp={resp} />
    </div>
  );
}

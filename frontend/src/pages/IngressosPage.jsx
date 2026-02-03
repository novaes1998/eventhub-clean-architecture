import { useEffect, useMemo, useState } from "react";
import { http, normalizeError } from "../api/http";
import ResponseBox from "../ui/ResponseBox";

export default function IngressosPage() {
  const [participantes, setParticipantes] = useState([]);
  const [eventos, setEventos] = useState([]);

  const [participanteId, setParticipanteId] = useState("");
  const [eventoId, setEventoId] = useState("");

  const [resp, setResp] = useState(null);
  const [loading, setLoading] = useState(false);

  async function carregarListas() {
    setLoading(true);
    try {
      const [pRes, eRes] = await Promise.all([
        http.get("/participantes"),
        http.get("/eventos"),
      ]);

      setParticipantes(pRes.data);
      setEventos(eRes.data);

      // Define defaults (primeiro item), se ainda não tiver selecionado
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
      // opcional: recarregar eventos para refletir capacidade decrementada
      await carregarListas();
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

  return (
    <div>
      <h2>Comprar Ingresso</h2>

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
            Comprar
          </button>

          <button type="button" className="secondary" onClick={carregarListas} disabled={loading}>
            Recarregar listas
          </button>
        </div>
      </form>

      <div style={{ marginTop: 12, opacity: 0.9 }}>
        <div>
          <b>Participante selecionado:</b>{" "}
          {participanteSelecionado ? (
            <>
              {participanteSelecionado.nome} — <small>ID: {participanteSelecionado.id}</small>
            </>
          ) : (
            "nenhum"
          )}
        </div>
        <div>
          <b>Evento selecionado:</b>{" "}
          {eventoSelecionado ? (
            <>
              {eventoSelecionado.nome} — capacidade: {eventoSelecionado.capacidade} — <small>ID: {eventoSelecionado.id}</small>
            </>
          ) : (
            "nenhum"
          )}
        </div>
      </div>

      <p style={{ marginTop: 10, opacity: 0.8 }}>
        Endpoint: <code>POST /participantes/{`{participanteId}`}/ingressos/{`{eventoId}`}</code>
      </p>

      <ResponseBox resp={resp} />
    </div>
  );
}

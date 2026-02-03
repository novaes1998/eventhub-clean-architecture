import { useState } from "react";
import { http, normalizeError } from "../api/http";
import ResponseBox from "../ui/ResponseBox";

export default function IngressosPage() {
  const [participanteId, setParticipanteId] = useState("");
  const [eventoId, setEventoId] = useState("");
  const [resp, setResp] = useState(null);

  async function comprar(e) {
    e.preventDefault();
    setResp(null);

    try {
      const r = await http.post(`/participantes/${participanteId}/ingressos/${eventoId}`);
      setResp({ ok: true, status: r.status, data: r.data ?? { message: "Ingresso comprado!" } });
    } catch (err) {
      setResp(normalizeError(err));
    }
  }

  return (
    <div>
      <h2>Comprar Ingresso</h2>

      <form className="form" onSubmit={comprar}>
        <div className="grid2">
          <label>
            Participante ID
            <input value={participanteId} onChange={(e) => setParticipanteId(e.target.value)} />
          </label>

          <label>
            Evento ID
            <input value={eventoId} onChange={(e) => setEventoId(e.target.value)} />
          </label>
        </div>

        <div className="row">
          <button type="submit">Comprar</button>
        </div>
      </form>

      <p style={{ marginTop: 10, opacity: 0.8 }}>
        Endpoint: <code>POST /participantes/{`{participanteId}`}/ingressos/{`{eventoId}`}</code>
      </p>

      <ResponseBox resp={resp} />
    </div>
  );
}

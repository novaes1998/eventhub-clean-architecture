export default function ResponseBox({ resp }) {
  if (!resp) return null;

  const title = resp.ok ? `✅ Sucesso (${resp.status})` : `❌ Erro (${resp.status})`;

  return (
    <div className={`response ${resp.ok ? "ok" : "err"}`}>
      <div className="responseTitle">{title}</div>
      <pre className="pre">
        {JSON.stringify(resp.ok ? resp.data : { message: resp.message, raw: resp.raw }, null, 2)}
      </pre>
    </div>
  );
}

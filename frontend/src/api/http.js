import axios from "axios";

export const http = axios.create({
  baseURL: "/api",
  timeout: 15000,
});

export function normalizeError(err) {
  const status = err?.response?.status ?? "SEM_STATUS";
  const data = err?.response?.data ?? null;

  return {
    ok: false,
    status,
    message:
      data?.mensagem ||
      data?.erro ||
      (typeof data === "string" ? data : null) ||
      err?.message ||
      "Erro desconhecido",
    raw: data,
  };
}

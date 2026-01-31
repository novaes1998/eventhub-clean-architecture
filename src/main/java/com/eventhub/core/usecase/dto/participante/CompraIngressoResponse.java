package com.eventhub.core.usecase.dto.participante;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record CompraIngressoResponse(
        UUID participanteId,
        String nomeParticipante,
        UUID eventoId,
        String nomeEvento,
        LocalDate dataEvento,
        String localEvento,
        LocalDateTime dataHoraCompra
) {}

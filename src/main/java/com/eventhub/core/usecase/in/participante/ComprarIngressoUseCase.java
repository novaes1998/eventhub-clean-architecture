package com.eventhub.core.usecase.in.participante;

import com.eventhub.core.usecase.dto.participante.CompraIngressoResponse;

import java.util.UUID;

public interface ComprarIngressoUseCase {

    CompraIngressoResponse executar(UUID participanteId, UUID eventoId);
}

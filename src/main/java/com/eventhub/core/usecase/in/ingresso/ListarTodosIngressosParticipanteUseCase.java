package com.eventhub.core.usecase.in.ingresso;

import com.eventhub.core.usecase.dto.participante.CompraIngressoResponse;

import java.util.List;
import java.util.UUID;

public interface ListarTodosIngressosParticipanteUseCase {

    List<CompraIngressoResponse> executar(UUID participanteId);
}

package com.eventhub.core.usecase.in.participante;

import com.eventhub.core.domain.model.Participante;

import java.util.UUID;

public interface AtualizarParticipanteUseCase {

    Participante executar(UUID id, Participante participante);
}

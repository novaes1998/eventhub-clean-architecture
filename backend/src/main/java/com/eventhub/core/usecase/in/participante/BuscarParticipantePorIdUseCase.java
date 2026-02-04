package com.eventhub.core.usecase.in.participante;

import com.eventhub.core.domain.model.Participante;

import java.util.UUID;

public interface BuscarParticipantePorIdUseCase {

    Participante executar(UUID id);
}

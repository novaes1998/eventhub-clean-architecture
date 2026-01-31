package com.eventhub.core.usecase.in.participante;

import com.eventhub.core.domain.model.Participante;

import java.util.List;

public interface ListarTodosParticipantesUseCase {

    List<Participante> executar();
}

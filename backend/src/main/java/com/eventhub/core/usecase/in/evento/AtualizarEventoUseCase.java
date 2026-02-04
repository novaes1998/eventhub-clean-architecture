package com.eventhub.core.usecase.in.evento;

import com.eventhub.core.domain.model.Evento;

import java.util.UUID;

public interface AtualizarEventoUseCase {

    Evento executar(UUID id, Evento evento);
}

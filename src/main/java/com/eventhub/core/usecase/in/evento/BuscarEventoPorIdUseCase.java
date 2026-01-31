package com.eventhub.core.usecase.in.evento;

import com.eventhub.core.domain.model.Evento;

import java.util.UUID;

public interface BuscarEventoPorIdUseCase {

    Evento executar(UUID id);
}

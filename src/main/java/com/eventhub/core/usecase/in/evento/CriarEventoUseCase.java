package com.eventhub.core.usecase.in.evento;

import com.eventhub.core.domain.model.Evento;

public interface CriarEventoUseCase {

    Evento executar(Evento evento);
}

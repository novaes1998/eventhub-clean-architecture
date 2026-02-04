package com.eventhub.core.usecase.in.evento;

import com.eventhub.core.domain.model.Evento;

import java.util.List;

public interface ListarTodosEventosUseCase {

    List<Evento> executar();
}

package com.eventhub.core.usecase.impl;

import com.eventhub.core.domain.model.Evento;
import com.eventhub.core.usecase.in.evento.ListarTodosEventosUseCase;
import com.eventhub.core.usecase.out.EventoRepository;

import java.util.List;

public class ListarTodosEventosUseCaseImpl implements ListarTodosEventosUseCase {

    private final EventoRepository eventoRepository;

    public ListarTodosEventosUseCaseImpl(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @Override
    public List<Evento> executar() {

        return eventoRepository.listar();
    }
}

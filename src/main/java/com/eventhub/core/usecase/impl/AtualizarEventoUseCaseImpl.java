package com.eventhub.core.usecase.impl;

import com.eventhub.core.domain.exception.EventoNaoEncontradoException;
import com.eventhub.core.domain.model.Evento;
import com.eventhub.core.usecase.in.evento.AtualizarEventoUseCase;
import com.eventhub.core.usecase.out.EventoRepository;

import java.util.UUID;

public class AtualizarEventoUseCaseImpl implements AtualizarEventoUseCase {

    private final EventoRepository eventoRepository;

    public AtualizarEventoUseCaseImpl(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @Override
    public Evento executar(UUID id, Evento evento) {

        eventoRepository.buscarPorId(id)
                .orElseThrow(() -> new EventoNaoEncontradoException(id));

        return eventoRepository.salvar(evento);
    }
}

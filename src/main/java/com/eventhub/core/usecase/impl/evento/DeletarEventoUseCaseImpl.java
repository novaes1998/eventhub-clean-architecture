package com.eventhub.core.usecase.impl;

import com.eventhub.core.domain.exception.EventoNaoEncontradoException;
import com.eventhub.core.usecase.in.evento.DeletarEventoUseCase;
import com.eventhub.core.usecase.out.EventoRepository;

import java.util.UUID;

public class DeletarEventoUseCaseImpl implements DeletarEventoUseCase {

    private final EventoRepository eventoRepository;

    public DeletarEventoUseCaseImpl(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @Override
    public void executar(UUID id) {

        eventoRepository.buscarPorId(id)
                .orElseThrow(() -> new EventoNaoEncontradoException(id));

        eventoRepository.deletar(id);
    }
}

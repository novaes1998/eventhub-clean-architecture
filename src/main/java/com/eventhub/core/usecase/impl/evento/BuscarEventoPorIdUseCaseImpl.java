package com.eventhub.core.usecase.impl.evento;

import com.eventhub.core.domain.exception.EventoNaoEncontradoException;
import com.eventhub.core.domain.model.Evento;
import com.eventhub.core.usecase.in.evento.BuscarEventoPorIdUseCase;
import com.eventhub.core.usecase.out.EventoRepository;

import java.util.UUID;

public class BuscarEventoPorIdUseCaseImpl implements BuscarEventoPorIdUseCase {

    private final EventoRepository eventoRepository;

    public BuscarEventoPorIdUseCaseImpl(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @Override
    public Evento executar(UUID id) {

        return eventoRepository.buscarPorId(id)
                .orElseThrow(() -> new EventoNaoEncontradoException(id));
    }
}

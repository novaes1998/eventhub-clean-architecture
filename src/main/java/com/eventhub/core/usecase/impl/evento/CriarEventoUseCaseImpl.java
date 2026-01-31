package com.eventhub.core.usecase.impl;

import com.eventhub.core.domain.model.Evento;
import com.eventhub.core.usecase.in.evento.CriarEventoUseCase;
import com.eventhub.core.usecase.out.EventoRepository;

public class CriarEventoUseCaseImpl implements CriarEventoUseCase {

    private final EventoRepository eventoRepository;

    public CriarEventoUseCaseImpl(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @Override
    public Evento executar(Evento evento) {

        return eventoRepository.salvar(evento);
    }
}

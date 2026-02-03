package com.eventhub.core.usecase.impl.evento;

import com.eventhub.core.domain.exception.EventoCapacidadeInvalidaException;
import com.eventhub.core.domain.exception.EventoSemCapacidadeException;
import com.eventhub.core.domain.model.Evento;
import com.eventhub.core.usecase.in.evento.CriarEventoUseCase;
import com.eventhub.core.usecase.out.EventoRepository;

import java.util.UUID;

public class CriarEventoUseCaseImpl implements CriarEventoUseCase {

    private final EventoRepository eventoRepository;

    public CriarEventoUseCaseImpl(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @Override
    public Evento executar(Evento evento) {

        if(evento.getCapacidade() <= 0){
            throw new EventoCapacidadeInvalidaException();
        }

        Evento novoEvento = new Evento(
                UUID.randomUUID(),
                evento.getNome(),
                evento.getData(),
                evento.getLocal(),
                evento.getCapacidade()
        );

        return eventoRepository.salvar(novoEvento);
    }
}

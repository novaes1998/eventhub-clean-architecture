package com.eventhub.core.usecase.impl.participante;

import com.eventhub.core.domain.exception.EventoNaoEncontradoException;
import com.eventhub.core.domain.exception.ParticipanteNaoEncontradoException;
import com.eventhub.core.domain.model.Participante;
import com.eventhub.core.usecase.in.participante.BuscarParticipantePorIdUseCase;
import com.eventhub.core.usecase.out.ParticipanteRepository;

import java.util.UUID;

public class BuscarParticipantePorIdUseCaseImpl implements BuscarParticipantePorIdUseCase {

    private final ParticipanteRepository participanteRepository;

    public BuscarParticipantePorIdUseCaseImpl(ParticipanteRepository participanteRepository) {
        this.participanteRepository = participanteRepository;
    }

    @Override
    public Participante executar(UUID id) {

        return participanteRepository.buscarPorId(id)
                .orElseThrow(() -> new EventoNaoEncontradoException(id));
    }
}

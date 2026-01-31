package com.eventhub.core.usecase.impl.participante;

import com.eventhub.core.domain.exception.ParticipanteNaoEncontradoException;
import com.eventhub.core.domain.model.Participante;
import com.eventhub.core.usecase.in.participante.AtualizarParticipanteUseCase;
import com.eventhub.core.usecase.out.ParticipanteRepository;

import java.util.UUID;

public class AtualizarParticipanteUseCaseImpl implements AtualizarParticipanteUseCase {

    private final ParticipanteRepository participanteRepository;

    public AtualizarParticipanteUseCaseImpl(ParticipanteRepository participanteRepository) {
        this.participanteRepository = participanteRepository;
    }

    @Override
    public Participante executar(UUID id, Participante participante) {

        participanteRepository.buscarPorId(id)
                .orElseThrow(() -> new ParticipanteNaoEncontradoException(id));

        return participanteRepository.salvar(participante);
    }
}

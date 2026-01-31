package com.eventhub.core.usecase.impl.participante;

import com.eventhub.core.domain.model.Participante;
import com.eventhub.core.usecase.in.participante.CriarParticipanteUseCase;
import com.eventhub.core.usecase.out.ParticipanteRepository;

public class CriarParticipanteUseCaseImpl implements CriarParticipanteUseCase {

    private final ParticipanteRepository participanteRepository;

    public CriarParticipanteUseCaseImpl(ParticipanteRepository participanteRepository) {
        this.participanteRepository = participanteRepository;
    }

    @Override
    public Participante executar(Participante participante) {

        return participanteRepository.salvar(participante);
    }
}

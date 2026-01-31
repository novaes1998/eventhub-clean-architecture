package com.eventhub.core.usecase.impl.participante;

import com.eventhub.core.domain.exception.ParticipanteNaoEncontradoException;
import com.eventhub.core.usecase.in.participante.DeletarParticipanteUseCase;
import com.eventhub.core.usecase.out.ParticipanteRepository;

import java.util.UUID;

public class DeletarParticipanteUseCaseImpl implements DeletarParticipanteUseCase {

    private final ParticipanteRepository participanteRepository;

    public DeletarParticipanteUseCaseImpl(ParticipanteRepository participanteRepository) {
        this.participanteRepository = participanteRepository;
    }

    @Override
    public void executar(UUID id) {

        participanteRepository.buscarPorId(id)
                        .orElseThrow(()-> new ParticipanteNaoEncontradoException(id));

        participanteRepository.deletar(id);
    }
}

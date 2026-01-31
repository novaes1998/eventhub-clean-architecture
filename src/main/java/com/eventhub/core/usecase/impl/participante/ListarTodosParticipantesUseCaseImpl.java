package com.eventhub.core.usecase.impl.participante;

import com.eventhub.core.domain.model.Participante;
import com.eventhub.core.usecase.in.participante.ListarTodosParticipantesUseCase;
import com.eventhub.core.usecase.out.ParticipanteRepository;

import java.util.List;

public class ListarTodosParticipantesUseCaseImpl implements ListarTodosParticipantesUseCase {

    private final ParticipanteRepository participanteRepository;

    public ListarTodosParticipantesUseCaseImpl(ParticipanteRepository participanteRepository) {
        this.participanteRepository = participanteRepository;
    }

    @Override
    public List<Participante> executar() {
        return participanteRepository.listar();
    }
}

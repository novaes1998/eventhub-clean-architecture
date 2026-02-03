package com.eventhub.core.usecase.impl.participante;

import com.eventhub.core.domain.exception.ParticipanteNaoEncontradoException;
import com.eventhub.core.domain.exception.ParticipanteNaoPodeSerDeletadoException;
import com.eventhub.core.domain.model.Ingresso;
import com.eventhub.core.usecase.in.participante.DeletarParticipanteUseCase;
import com.eventhub.core.usecase.out.IngressoRepository;
import com.eventhub.core.usecase.out.ParticipanteRepository;

import java.util.List;
import java.util.UUID;

public class DeletarParticipanteUseCaseImpl implements DeletarParticipanteUseCase {

    private final ParticipanteRepository participanteRepository;
    private final IngressoRepository ingressoRepository;

    public DeletarParticipanteUseCaseImpl(ParticipanteRepository participanteRepository, IngressoRepository ingressoRepository) {
        this.participanteRepository = participanteRepository;
        this.ingressoRepository = ingressoRepository;
    }

    @Override
    public void executar(UUID id) {

        //1. valida se o participante existe
        participanteRepository.buscarPorId(id)
                        .orElseThrow(()-> new ParticipanteNaoEncontradoException(id));

        //2. verifica se existe algum evento vinculado a este participante
        List<Ingresso> eventosVinculadosAoParticipante =
                ingressoRepository.listarPorParticipante(id);

        //3. caso existe alguma relação
        if(!eventosVinculadosAoParticipante.isEmpty()){

            throw new ParticipanteNaoPodeSerDeletadoException();
        }

        //4. caso não exista relação, deleta participante
        participanteRepository.deletar(id);
    }
}

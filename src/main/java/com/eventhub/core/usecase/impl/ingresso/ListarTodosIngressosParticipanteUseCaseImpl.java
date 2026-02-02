package com.eventhub.core.usecase.impl.ingresso;

import com.eventhub.core.domain.exception.EventoNaoEncontradoException;
import com.eventhub.core.domain.exception.ParticipanteNaoEncontradoException;
import com.eventhub.core.domain.model.Evento;
import com.eventhub.core.domain.model.Ingresso;
import com.eventhub.core.domain.model.Participante;
import com.eventhub.core.usecase.dto.participante.CompraIngressoResponse;
import com.eventhub.core.usecase.in.ingresso.ListarTodosIngressosParticipanteUseCase;
import com.eventhub.core.usecase.out.EventoRepository;
import com.eventhub.core.usecase.out.IngressoRepository;
import com.eventhub.core.usecase.out.ParticipanteRepository;

import java.util.List;
import java.util.UUID;

public class ListarTodosIngressosParticipanteUseCaseImpl implements ListarTodosIngressosParticipanteUseCase {

    private final IngressoRepository ingressoRepository;
    private final ParticipanteRepository participanteRepository;
    private final EventoRepository eventoRepository;

    public ListarTodosIngressosParticipanteUseCaseImpl(IngressoRepository ingressoRepository, ParticipanteRepository participanteRepository, EventoRepository eventoRepository) {
        this.ingressoRepository = ingressoRepository;
        this.participanteRepository = participanteRepository;
        this.eventoRepository = eventoRepository;
    }

    @Override
    public List<CompraIngressoResponse> executar(UUID participanteId) {

        //1. Verificar se o participante existe
        Participante participante = participanteRepository.buscarPorId(participanteId)
                .orElseThrow(() -> new ParticipanteNaoEncontradoException(participanteId));

        //2. Puxar todos os ingressos vinculados ao participante
        List<Ingresso> ingressos =
                ingressoRepository.listarPorParticipante(participanteId);

        // 3. Monta o DTO de resposta
        return ingressos.stream()
                .map(ingresso -> {
                    Evento evento = eventoRepository.buscarPorId(ingresso.getEventoId())
                            .orElseThrow(() ->
                                    new EventoNaoEncontradoException(ingresso.getEventoId()));

                    return new CompraIngressoResponse(
                            ingresso.getId(),
                            participante.getId(),
                            participante.getNome(),
                            evento.getId(),
                            evento.getNome(),
                            evento.getData(),
                            evento.getLocal(),
                            ingresso.getDataCompra()
                    );
                })
                .toList();
    }
}

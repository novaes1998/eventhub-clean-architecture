package com.eventhub.core.usecase.impl.ingresso;

import com.eventhub.core.domain.exception.EventoNaoEncontradoException;
import com.eventhub.core.domain.exception.ParticipanteNaoEncontradoException;
import com.eventhub.core.domain.model.Evento;
import com.eventhub.core.domain.model.Ingresso;
import com.eventhub.core.domain.model.Participante;
import com.eventhub.core.usecase.dto.participante.CompraIngressoResponse;
import com.eventhub.core.usecase.in.ingresso.ComprarIngressoUseCase;
import com.eventhub.core.usecase.out.EventoRepository;
import com.eventhub.core.usecase.out.IngressoRepository;
import com.eventhub.core.usecase.out.ParticipanteRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public class ComprarIngressoUseCaseImpl implements ComprarIngressoUseCase {

    private final IngressoRepository ingressoRepository;
    private final ParticipanteRepository participanteRepository;
    private final EventoRepository eventoRepository;

    public ComprarIngressoUseCaseImpl(IngressoRepository ingressoRepository, ParticipanteRepository participanteRepository, EventoRepository eventoRepository) {
        this.ingressoRepository = ingressoRepository;
        this.participanteRepository = participanteRepository;
        this.eventoRepository = eventoRepository;
    }

    @Override
    public CompraIngressoResponse executar(UUID participanteId, UUID eventoId) {

        //1. verifica se o participante existe
        Participante participante = participanteRepository.buscarPorId(participanteId)
                .orElseThrow(() -> new ParticipanteNaoEncontradoException(participanteId));

        //2. verifica se o evento existe
        Evento evento = eventoRepository.buscarPorId(eventoId)
                .orElseThrow(() -> new EventoNaoEncontradoException(eventoId));

        //3.1 valida se o evento possui capacidade disponível antes de confirmar a compra
        //3.2 decrementar a capacidade após a venda.
        evento.decrementarCapacidade();

        //4. persiste evento atualizado
        eventoRepository.salvar(evento);

        //5. cria o ingresso
        Ingresso ingresso = new Ingresso(
                UUID.randomUUID(),
                participante.getId(),
                evento.getId(),
                LocalDateTime.now());

        //6. salva o ingresso
        ingressoRepository.salvar(ingresso);

        //7. retorna infos ingresso comprado
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
    }
}

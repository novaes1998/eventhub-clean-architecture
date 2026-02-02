package com.eventhub.infra.config;

import com.eventhub.core.usecase.impl.ingresso.ComprarIngressoUseCaseImpl;
import com.eventhub.core.usecase.impl.ingresso.ListarTodosIngressosParticipanteUseCaseImpl;
import com.eventhub.core.usecase.in.ingresso.ComprarIngressoUseCase;
import com.eventhub.core.usecase.in.ingresso.ListarTodosIngressosParticipanteUseCase;
import com.eventhub.core.usecase.out.EventoRepository;
import com.eventhub.core.usecase.out.IngressoRepository;
import com.eventhub.core.usecase.out.ParticipanteRepository;
import org.springframework.context.annotation.Bean;

public class IngressoBeanConfig {

    @Bean
    public ComprarIngressoUseCase comprarIngressoUseCase(
            IngressoRepository ingressoRepository,
            ParticipanteRepository participanteRepository,
            EventoRepository eventoRepository
            ){

        return new ComprarIngressoUseCaseImpl(
                ingressoRepository,
                participanteRepository,
                eventoRepository
        );
    }

    @Bean
    public ListarTodosIngressosParticipanteUseCase listarTodosIngressosParticipanteUseCase(
            IngressoRepository ingressoRepository,
            ParticipanteRepository participanteRepository,
            EventoRepository eventoRepository
    ){

        return new ListarTodosIngressosParticipanteUseCaseImpl(
                ingressoRepository,
                participanteRepository,
                eventoRepository
        );
    }
}

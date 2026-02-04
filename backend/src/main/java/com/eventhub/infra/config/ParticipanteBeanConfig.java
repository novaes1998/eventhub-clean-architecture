package com.eventhub.infra.config;

import com.eventhub.core.usecase.impl.participante.*;
import com.eventhub.core.usecase.in.participante.*;
import com.eventhub.core.usecase.out.IngressoRepository;
import com.eventhub.core.usecase.out.ParticipanteRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParticipanteBeanConfig {

    @Bean
    public AtualizarParticipanteUseCase atualizarParticipanteUseCase(ParticipanteRepository participanteRepository){

        return new AtualizarParticipanteUseCaseImpl(participanteRepository);
    }

    @Bean
    public BuscarParticipantePorIdUseCase buscarParticipantePorIdUseCase(ParticipanteRepository participanteRepository){

        return new BuscarParticipantePorIdUseCaseImpl(participanteRepository);
    }

    @Bean
    public CriarParticipanteUseCase criarParticipanteUseCase(ParticipanteRepository participanteRepository){

        return new CriarParticipanteUseCaseImpl(participanteRepository);
    }

    @Bean
    public DeletarParticipanteUseCase deletarParticipanteUseCase(
            ParticipanteRepository participanteRepository,
            IngressoRepository ingressoRepository
            ){

        return new DeletarParticipanteUseCaseImpl(participanteRepository, ingressoRepository);
    }

    @Bean
    public ListarTodosParticipantesUseCase listarTodosParticipantesUseCase(ParticipanteRepository participanteRepository){

        return new ListarTodosParticipantesUseCaseImpl(participanteRepository);
    }
}

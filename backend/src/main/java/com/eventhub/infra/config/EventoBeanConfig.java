package com.eventhub.infra.config;

import com.eventhub.core.usecase.impl.evento.*;
import com.eventhub.core.usecase.in.evento.*;
import com.eventhub.core.usecase.out.EventoRepository;
import com.eventhub.core.usecase.out.IngressoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventoBeanConfig {

    @Bean
    public AtualizarEventoUseCase atualizarEventoUseCase(EventoRepository eventoRepository){

        return new AtualizarEventoUseCaseImpl(eventoRepository);
    }

    @Bean
    public BuscarEventoPorIdUseCase buscarEventoPorIdUseCase(EventoRepository eventoRepository){

        return new BuscarEventoPorIdUseCaseImpl(eventoRepository);
    }

    @Bean
    public CriarEventoUseCase criarEventoUseCase(EventoRepository eventoRepository){

        return new CriarEventoUseCaseImpl(eventoRepository);
    }

    @Bean
    public DeletarEventoUseCase deletarEventoUseCase(
            EventoRepository eventoRepository,
            IngressoRepository ingressoRepository
    ){

        return new DeletarEventoUseCaseImpl(eventoRepository, ingressoRepository);
    }

    @Bean
    public ListarTodosEventosUseCase listarTodosEventosUseCase(EventoRepository eventoRepository){

        return new ListarTodosEventosUseCaseImpl(eventoRepository);
    }
}

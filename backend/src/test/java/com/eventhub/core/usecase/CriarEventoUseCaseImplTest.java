package com.eventhub.core.usecase;

import com.eventhub.core.domain.exception.DataInvalidaException;
import com.eventhub.core.domain.exception.EventoCapacidadeInvalidaException;
import com.eventhub.core.domain.exception.EventoSemCapacidadeException;
import com.eventhub.core.domain.model.Evento;
import com.eventhub.core.usecase.impl.evento.CriarEventoUseCaseImpl;
import com.eventhub.core.usecase.out.EventoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CriarEventoUseCaseImplTest {

    @Mock
    private EventoRepository eventoRepository;

    @InjectMocks
    private CriarEventoUseCaseImpl criarEventoUseCase;

    @Test
    void deveCriarEventosComSucesso(){

        //given
        Evento evento = new Evento(
                null,
                "Workshop FIAP",
                LocalDate.now().plusDays(5),
                "São Paulo",
                100
        );

        //when
        when(eventoRepository.salvar(any(Evento.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Evento resultado = criarEventoUseCase.executar(evento);

        //then
        assertThat(resultado.getId()).isNotNull();
        assertThat(resultado.getNome()).isEqualTo("Workshop FIAP");

        verify(eventoRepository, times(1)).salvar(any(Evento.class));
    }

    @ParameterizedTest
    @ValueSource(ints = { -1, -5, -30 })
    void deveLancarExcecaoQuandoDataForNoPassado(int diasNoPassado) {

        assertThrows(DataInvalidaException.class, () -> {

            Evento evento = new Evento(
                    UUID.randomUUID(),
                    "Evento Teste",
                    LocalDate.now().plusDays(diasNoPassado),
                    "São Paulo",
                    10
            );

            criarEventoUseCase.executar(evento);
        });

        verify(eventoRepository, never()).salvar(any());
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, -1, -10 })
    void deveLancarExcecaoQuandoCapacidadeForInvalida(int capacidade) {

        EventoCapacidadeInvalidaException exception =
        assertThrows(EventoCapacidadeInvalidaException.class, () ->{

            Evento evento = new Evento(
                    UUID.randomUUID(),
                    "Evento Teste",
                    LocalDate.now().plusDays(5),
                    "São Paulo",
                    capacidade
            );

            criarEventoUseCase.executar(evento);
        });

        assertEquals(
                "Evento sem capacidade. Inserir um valor acima de 0",
                exception.getMessage()
        );

        verify(eventoRepository, never()).salvar(any());
    }
}

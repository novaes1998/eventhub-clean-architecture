package com.eventhub.core.usecase;

import com.eventhub.core.domain.exception.EventoNaoEncontradoException;
import com.eventhub.core.domain.exception.EventoSemCapacidadeException;
import com.eventhub.core.domain.exception.ParticipanteNaoEncontradoException;
import com.eventhub.core.domain.model.Evento;
import com.eventhub.core.domain.model.Participante;
import com.eventhub.core.usecase.dto.participante.CompraIngressoResponse;
import com.eventhub.core.usecase.impl.ingresso.ComprarIngressoUseCaseImpl;
import com.eventhub.core.usecase.out.EventoRepository;
import com.eventhub.core.usecase.out.IngressoRepository;
import com.eventhub.core.usecase.out.ParticipanteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ComprarIngressoUseCaseImplTest {

    @Mock
    private IngressoRepository ingressoRepository;

    @Mock
    private ParticipanteRepository participanteRepository;

    @Mock
    private EventoRepository eventoRepository;

    @InjectMocks
    private ComprarIngressoUseCaseImpl comprarIngressoUseCase;


    @Test
    void deveComprarIngressoComSucessoQuandoEventoTemCapacidade(){

        UUID participanteId = UUID.randomUUID();
        UUID eventoId = UUID.randomUUID();

        Participante participante = new Participante(
                participanteId,
                "João",
                "joao@email.com"
        );

        Evento evento = new Evento(
                eventoId,
                "Show",
                LocalDate.now().plusDays(5),
                "SP",
                10
        );

        when(participanteRepository.buscarPorId(participanteId))
                .thenReturn(Optional.of(participante));

        when(eventoRepository.buscarPorId(eventoId))
                .thenReturn(Optional.of(evento));

        when(ingressoRepository.salvar(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CompraIngressoResponse response =
                comprarIngressoUseCase.executar(participanteId, eventoId);

        assertNotNull(response);
        assertEquals(participanteId, response.participanteId());
        assertEquals(eventoId, response.eventoId());

        verify(eventoRepository).salvar(any(Evento.class));
        verify(ingressoRepository).salvar(any());
    }

    @Test
    void deveLancarExcecaoQuandoEventoNaoTemCapacidade() {

        UUID participanteId = UUID.randomUUID();
        UUID eventoId = UUID.randomUUID();

        Participante participante = new Participante(
                participanteId,
                "João",
                "joao@email.com"
        );

        Evento evento = new Evento(
                eventoId,
                "Show",
                LocalDate.now().plusDays(5),
                "SP",
                1
        );

        when(participanteRepository.buscarPorId(participanteId))
                .thenReturn(Optional.of(participante));

        when(eventoRepository.buscarPorId(eventoId))
                .thenReturn(Optional.of(evento));

        // primeira compra OK
        comprarIngressoUseCase.executar(participanteId, eventoId);

        // segunda compra deve falhar
        EventoSemCapacidadeException exception =
        assertThrows(EventoSemCapacidadeException.class, () ->
                comprarIngressoUseCase.executar(participanteId, eventoId)
        );

        assertEquals(
                "Não é possível efetuar a compra do ingresso. Ingressos foram esgotados.",
                exception.getMessage()
        );

        verify(ingressoRepository, times(1)).salvar(any());
    }

    @Test
    void deveLancarExcecaoQuandoEventoNaoExiste() {

        UUID participanteId = UUID.randomUUID();
        UUID eventoId = UUID.randomUUID();

        when(participanteRepository.buscarPorId(participanteId))
                .thenReturn(Optional.of(new Participante(
                        participanteId, "João", "joao@email.com"
                )));

        when(eventoRepository.buscarPorId(eventoId))
                .thenReturn(Optional.empty());

        assertThrows(EventoNaoEncontradoException.class, () ->
                comprarIngressoUseCase.executar(participanteId, eventoId)
        );

        verify(ingressoRepository, never()).salvar(any());
    }

    @Test
    void deveLancarExcecaoQuandoParticipanteNaoExiste() {

        UUID participanteId = UUID.randomUUID();
        UUID eventoId = UUID.randomUUID();

        when(participanteRepository.buscarPorId(participanteId))
                .thenReturn(Optional.empty());

        assertThrows(ParticipanteNaoEncontradoException.class, () ->
                comprarIngressoUseCase.executar(participanteId, eventoId)
        );

        verifyNoInteractions(eventoRepository);
        verifyNoInteractions(ingressoRepository);
    }
}

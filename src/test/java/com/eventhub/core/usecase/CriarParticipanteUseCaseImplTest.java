package com.eventhub.core.usecase;

import com.eventhub.core.domain.model.Participante;
import com.eventhub.core.usecase.impl.participante.CriarParticipanteUseCaseImpl;
import com.eventhub.core.usecase.out.ParticipanteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CriarParticipanteUseCaseImplTest {

    @Mock
    private ParticipanteRepository participanteRepository;

    @InjectMocks
    private CriarParticipanteUseCaseImpl criarParticipanteUseCase;

    @Test
    void deveCriarParticipanteComSucesso() {

        Participante participanteEntrada = new Participante(
                null,
                "Maria",
                "maria@email.com"
        );

        when(participanteRepository.salvar(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Participante resultado =
                criarParticipanteUseCase.executar(participanteEntrada);

        assertNotNull(resultado.getId());
        assertEquals("Maria", resultado.getNome());
        assertEquals("maria@email.com", resultado.getEmail());

        verify(participanteRepository).salvar(any(Participante.class));
    }
}


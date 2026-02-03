package com.eventhub.infra.web.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.eventhub.core.usecase.dto.participante.CompraIngressoResponse;
import com.eventhub.core.usecase.in.ingresso.ComprarIngressoUseCase;
import com.eventhub.core.usecase.in.ingresso.ListarTodosIngressosParticipanteUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(IngressoController.class)
public class IngressoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ListarTodosIngressosParticipanteUseCase listarTodosIngressosParticipanteUseCase;

    @MockBean
    private ComprarIngressoUseCase comprarIngressoUseCase;

    private final UUID ingressoId = UUID.randomUUID();
    private final UUID participanteId = UUID.randomUUID();


    @Test
    void deveRetornar200QuandoParticipantePossuirIngressos() throws Exception {

        List<CompraIngressoResponse> response = List.of(
                new CompraIngressoResponse(
                        ingressoId,
                        participanteId,
                        "Lucas",
                        UUID.randomUUID(),
                        "Workshop Java",
                        LocalDate.now().plusDays(10),
                        "São Paulo",
                        LocalDateTime.now()
                )
        );

        when(listarTodosIngressosParticipanteUseCase.executar(participanteId))
                .thenReturn(response);

        mockMvc.perform(get("/participantes/{participanteId}/ingressos", participanteId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nomeEvento").value("Workshop Java"));
    }
}

package com.eventhub.infra.web.controller;

import com.eventhub.core.usecase.in.participante.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ParticipanteController.class)
public class ParticipanteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CriarParticipanteUseCase criarParticipanteUseCase;

    @MockBean
    private AtualizarParticipanteUseCase atualizarParticipanteUseCase;

    @MockBean
    private BuscarParticipantePorIdUseCase buscarParticipantePorIdUseCase;

    @MockBean
    private ListarTodosParticipantesUseCase listarTodosParticipantesUseCase;

    @MockBean
    private DeletarParticipanteUseCase deletarParticipanteUseCase;


    @Test
    void deveRetornar400QuandoEmailForInvalido() throws Exception {

        mockMvc.perform(post("/participantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "nome": "Lucas Silva",
                  "email": "email-invalido"
                }
            """))
                .andExpect(status().isBadRequest());

        verify(criarParticipanteUseCase, never()).executar(any());
    }

    @Test
    void deveRetornar204AoDeletarParticipanteComSucesso() throws Exception {

        UUID participanteId = UUID.randomUUID();

        // when / then
        mockMvc.perform(
                        delete("/participantes/{id}", participanteId)
                )
                .andExpect(status().isNoContent());

        // garante que o use case foi chamado
        verify(deletarParticipanteUseCase, times(1))
                .executar(participanteId);
    }
}

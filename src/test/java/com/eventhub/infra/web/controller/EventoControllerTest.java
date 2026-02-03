package com.eventhub.infra.web.controller;

import com.eventhub.core.domain.exception.DataInvalidaException;
import com.eventhub.core.domain.exception.EventoNaoEncontradoException;
import com.eventhub.core.domain.model.Evento;
import com.eventhub.core.usecase.in.evento.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventoController.class)
class EventoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CriarEventoUseCase criarEventoUseCase;

    @MockBean
    private AtualizarEventoUseCase atualizarEventoUseCase;

    @MockBean
    private BuscarEventoPorIdUseCase buscarEventoPorIdUseCase;

    @MockBean
    private ListarTodosEventosUseCase listarTodosEventosUseCase;

    @MockBean
    private DeletarEventoUseCase deletarEventoUseCase;


    @Test
    void deveCriarEventoComSucesso201() throws Exception {

        Evento evento = new Evento(
                UUID.randomUUID(),
                "Workshop FIAP",
                LocalDate.now().plusDays(5),
                "São Paulo",
                100
        );

        when(criarEventoUseCase.executar(any()))
                .thenReturn(evento);

        mockMvc.perform(post("/eventos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "nome": "Workshop FIAP",
                  "data": "2026-05-10",
                  "local": "São Paulo",
                  "capacidade": 100
                }
            """))
                .andExpect(status().isCreated());
    }

    @Test
    void deveRetornar400QuandoNomeForVazio() throws Exception {

        mockMvc.perform(post("/eventos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "nome": "",
                  "data": "2026-05-10",
                  "local": "São Paulo",
                  "capacidade": 100
                }
            """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornar400QuandoDataForNoPassado() throws Exception {

        when(criarEventoUseCase.executar(any()))
                .thenThrow(new DataInvalidaException());

        mockMvc.perform(post("/eventos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "nome": "Evento Teste",
                  "data": "2020-01-01",
                  "local": "São Paulo",
                  "capacidade": 100
                }
            """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornar404QuandoEventoNaoEncontrado() throws Exception {

        when(buscarEventoPorIdUseCase.executar(any()))
                .thenThrow(new EventoNaoEncontradoException(UUID.randomUUID()));

        mockMvc.perform(get("/eventos/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
}


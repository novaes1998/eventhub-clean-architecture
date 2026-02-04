package com.eventhub.infra.web.controller;

import com.eventhub.core.domain.model.Evento;
import com.eventhub.core.usecase.in.evento.*;
import com.eventhub.infra.web.dto.request.EventoRequest;
import com.eventhub.infra.web.dto.response.EventoResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    private final CriarEventoUseCase criarEventoUseCase;
    private final BuscarEventoPorIdUseCase buscarEventoPorIdUseCase;
    private final ListarTodosEventosUseCase listarTodosEventosUseCase;
    private final AtualizarEventoUseCase atualizarEventoUseCase;
    private final DeletarEventoUseCase deletarEventoUseCase;


    public EventoController(CriarEventoUseCase criarEventoUseCase, BuscarEventoPorIdUseCase buscarEventoPorIdUseCase, ListarTodosEventosUseCase listarTodosEventosUseCase, AtualizarEventoUseCase atualizarEventoUseCase, DeletarEventoUseCase deletarEventoUseCase) {
        this.criarEventoUseCase = criarEventoUseCase;
        this.buscarEventoPorIdUseCase = buscarEventoPorIdUseCase;
        this.listarTodosEventosUseCase = listarTodosEventosUseCase;
        this.atualizarEventoUseCase = atualizarEventoUseCase;
        this.deletarEventoUseCase = deletarEventoUseCase;
    }


    @PostMapping
    public ResponseEntity<EventoResponse> criar(
            @RequestBody @Valid EventoRequest request){

        Evento evento = request.toDomain();
        Evento criado = criarEventoUseCase.executar(evento);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(EventoResponse.from(criado));
    }

    @GetMapping
    public ResponseEntity<List<EventoResponse>> listar(){

        List<EventoResponse> response = listarTodosEventosUseCase.executar()
                .stream()
                .map(EventoResponse::from)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponse> buscarPorId(
            @PathVariable UUID id
            ){

        Evento evento = buscarEventoPorIdUseCase.executar(id);

        return ResponseEntity.ok(EventoResponse.from(evento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoResponse> atualizar(
            @PathVariable UUID id,
            @RequestBody @Valid EventoRequest request){

        Evento evento = request.toDomainComId(id);
        Evento atualizado = atualizarEventoUseCase.executar(id, evento);

        return ResponseEntity.ok(EventoResponse.from(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable UUID id){

        deletarEventoUseCase.executar(id);

        return ResponseEntity.noContent().build();
    }
}

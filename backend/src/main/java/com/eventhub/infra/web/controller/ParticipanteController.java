package com.eventhub.infra.web.controller;

import com.eventhub.core.domain.model.Participante;
import com.eventhub.core.usecase.in.participante.*;
import com.eventhub.infra.web.dto.request.ParticipanteRequest;
import com.eventhub.infra.web.dto.response.ParticipanteResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/participantes")
public class ParticipanteController {

    private final AtualizarParticipanteUseCase atualizarParticipanteUseCase;
    private final BuscarParticipantePorIdUseCase buscarParticipantePorIdUseCase;
    private final CriarParticipanteUseCase criarParticipanteUseCase;
    private final DeletarParticipanteUseCase deletarParticipanteUseCase;
    private final ListarTodosParticipantesUseCase listarTodosParticipantesUseCase;

    public ParticipanteController(AtualizarParticipanteUseCase atualizarParticipanteUseCase, BuscarParticipantePorIdUseCase buscarParticipantePorIdUseCase, CriarParticipanteUseCase criarParticipanteUseCase, DeletarParticipanteUseCase deletarParticipanteUseCase, ListarTodosParticipantesUseCase listarTodosParticipantesUseCase) {
        this.atualizarParticipanteUseCase = atualizarParticipanteUseCase;
        this.buscarParticipantePorIdUseCase = buscarParticipantePorIdUseCase;
        this.criarParticipanteUseCase = criarParticipanteUseCase;
        this.deletarParticipanteUseCase = deletarParticipanteUseCase;
        this.listarTodosParticipantesUseCase = listarTodosParticipantesUseCase;
    }


    @PostMapping
    public ResponseEntity<ParticipanteResponse> criar(
            @RequestBody @Valid ParticipanteRequest request){

        Participante participante = request.toDomain();
        Participante criado = criarParticipanteUseCase.executar(participante);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ParticipanteResponse.from(criado));
    }

    @GetMapping
    public ResponseEntity<List<ParticipanteResponse>> listar(){

        List<ParticipanteResponse> response = listarTodosParticipantesUseCase.executar()
                .stream()
                .map(ParticipanteResponse::from)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipanteResponse> buscarPorId(
            @PathVariable UUID id
            ){

        Participante participante = buscarParticipantePorIdUseCase.executar(id);

        return ResponseEntity.ok(ParticipanteResponse.from(participante));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParticipanteResponse> atualizar(
            @PathVariable UUID id,
            @RequestBody @Valid ParticipanteRequest request
    ){

        Participante participante = request.toDomainComId(id);
        Participante atualizado = atualizarParticipanteUseCase.executar(id,participante);

        return ResponseEntity.ok(ParticipanteResponse.from(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable UUID id){

        deletarParticipanteUseCase.executar(id);

        return ResponseEntity.noContent().build();
    }
}

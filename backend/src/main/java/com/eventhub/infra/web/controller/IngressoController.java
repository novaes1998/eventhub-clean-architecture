package com.eventhub.infra.web.controller;

import com.eventhub.core.usecase.dto.participante.CompraIngressoResponse;
import com.eventhub.core.usecase.in.ingresso.ComprarIngressoUseCase;
import com.eventhub.core.usecase.in.ingresso.ListarTodosIngressosParticipanteUseCase;
import com.eventhub.infra.web.dto.response.IngressoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/participantes/{participanteId}/ingressos")
public class IngressoController {

    private final ComprarIngressoUseCase comprarIngressoUseCase;
    private final ListarTodosIngressosParticipanteUseCase listarTodosIngressosParticipanteUseCase;

    public IngressoController(
            ComprarIngressoUseCase comprarIngressoUseCase,
            ListarTodosIngressosParticipanteUseCase listarTodosIngressosParticipanteUseCase) {

        this.comprarIngressoUseCase = comprarIngressoUseCase;
        this.listarTodosIngressosParticipanteUseCase = listarTodosIngressosParticipanteUseCase;
    }

    @PostMapping("/{eventoId}")
    public ResponseEntity<IngressoResponse> comprar(
            @PathVariable UUID participanteId,
            @PathVariable UUID eventoId){

        CompraIngressoResponse compraResponse =
                comprarIngressoUseCase.executar(participanteId,eventoId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(IngressoResponse.from(compraResponse));
    }

    @GetMapping
    public ResponseEntity<List<IngressoResponse>> listar(
            @PathVariable UUID participanteId){

        List<IngressoResponse> response = listarTodosIngressosParticipanteUseCase
                .executar(participanteId)
                .stream()
                .map(IngressoResponse::from)
                .toList();

        return ResponseEntity.ok(response);
    }
}

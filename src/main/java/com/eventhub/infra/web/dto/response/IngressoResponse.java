package com.eventhub.infra.web.dto.response;

import com.eventhub.core.usecase.dto.participante.CompraIngressoResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record IngressoResponse(
        UUID id,
        UUID participanteId,
        String nomeParticipante,
        UUID eventoId,
        String nomeEvento,
        LocalDate dataEvento,
        String localEvento,
        LocalDateTime dataCompra
) {

    public static IngressoResponse from(CompraIngressoResponse response){

        return new IngressoResponse(
                response.ingressoId(),
                response.participanteId(),
                response.nomeParticipante(),
                response.eventoId(),
                response.nomeEvento(),
                response.dataEvento(),
                response.localEvento(),
                response.dataHoraCompra()
        );
    }
}

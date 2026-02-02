package com.eventhub.infra.web.dto.response;

import com.eventhub.core.domain.model.Participante;

import java.util.UUID;

public record ParticipanteResponse(
        UUID id,
        String nome,
        String email
) {

    public static ParticipanteResponse from(Participante participante){

        return new ParticipanteResponse(
                participante.getId(),
                participante.getNome(),
                participante.getEmail()
        );
    }
}

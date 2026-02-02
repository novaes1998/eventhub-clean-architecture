package com.eventhub.infra.web.dto.request;

import com.eventhub.core.domain.model.Participante;
import jakarta.validation.constraints.NotBlank;

public record ParticipanteRequest(
        @NotBlank(message = "Nome do participante é obrigatório")
        String nome,

        @NotBlank(message = "Email do participante é obrigatório")
        String email
) {

    public Participante toDomain(){

        return new Participante(
                null,
                nome,
                email
        );
    }
}

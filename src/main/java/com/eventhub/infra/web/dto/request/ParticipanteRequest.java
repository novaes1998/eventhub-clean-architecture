package com.eventhub.infra.web.dto.request;

import com.eventhub.core.domain.model.Participante;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record ParticipanteRequest(
        @NotBlank(message = "Nome do participante é obrigatório")
        String nome,

        @Email(message = "Email do participante é obrigatório")
        String email
) {

    public Participante toDomain(){

        return new Participante(
                null,
                nome,
                email
        );
    }

    public Participante toDomainComId(UUID id){

        return new Participante(
                id,
                nome,
                email
        );
    }
}

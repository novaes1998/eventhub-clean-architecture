package com.eventhub.infra.web.dto.request;

import com.eventhub.core.domain.model.Participante;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ParticipanteRequest(
        @NotBlank(message = "Nome do participante é obrigatório")
        String nome,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "Insira um e-mail válido")
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

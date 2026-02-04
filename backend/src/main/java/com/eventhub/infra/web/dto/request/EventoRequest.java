package com.eventhub.infra.web.dto.request;

import com.eventhub.core.domain.model.Evento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.UUID;

public record EventoRequest(
        @NotBlank(message = "Nome do evento é obrigatório")
        String nome,

        @NotNull(message = "Data do evento é obrigatória")
        LocalDate data,

        @NotBlank(message = "Local do evento é obrigatório")
        String local,

        @Positive(message = "Capacidade deve ser maior que zero")
        int capacidade
) {

    public Evento toDomain(){

        return new Evento(
                null,
                nome,
                data,
                local,
                capacidade
        );
    }

    public Evento toDomainComId(UUID id){

        return new Evento(
                id,
                nome,
                data,
                local,
                capacidade
        );
    }
}

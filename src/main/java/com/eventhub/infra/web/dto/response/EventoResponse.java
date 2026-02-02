package com.eventhub.infra.web.dto.response;

import com.eventhub.core.domain.model.Evento;

import java.time.LocalDate;
import java.util.UUID;

public record EventoResponse(
        UUID id,
        String nome,
        LocalDate data,
        String local,
        int capacidade
) {

    public static EventoResponse from(Evento evento){

        return new EventoResponse(
                evento.getId(),
                evento.getNome(),
                evento.getData(),
                evento.getLocal(),
                evento.getCapacidade()
        );
    }
}

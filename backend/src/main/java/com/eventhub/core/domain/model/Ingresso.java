package com.eventhub.core.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Ingresso {

    private final UUID id;
    private final UUID participanteId;
    private final UUID eventoId;
    private final LocalDateTime dataCompra;

    public Ingresso(UUID id, UUID participanteId, UUID eventoId, LocalDateTime dataCompra) {
        this.id = id;
        this.participanteId = participanteId;
        this.eventoId = eventoId;
        this.dataCompra = dataCompra;
    }

    public UUID getId() {
        return id;
    }

    public UUID getParticipanteId() {
        return participanteId;
    }

    public UUID getEventoId() {
        return eventoId;
    }

    public LocalDateTime getDataCompra() {
        return dataCompra;
    }
}

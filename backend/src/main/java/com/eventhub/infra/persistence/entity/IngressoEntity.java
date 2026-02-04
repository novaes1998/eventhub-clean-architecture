package com.eventhub.infra.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ingressos")
public class IngressoEntity {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false)
    private UUID participanteId;

    @Column(nullable = false)
    private UUID eventoId;

    @Column(nullable = false)
    private LocalDateTime dataCompra;


    protected IngressoEntity(){}

    public IngressoEntity(UUID id, UUID participanteId, UUID eventoId, LocalDateTime dataCompra) {
        this.id = id;
        this.participanteId = participanteId;
        this.eventoId = eventoId;
        this.dataCompra = dataCompra;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getParticipanteId() {
        return participanteId;
    }

    public void setParticipanteId(UUID participanteId) {
        this.participanteId = participanteId;
    }

    public UUID getEventoId() {
        return eventoId;
    }

    public void setEventoId(UUID eventoId) {
        this.eventoId = eventoId;
    }

    public LocalDateTime getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(LocalDateTime dataCompra) {
        this.dataCompra = dataCompra;
    }
}

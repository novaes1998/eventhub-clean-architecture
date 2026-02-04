package com.eventhub.core.domain.model;

import java.util.UUID;

public class Participante {

    private final UUID id;
    private String nome;
    private String email;


    public Participante(UUID id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }
}

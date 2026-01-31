package com.eventhub.core.domain.model;

import com.eventhub.core.domain.exception.DataInvalidaException;
import com.eventhub.core.domain.exception.EventoSemCapacidadeException;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.UUID;

public class Evento {

    private final UUID id;

    @NotBlank
    private String nome;

    @NotNull
    private LocalDate data;

    @NotBlank
    private String local;

    @NotNull
    @Positive
    private int capacidade;


    public Evento(UUID id, String nome, LocalDate data, String local, int capacidade) {

        this.id = id;
        this.nome = nome;
        this.local = local;

        if(data.isBefore(LocalDate.now())){
            throw new DataInvalidaException();
        }
        this.data = data;

        if(capacidade <= 0){
            throw EventoSemCapacidadeException.paraCriacao();
        }
        this.capacidade = capacidade;
    }


    public int getCapacidade() {
        return capacidade;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getData() {
        return data;
    }

    public String getLocal() {
        return local;
    }


    public void decrementarCapacidade(){
        if(capacidade <= 0){
            throw EventoSemCapacidadeException.paraObter();
        }
        this.capacidade -= 1;
    }
}

package com.eventhub.core.domain.exception;

public class EventoSemCapacidadeException extends RuntimeException {

    public EventoSemCapacidadeException() {

        super("Evento sem capacidade. Inserir um valor acima de 0");
    }
}

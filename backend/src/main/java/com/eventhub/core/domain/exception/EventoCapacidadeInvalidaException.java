package com.eventhub.core.domain.exception;

public class EventoCapacidadeInvalidaException extends RuntimeException {

    public EventoCapacidadeInvalidaException() {
        super("Evento sem capacidade. Inserir um valor acima de 0");
    }
}

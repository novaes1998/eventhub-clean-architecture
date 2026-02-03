package com.eventhub.core.domain.exception;

public class EventoSemCapacidadeException extends RuntimeException {

    public EventoSemCapacidadeException() {
        super("Não é possível efetuar a compra do ingresso. Ingressos foram esgotados.");
    }
}

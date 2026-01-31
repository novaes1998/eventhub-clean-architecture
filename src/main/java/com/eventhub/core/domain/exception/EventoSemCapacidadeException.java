package com.eventhub.core.domain.exception;

public class EventoSemCapacidadeException extends RuntimeException {

    private EventoSemCapacidadeException(String message) { super(message); }

    public static EventoSemCapacidadeException paraCriacao(){

        return new EventoSemCapacidadeException(
                "Evento sem capacidade. Inserir um valor acima de 0"
        );
    }

    public static EventoSemCapacidadeException paraObter(){

        return new EventoSemCapacidadeException(
                "Não é possível efetuar a compra do ingresso. Ingressos foram esgotados."
        );
    }
}

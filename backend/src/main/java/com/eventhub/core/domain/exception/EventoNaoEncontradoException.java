package com.eventhub.core.domain.exception;

import java.util.UUID;

public class EventoNaoEncontradoException extends RuntimeException {

    public EventoNaoEncontradoException(UUID id) {
        super("Evento com o ID: " + id + " não encontrado.");
    }
}

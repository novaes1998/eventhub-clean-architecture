package com.eventhub.core.domain.exception;

import java.util.UUID;

public class ParticipanteNaoEncontradoException extends RuntimeException {

    public ParticipanteNaoEncontradoException(UUID id){
        super("Participante com o ID: " + id + " não encontrado.");
    }
}

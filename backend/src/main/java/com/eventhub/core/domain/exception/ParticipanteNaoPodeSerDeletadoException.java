package com.eventhub.core.domain.exception;

public class ParticipanteNaoPodeSerDeletadoException extends RuntimeException {
    public ParticipanteNaoPodeSerDeletadoException()
    {
        super("Participante não pode ser excluído pelo seguinte motivo: Ele pertence a um ou mais evento(s)");
    }
}

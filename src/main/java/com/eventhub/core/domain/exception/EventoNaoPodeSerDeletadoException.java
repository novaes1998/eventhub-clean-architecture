package com.eventhub.core.domain.exception;

public class EventoNaoPodeSerDeletadoException extends RuntimeException {
    public EventoNaoPodeSerDeletadoException()
    {
        super(
                "Evento não pode ser excluído pelo seguinte motivo: Ele pertence a um ou mais usuário(s)"
        );
    }
}

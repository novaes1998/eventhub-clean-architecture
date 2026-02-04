package com.eventhub.core.domain.exception;

public class DataInvalidaException extends RuntimeException {

    public DataInvalidaException() {
        super("A data do evento não pode ser no passado.");
    }
}

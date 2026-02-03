package com.eventhub.infra.web.exception;

import com.eventhub.core.domain.exception.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // ---------- 400 - Erro de validação (DTO) ----------
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationError(
            MethodArgumentNotValidException ex) {

        String mensagem = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Dados inválidos");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiErrorResponse.of(400, mensagem));
    }

    // ---------- 400 - Regra de negócio ----------
    @ExceptionHandler({
            EventoCapacidadeInvalidaException.class,
            DataInvalidaException.class
    })
    public ResponseEntity<ApiErrorResponse> handleBusinessRuleError(
            RuntimeException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiErrorResponse.of(400, ex.getMessage()));
    }

    // ---------- 404 - Recurso não encontrado ----------
    @ExceptionHandler({
            EventoNaoEncontradoException.class,
            ParticipanteNaoEncontradoException.class
    })
    public ResponseEntity<ApiErrorResponse> handleNotFound(
            RuntimeException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiErrorResponse.of(404, ex.getMessage()));
    }

    // ---------- 409 - Conflito ----------
    @ExceptionHandler({
            EventoSemCapacidadeException.class,
            EventoNaoPodeSerDeletadoException.class,
            ParticipanteNaoPodeSerDeletadoException.class
    })
    public ResponseEntity<ApiErrorResponse> handleConflict(RuntimeException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiErrorResponse.of(409, ex.getMessage()));
    }
}

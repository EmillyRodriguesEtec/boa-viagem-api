package br.com.etechoracio.boa_viagem.config;

import br.com.etechoracio.boa_viagem.exceptions.ViagemInvalidaException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private record ErrorResponse(String error, LocalDateTime dateTime){};

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ViagemInvalidaException.class)
    public ErrorResponse handleRuntimeException(ViagemInvalidaException ex) {
        return new ErrorResponse(ex.getMessage(), LocalDateTime.now());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse handleRuntimeException(IllegalArgumentException ex) {
        return new ErrorResponse(ex.getMessage(), LocalDateTime.now());
    }
}

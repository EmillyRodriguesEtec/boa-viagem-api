package br.com.etechoracio.boa_viagem.exceptions;

public class ViagemInvalidaException extends RuntimeException {
    public ViagemInvalidaException(String message) {
        super(message);
    }
}

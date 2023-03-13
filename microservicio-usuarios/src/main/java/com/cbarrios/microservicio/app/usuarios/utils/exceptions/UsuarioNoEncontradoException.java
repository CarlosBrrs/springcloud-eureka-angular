package com.cbarrios.microservicio.app.usuarios.utils.exceptions;

public class UsuarioNoEncontradoException extends RuntimeException {

    public UsuarioNoEncontradoException(String message) {
        super(message);
    }
}

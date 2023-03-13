package com.cbarrios.microservicio.app.usuarios.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {UsuarioNoEncontradoException.class})
    public ResponseEntity<Object> handleApiRequestException(UsuarioNoEncontradoException e) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(e.getMessage(), notFound, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity(apiException, notFound);
    }
}

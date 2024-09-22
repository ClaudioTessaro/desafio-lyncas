package com.desafio.lyncas.contas.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AcessDeniedException extends RuntimeException {

    public AcessDeniedException() {
        super("Usuario não tem acesso ao recurso desejado.");
    }
}
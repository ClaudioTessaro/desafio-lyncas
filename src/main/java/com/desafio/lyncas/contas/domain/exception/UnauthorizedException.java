package com.desafio.lyncas.contas.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
        super("Organizacao not authorized");
    }

    public UnauthorizedException(String msg) {
        super(msg);
    }
}
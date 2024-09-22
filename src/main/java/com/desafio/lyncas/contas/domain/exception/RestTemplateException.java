package com.desafio.lyncas.contas.domain.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RestTemplateException extends RuntimeException {

    public RestTemplateException(String msg) {
        super(msg);
    }
}

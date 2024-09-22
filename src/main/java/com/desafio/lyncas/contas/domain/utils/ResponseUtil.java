package com.desafio.lyncas.contas.domain.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResponseUtil {
    private final MessageUtil messageUtil;
    public ResponseSuccess buildResponse(String mensagem, Long id) {
        return new ResponseSuccess(messageUtil.getMessage(mensagem, id), id);
    }

    public ResponseSuccess buildResponse(String mensagem) {
        return new ResponseSuccess(messageUtil.getMessage(mensagem), null);
    }
}

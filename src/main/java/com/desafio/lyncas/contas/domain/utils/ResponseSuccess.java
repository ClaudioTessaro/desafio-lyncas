package com.desafio.lyncas.contas.domain.utils;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseSuccess(
        String mensagem,
        Long id
) {
}

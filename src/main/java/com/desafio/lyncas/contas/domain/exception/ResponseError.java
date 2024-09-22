package com.desafio.lyncas.contas.domain.exception;

import lombok.NonNull;

import java.time.OffsetDateTime;

public record ResponseError(
        @NonNull String error, @NonNull OffsetDateTime timestamp, int statusCode
) {
}

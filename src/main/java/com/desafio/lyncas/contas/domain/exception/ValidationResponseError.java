package com.desafio.lyncas.contas.domain.exception;

import lombok.NonNull;

import java.time.OffsetDateTime;
import java.util.List;

public record ValidationResponseError(
        @NonNull List<String> errors, @NonNull OffsetDateTime timestamp, int statusCode
) {
}

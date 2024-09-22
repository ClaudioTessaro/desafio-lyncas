package com.desafio.lyncas.contas.domain.dto.contas;

import java.math.BigDecimal;

public record ValorPagoPeriodoDTO(
        BigDecimal valorPago,
        int periodo
) {
}

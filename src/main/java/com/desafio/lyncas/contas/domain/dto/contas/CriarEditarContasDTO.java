package com.desafio.lyncas.contas.domain.dto.contas;

import com.desafio.lyncas.contas.domain.enums.StatusEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CriarEditarContasDTO(
        LocalDate dataVencimento,
        LocalDate dataPagamento,
        BigDecimal valor,
        String descricao,
        StatusEnum status
) {
}

package com.desafio.lyncas.contas.domain.entities;

import com.desafio.lyncas.contas.domain.enums.StatusEnum;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contas_pagar")
public class ContasPagar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CsvBindByName(column = "id")
    private Long id;

    @Column(name = "data_vencimento", nullable = false)
    @CsvBindByName(column = "data_vencimento")
    @CsvDate("yyyy-MM-dd")
    private LocalDate dataVencimento;

    @Column(name = "data_pagamento", nullable = false)
    @CsvBindByName(column = "data_pagamento")
    @CsvDate("yyyy-MM-dd")
    private LocalDate dataPagamento;

    @Column(name = "valor", scale = 2, precision = 10, nullable = false)
    @CsvBindByName(column = "valor")
    private BigDecimal valor;

    @Column(name = "descricao", length = 255)
    @CsvBindByName(column = "descricao")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao")
    @CsvBindByName(column = "status")
    private StatusEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }


}

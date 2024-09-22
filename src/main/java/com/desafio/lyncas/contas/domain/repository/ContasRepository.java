package com.desafio.lyncas.contas.domain.repository;

import com.desafio.lyncas.contas.domain.entities.ContasPagar;
import com.desafio.lyncas.contas.domain.entities.Organizacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContasRepository extends JpaRepository<ContasPagar, Long> {

    boolean existsByDataVencimentoAndValorAndOrganizacao_Id(LocalDate dataVencimento, BigDecimal valor, Long id);

    Optional<ContasPagar> findByIdAndOrganizacao_Id(Long idConta, Long idOrganizacao);

    @Query("""
                    Select cp from ContasPagar cp 
                    left join cp.organizacao org
                    WHERE org.id = :idOrganizacao
                    AND (:descricao is null or cp.descricao like %:descricao%)
                    AND (:dataVencimento is null or cp.dataVencimento = :dataVencimento)
            """)
    Page<ContasPagar> getContasPorFiltro(String descricao, LocalDate dataVencimento, Long idOrganizacao, Pageable pageable);

    @Query("""
                    Select sum(cp.valor) from ContasPagar cp 
                    left join cp.organizacao org
                    WHERE org.id = :idOrganizacao
                    AND cp.status = 'PAGO'
                    AND cp.dataPagamento between :dataInicial and :dataFinal
            """)
    BigDecimal sumAllAccountsByPeriod(LocalDate dataFinal, LocalDate dataInicial, Long idOrganizacao);
}
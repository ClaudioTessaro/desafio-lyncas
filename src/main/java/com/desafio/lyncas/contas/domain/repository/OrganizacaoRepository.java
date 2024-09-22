package com.desafio.lyncas.contas.domain.repository;

import com.desafio.lyncas.contas.domain.entities.Organizacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizacaoRepository extends JpaRepository<Organizacao, Long> {

    boolean existsByEmailIgnoreCase(String email);

    Optional<Organizacao> findByEmail(String username);
}
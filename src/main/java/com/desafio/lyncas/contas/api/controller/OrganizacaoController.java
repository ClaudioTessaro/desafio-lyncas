package com.desafio.lyncas.contas.api.controller;

import com.desafio.lyncas.contas.api.annotations.ApiController;
import com.desafio.lyncas.contas.api.annotations.SpringDocApiResponseUtil;
import com.desafio.lyncas.contas.domain.dto.organizacao.CriarOrganizacaoDTO;
import com.desafio.lyncas.contas.domain.utils.ResponseSuccess;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ApiController(path = "v1/organizacao", name = "Organizacao", description = "API responsavel por gerenciar as organizações da empresa")
public interface OrganizacaoController {

    @PostMapping("/cadastrar")
    @SpringDocApiResponseUtil(summary = "Registrar uma organização no sistema")
    ResponseEntity<ResponseSuccess> cadastrar(@RequestBody @Valid CriarOrganizacaoDTO criarOrganizacaoDTO);
}

package com.desafio.lyncas.contas.api.controller.impl;

import com.desafio.lyncas.contas.api.annotations.ApiController;
import com.desafio.lyncas.contas.api.controller.OrganizacaoController;
import com.desafio.lyncas.contas.domain.dto.organizacao.CriarOrganizacaoDTO;
import com.desafio.lyncas.contas.domain.service.OrganizacaoService;
import com.desafio.lyncas.contas.domain.utils.ResponseSuccess;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ApiController(path = "v1/organizacao", name = "Organizacao", description = "API responsavel por gerenciar as organizações da empresa")
@RequiredArgsConstructor
public class OrganizacaoControllerImpl implements OrganizacaoController {

    private final OrganizacaoService service;

    @Override
    public ResponseEntity<ResponseSuccess> cadastrar(CriarOrganizacaoDTO criarOrganizacaoDTO) {
        return new ResponseEntity<>(service.cadastrar(criarOrganizacaoDTO), HttpStatus.CREATED);
    }
}

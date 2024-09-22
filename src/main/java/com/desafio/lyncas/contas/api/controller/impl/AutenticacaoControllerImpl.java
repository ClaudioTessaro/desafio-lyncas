package com.desafio.lyncas.contas.api.controller.impl;

import com.desafio.lyncas.contas.api.annotations.ApiController;
import com.desafio.lyncas.contas.api.controller.AutenticacaoController;
import com.desafio.lyncas.contas.domain.dto.auth.JwtViewDTO;
import com.desafio.lyncas.contas.domain.dto.auth.LoginUserDTO;
import com.desafio.lyncas.contas.domain.service.AutenticacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
@ApiController(path = "v1/autenticacao", name = "Autenticacao", description = "APIs responsavel por autenticar o usuario")
public class AutenticacaoControllerImpl implements AutenticacaoController {

    private final AutenticacaoService service;

    @Override
    public ResponseEntity<JwtViewDTO> signIn(LoginUserDTO loginUserDTO) {
        return new ResponseEntity<>(service.signIn(loginUserDTO), HttpStatus.ACCEPTED);
    }
}

package com.desafio.lyncas.contas.api.controller;

import com.desafio.lyncas.contas.api.annotations.ApiController;
import com.desafio.lyncas.contas.api.annotations.SpringDocApiErrorResponseDTO;
import com.desafio.lyncas.contas.domain.dto.auth.JwtViewDTO;
import com.desafio.lyncas.contas.domain.dto.auth.LoginUserDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ApiController(path = "v1/autenticacao", name = "Autenticacao", description = "APIs responsavel por autenticar o usuario")
public interface AutenticacaoController {

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = JwtViewDTO.class))})})
    @SpringDocApiErrorResponseDTO(summary = "Authenticate user in system")
    ResponseEntity<JwtViewDTO> signIn(@RequestBody LoginUserDTO loginUserDTO);
}

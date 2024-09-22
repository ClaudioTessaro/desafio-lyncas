package com.desafio.lyncas.contas.domain.dto.organizacao;


public record UsuarioAutenticacao(
        Long id,
        String nome,
        String email,
        String password
) {
}

package com.desafio.lyncas.contas.domain.dto.organizacao;

import com.desafio.lyncas.contas.api.annotations.PasswordConstraintValidator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record CriarOrganizacaoDTO(

        String nome,
        @Email(message = "O campo email deve ser no seguinte formato: example@provider.com")
        String email,
        @Size(min = 6, message = "O campo password deve ter no minimo 6 caracteres")
        @PasswordConstraintValidator(message = "O campo de senha deve conter pelo menos um caractere especial, um número e letras maiúsculas e minúsculas.")
        String password,
        String descricao) {

}

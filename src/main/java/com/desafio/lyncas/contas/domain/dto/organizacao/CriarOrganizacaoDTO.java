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
        @Size(min = 8, message = "O campo password deve ter no minimo 8 caracteres")
        @PasswordConstraintValidator(message = "The password field must contain at least one special character, one number, and both uppercase and lowercase letters.")
        String password,
        String descricao) {

}

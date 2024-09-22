package com.desafio.lyncas.contas.api.validators;

import com.desafio.lyncas.contas.api.annotations.PasswordConstraintValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<PasswordConstraintValidator, String> {

    @Override
    public boolean isValid(String valor, ConstraintValidatorContext constraintValidatorContext) {
        var regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$";
        return StringUtils.isNotBlank(valor) && Pattern.compile(regex).matcher(valor).find();
    }
}

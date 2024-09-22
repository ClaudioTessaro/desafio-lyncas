package com.desafio.lyncas.contas.domain.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class MessageUtil {
    private final MessageSource messageSource;

    public String getMessage(String message) {
        return messageSource.getMessage(message, new Object[]{}, Locale.getDefault());
    }

    public String getMessage(String message, Object... values) {
        return messageSource.getMessage(message, values, Locale.getDefault());
    }
}
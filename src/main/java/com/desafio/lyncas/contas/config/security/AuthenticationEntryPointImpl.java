package com.desafio.lyncas.contas.config.security;


import com.desafio.lyncas.contas.domain.exception.GeneralException;
import com.desafio.lyncas.contas.domain.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        try {
            resolver.resolveException(request, response, null, authException instanceof InsufficientAuthenticationException ?
                    new com.desafio.lyncas.contas.domain.exception.AuthenticationException(authException.getMessage()) : new UnauthorizedException(authException.getMessage()));
        } catch (Exception e) {
            throw new GeneralException(e.getMessage());
        }
    }
}

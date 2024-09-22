package com.desafio.lyncas.contas.domain.utils;

import com.desafio.lyncas.contas.config.security.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    public UserDetailsImpl getUsuario() {
        return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public String getEmail() {
        return getUsuario().getEmail();
    }

    public Long getId() {
        return getUsuario().getId();
    }
}

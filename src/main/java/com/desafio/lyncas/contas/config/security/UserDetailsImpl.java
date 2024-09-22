package com.desafio.lyncas.contas.config.security;

import com.desafio.lyncas.contas.domain.dto.organizacao.UsuarioAutenticacao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


@Data
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final Long id;
    private final String email;
    private final String nome;
    private final String userName;
    @JsonIgnore
    private final String senha;

    private final Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public static UserDetailsImpl build(UsuarioAutenticacao usuario) {
        return new UserDetailsImpl(
                usuario.id(),
                usuario.email(),
                usuario.nome(),
                usuario.email(),
                usuario.password(),
                Collections.emptyList()
        );
    }

}

package com.desafio.lyncas.contas.config.security;

import com.desafio.lyncas.contas.domain.dto.organizacao.UsuarioAutenticacao;
import com.desafio.lyncas.contas.domain.entities.Organizacao;
import com.desafio.lyncas.contas.domain.mapper.OrganizacaoMapper;
import com.desafio.lyncas.contas.domain.service.OrganizacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final OrganizacaoService organizacaoService;
    private final OrganizacaoMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Organizacao organizacao = organizacaoService.getOrganizacaoByUserName(username);
        UsuarioAutenticacao usuarioAutenticacao = mapper.mapperAuthentication(organizacao);
        return UserDetailsImpl.build(usuarioAutenticacao);
    }
}
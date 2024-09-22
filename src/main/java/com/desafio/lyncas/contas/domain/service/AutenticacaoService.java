package com.desafio.lyncas.contas.domain.service;


import com.desafio.lyncas.contas.config.security.JwtProvider;
import com.desafio.lyncas.contas.domain.dto.auth.JwtViewDTO;
import com.desafio.lyncas.contas.domain.dto.auth.LoginUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutenticacaoService {

    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    public JwtViewDTO signIn(LoginUserDTO loginUserDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserDTO.email(), loginUserDTO.senha()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.geneareteJwt(authentication);
        return new JwtViewDTO(token);
    }
}

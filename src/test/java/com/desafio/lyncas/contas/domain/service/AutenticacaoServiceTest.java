package com.desafio.lyncas.contas.domain.service;

import com.desafio.lyncas.contas.config.security.JwtProvider;
import com.desafio.lyncas.contas.domain.dto.auth.JwtViewDTO;
import com.desafio.lyncas.contas.domain.dto.auth.LoginUserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AutenticacaoServiceTest {

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AutenticacaoService autenticacaoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSignIn() {
        LoginUserDTO loginUserDTO = new LoginUserDTO("user@example.com", "password");
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtProvider.geneareteJwt(authentication)).thenReturn("mocked-jwt-token");

        JwtViewDTO jwtViewDTO = autenticacaoService.signIn(loginUserDTO);

        assertEquals("mocked-jwt-token", jwtViewDTO.token());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtProvider).geneareteJwt(authentication);
        assertEquals(authentication, SecurityContextHolder.getContext().getAuthentication());
    }
}
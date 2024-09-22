package com.desafio.lyncas.contas.domain.service;

import com.desafio.lyncas.contas.domain.dto.organizacao.CriarOrganizacaoDTO;
import com.desafio.lyncas.contas.domain.entities.Organizacao;
import com.desafio.lyncas.contas.domain.exception.BusinessException;
import com.desafio.lyncas.contas.domain.exception.NotFoundException;
import com.desafio.lyncas.contas.domain.mapper.OrganizacaoMapper;
import com.desafio.lyncas.contas.domain.repository.OrganizacaoRepository;
import com.desafio.lyncas.contas.domain.utils.ResponseSuccess;
import com.desafio.lyncas.contas.domain.utils.ResponseUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrganizacaoServiceTest {

    @Mock
    private OrganizacaoRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private OrganizacaoMapper mapper;

    @Mock
    private ResponseUtil response;

    @InjectMocks
    private OrganizacaoService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCadastrar_Success() {
        CriarOrganizacaoDTO dto = new CriarOrganizacaoDTO("Nome", "email@example.com", "Password123!", "Descricao");
        Organizacao organizacao = new Organizacao();
        organizacao.setEmail(dto.email());

        when(repository.existsByEmailIgnoreCase(dto.email())).thenReturn(false);
        when(mapper.toOrganizacao(dto)).thenReturn(organizacao);
        when(passwordEncoder.encode(dto.password())).thenReturn("encodedPassword");
        when(response.buildResponse(any())).thenReturn(new ResponseSuccess("Organização criada com sucesso.", null));

        ResponseSuccess responseSuccess = service.cadastrar(dto);

        assertNotNull(responseSuccess);
        assertEquals(response.buildResponse("organizacao.criada").mensagem(), responseSuccess.mensagem());
        verify(repository, times(1)).save(any(Organizacao.class));
    }

    @Test
    void testCadastrar_ThrowsBusinessException() {
        CriarOrganizacaoDTO dto = new CriarOrganizacaoDTO("Nome", "email@example.com", "Password123!", "Descricao");

        when(repository.existsByEmailIgnoreCase(dto.email())).thenReturn(true);

        assertThrows(BusinessException.class, () -> service.cadastrar(dto));
        verify(repository, never()).save(any(Organizacao.class));
    }

    @Test
    void testGetOrganizacaoByUserName_Success() {
        String email = "email@example.com";
        Organizacao organizacao = new Organizacao();
        organizacao.setEmail(email);

        when(repository.findByEmail(email)).thenReturn(Optional.of(organizacao));

        Organizacao result = service.getOrganizacaoByUserName(email);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }

    @Test
    void testGetOrganizacaoByUserName_ThrowsNotFoundException() {
        String email = "email@example.com";

        when(repository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.getOrganizacaoByUserName(email));
    }

    @Test
    void testGetOrganizacaoPorId_Success() {
        Long id = 1L;
        Organizacao organizacao = new Organizacao();
        organizacao.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(organizacao));

        Organizacao result = service.getOrganizacaoPorId(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void testGetOrganizacaoPorId_ThrowsNotFoundException() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.getOrganizacaoPorId(id));
    }
}
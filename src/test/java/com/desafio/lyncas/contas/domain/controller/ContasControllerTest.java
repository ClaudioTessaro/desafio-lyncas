package com.desafio.lyncas.contas.domain.controller;

import com.desafio.lyncas.contas.config.security.UserDetailsImpl;
import com.desafio.lyncas.contas.domain.dto.contas.CriarEditarContasDTO;
import com.desafio.lyncas.contas.domain.dto.contas.StatusContasDTO;
import com.desafio.lyncas.contas.domain.dto.contas.ValorPagoPeriodoDTO;
import com.desafio.lyncas.contas.domain.dto.contas.VisualizarContaDTO;
import com.desafio.lyncas.contas.domain.entities.ContasPagar;
import com.desafio.lyncas.contas.domain.entities.Organizacao;
import com.desafio.lyncas.contas.domain.enums.StatusEnum;
import com.desafio.lyncas.contas.domain.repository.ContasRepository;
import com.desafio.lyncas.contas.domain.service.ContasService;
import com.desafio.lyncas.contas.domain.service.OrganizacaoService;
import com.desafio.lyncas.contas.domain.utils.ResponseUtil;
import com.desafio.lyncas.contas.domain.utils.SecurityUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ContasIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private ContasService contasService;

    @MockBean
    private OrganizacaoService organizacaoService;

    @MockBean
    private ContasRepository repository;

    @Autowired
    private ResponseUtil responseUtil;

    @Autowired
    private SecurityUtil securityUtil;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        SecurityContextHolder.setContext(createSecurityContext());
        when(organizacaoService.getOrganizacaoPorId(anyLong())).thenReturn(mockOrganizacao());
        when(contasService.buscarValorTotalPeriodo(any(), any(), any()))
                .thenReturn(mockConta());
        when(contasService.cadastrar(any(), any()))
                .thenReturn(responseUtil.buildResponse("conta.criada"));
        when(contasService.alterarStatus(any(), any(), any()))
                .thenReturn(responseUtil.buildResponse("status.atualizado.com.sucesso"));
        when(contasService.buscarPorId(any(), any()))
                .thenReturn(mockResponse());

    }

    private VisualizarContaDTO mockResponse() {
        return new VisualizarContaDTO(1L, LocalDate.now(), LocalDate.now(), BigDecimal.valueOf(10.00), "descricao", StatusEnum.PENDENTE);
    }

    private ValorPagoPeriodoDTO mockConta() {
        return new ValorPagoPeriodoDTO(BigDecimal.TEN, 20);
    }

    private SecurityContext createSecurityContext() {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UserDetailsImpl userDetails = new UserDetailsImpl(
                1L, // id
                "user@example.com", // email
                "User Name", // nome
                "user", // userName
                "password", // senha
                Collections.emptyList() // authorities
        );
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, "password", userDetails.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }

    @Test
    void testCadastrarConta() throws Exception {
        CriarEditarContasDTO contasDTO = new CriarEditarContasDTO(
                LocalDate.now(),
                LocalDate.now(),
                BigDecimal.TEN,
                "descricao",
                StatusEnum.PENDENTE
        );

        mockMvc.perform(post("/v1/contas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(contasDTO))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mensagem").value("Conta criada com sucesso."));
    }

    private Organizacao mockOrganizacao() {
        Organizacao organizacao = new Organizacao();
        organizacao.setId(1L);
        organizacao.setEmail("org@example.com");
        return organizacao;
    }

    @Test
    void testAtualizarConta() throws Exception {
        CriarEditarContasDTO contasDTO = new CriarEditarContasDTO(
                LocalDate.now(),
                LocalDate.now(),
                BigDecimal.TEN,
                "descricao atualizada",
                StatusEnum.ATRASADO
        );

        mockMvc.perform(put("/v1/contas/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(contasDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void testAlterarStatusConta() throws Exception {
        StatusContasDTO statusContasDTO = new StatusContasDTO(StatusEnum.PAGO);

        mockMvc.perform(put("/v1/contas/alterar-status/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(statusContasDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mensagem").value("Status da conta atualizada com sucesso."));
    }

    @Test
    void testBuscarContaPorId() throws Exception {
        mockMvc.perform(get("/v1/contas/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testBuscarValorTotalPeriodo() throws Exception {
        mockMvc.perform(get("/v1/contas/valor-periodo")
                        .param("dataInicial", LocalDate.now().minusDays(10).toString())
                        .param("dataFinal", LocalDate.now().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valorPago").value(BigDecimal.TEN));
    }

    private static String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

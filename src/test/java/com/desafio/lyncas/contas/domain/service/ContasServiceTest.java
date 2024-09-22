package com.desafio.lyncas.contas.domain.service;

import com.desafio.lyncas.contas.domain.dto.contas.CriarEditarContasDTO;
import com.desafio.lyncas.contas.domain.dto.contas.StatusContasDTO;
import com.desafio.lyncas.contas.domain.dto.contas.ValorPagoPeriodoDTO;
import com.desafio.lyncas.contas.domain.dto.contas.VisualizarContaDTO;
import com.desafio.lyncas.contas.domain.entities.ContasPagar;
import com.desafio.lyncas.contas.domain.entities.Organizacao;
import com.desafio.lyncas.contas.domain.enums.StatusEnum;
import com.desafio.lyncas.contas.domain.exception.BusinessException;
import com.desafio.lyncas.contas.domain.mapper.ContasMapper;
import com.desafio.lyncas.contas.domain.repository.ContasRepository;
import com.desafio.lyncas.contas.domain.utils.ResponseSuccess;
import com.desafio.lyncas.contas.domain.utils.ResponseUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ContasServiceTest {

    @Mock
    private OrganizacaoService organizacaoService;

    @Mock
    private ContasRepository repository;

    @Mock
    private ContasMapper mapper;

    @Mock
    private ResponseUtil responseUtil;

    @InjectMocks
    private ContasService contasService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void cadastrar() {
        // Arrange
        CriarEditarContasDTO contasDTO = new CriarEditarContasDTO(LocalDate.now(), LocalDate.now(), BigDecimal.TEN, "descricao", StatusEnum.PENDENTE);
        Organizacao organizacao = mockOrganizacao();
        ContasPagar contasPagar = mockContasPagar();
        when(organizacaoService.getOrganizacaoPorId(anyLong())).thenReturn(organizacao);
        when(mapper.toEntity(any(CriarEditarContasDTO.class), any(Organizacao.class))).thenReturn(contasPagar);
        when(responseUtil.buildResponse(anyString())).thenReturn(new ResponseSuccess("Conta criada com sucesso.", null));

        // Act
        ResponseSuccess response = contasService.cadastrar(contasDTO, 1L);

        // Assert
        assertEquals(responseUtil.buildResponse("conta.criada").mensagem(), response.mensagem());
        verify(repository).save(contasPagar);
    }

    @Test
    void atualizar() {
        // Arrange
        CriarEditarContasDTO contasDTO = new CriarEditarContasDTO(LocalDate.now(), LocalDate.now(), BigDecimal.TEN, "descricao", StatusEnum.ATRASADO);
        ContasPagar contasPagar = mockContasPagar();
        when(repository.findByIdAndOrganizacao_Id(anyLong(), anyLong())).thenReturn(Optional.of(contasPagar));
        when(responseUtil.buildResponse(anyString())).thenReturn(new ResponseSuccess("Conta atualizada com sucesso.", null));

        // Act
        ResponseSuccess response = contasService.atualizar(1L, contasDTO, 1L);

        // Assert
        assertEquals(responseUtil.buildResponse("conta.atualizado.com.sucesso").mensagem(), response.mensagem());
        verify(repository).save(contasPagar);
    }

    @Test
    void alterarStatus() {
        // Arrange
        StatusContasDTO statusContasDTO = new StatusContasDTO(StatusEnum.PAGO);
        ContasPagar contasPagar = mockContasPagar();
        when(repository.findByIdAndOrganizacao_Id(anyLong(), anyLong())).thenReturn(Optional.of(contasPagar));
        when(responseUtil.buildResponse(anyString())).thenReturn(new ResponseSuccess("Status da conta atualizada com sucesso.", null));

        // Act
        ResponseSuccess response = contasService.alterarStatus(1L, statusContasDTO, 1L);

        // Assert
        assertEquals(responseUtil.buildResponse("status.atualizado.com.sucesso").mensagem(), response.mensagem());
        assertEquals(StatusEnum.PAGO, contasPagar.getStatus());
        verify(repository).save(contasPagar);
    }

    @Test
    void buscarPorId() {
        ContasPagar contasPagar = mockContasPagar();
        VisualizarContaDTO visualizarContaDTO = mockVisualizarDTO();
        when(repository.findByIdAndOrganizacao_Id(anyLong(), anyLong())).thenReturn(Optional.of(contasPagar));
        when(mapper.toDTO(any(ContasPagar.class))).thenReturn(visualizarContaDTO);

        // Act
        VisualizarContaDTO result = contasService.buscarPorId(1L, 1L);

        // Assert
        assertNotNull(result);
        assertEquals(visualizarContaDTO, result);
    }

    @Test
    void buscarPorFiltro() {
        // Arrange
        Page<ContasPagar> contasPagarPage = new PageImpl<>(Collections.singletonList(new ContasPagar()));
        when(repository.getContasPorFiltro(anyString(), any(LocalDate.class), anyLong(), any(PageRequest.class))).thenReturn(contasPagarPage);
        when(mapper.toDTO(any(ContasPagar.class))).thenReturn(mockVisualizarDTO());

        // Act
        Page<VisualizarContaDTO> result = contasService.buscarPorFiltro("descricao", LocalDate.now(), PageRequest.of(0, 10), 1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }


    @Test
    void buscarValorTotalPeriodo() {
        // Arrange
        when(repository.sumAllAccountsByPeriod(any(LocalDate.class), any(LocalDate.class), anyLong())).thenReturn(BigDecimal.TEN);

        // Act
        ValorPagoPeriodoDTO result = contasService.buscarValorTotalPeriodo(LocalDate.now().minusDays(10), LocalDate.now(), 1L);

        // Assert
        assertNotNull(result);
        assertEquals(BigDecimal.TEN, result.valorPago());
    }

    private Organizacao mockOrganizacao() {
        return new Organizacao(
                1L,
                "Organização",
                "Endereço",
                "Telefone",
                "Teste",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }


    private ContasPagar mockContasPagar() {
        return new ContasPagar(
                1L,
                LocalDate.now(),
                LocalDate.now(),
                BigDecimal.TEN,
                "descricao",
                StatusEnum.PENDENTE,
                mockOrganizacao(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    private VisualizarContaDTO mockVisualizarDTO() {
        return new VisualizarContaDTO(
                1L,
                LocalDate.now(),
                LocalDate.now(),
                BigDecimal.TEN,
                "descricao",
                StatusEnum.PENDENTE
        );
    }
}
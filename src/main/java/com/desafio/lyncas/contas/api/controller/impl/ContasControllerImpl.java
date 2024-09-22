package com.desafio.lyncas.contas.api.controller.impl;

import com.desafio.lyncas.contas.api.annotations.ApiController;
import com.desafio.lyncas.contas.api.controller.ContasController;
import com.desafio.lyncas.contas.domain.dto.contas.CriarEditarContasDTO;
import com.desafio.lyncas.contas.domain.dto.contas.StatusContasDTO;
import com.desafio.lyncas.contas.domain.dto.contas.ValorPagoPeriodoDTO;
import com.desafio.lyncas.contas.domain.dto.contas.VisualizarContaDTO;
import com.desafio.lyncas.contas.domain.service.ContasService;
import com.desafio.lyncas.contas.domain.utils.ResponseSuccess;
import com.desafio.lyncas.contas.domain.utils.ResponseUtil;
import com.desafio.lyncas.contas.domain.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@ApiController(path = "v1/contas", name = "Contas", description = "API responsavel por gerenciar as contas a pagar da empresa")
public class ContasControllerImpl implements ContasController {

    private final ContasService service;
    private final SecurityUtil securityUtil;

    @Override
    public ResponseEntity<ResponseSuccess> cadastrar(CriarEditarContasDTO contasDTO) {
        return new ResponseEntity<>(service.cadastrar(contasDTO, securityUtil.getId()), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseSuccess> atualizar(Long id, CriarEditarContasDTO contasDTO) {
        return new ResponseEntity<>(service.atualizar(id, contasDTO, securityUtil.getId()), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseSuccess> alterarStatus(Long id, StatusContasDTO statusContasDTO) {
        return new ResponseEntity<>(service.alterarStatus(id, statusContasDTO, securityUtil.getId()), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<VisualizarContaDTO> buscarPorId(Long id) {
        return new ResponseEntity<>(service.buscarPorId(id, securityUtil.getId()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Page<VisualizarContaDTO>> buscarPorFiltro(String descricao, LocalDate dataVencimento, Pageable page) {
        return new ResponseEntity<>(service.buscarPorFiltro(descricao, dataVencimento, page, securityUtil.getId()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ValorPagoPeriodoDTO> buscarValorTotalPeriodo(LocalDate dataInicial, LocalDate dataFinal) {
        return new ResponseEntity<>(service.buscarValorTotalPeriodo(dataInicial, dataFinal, securityUtil.getId()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseSuccess> importaContasAPagar(MultipartFile files) {
        return new ResponseEntity<>(service.importaContasAPagar(files, securityUtil.getId()), HttpStatus.OK);
    }


    @Override
    public void gerarCsv() {
        service.gerarCsv();
    }
}

package com.desafio.lyncas.contas.domain.service;

import com.desafio.lyncas.contas.domain.dto.contas.CriarEditarContasDTO;
import com.desafio.lyncas.contas.domain.dto.contas.StatusContasDTO;
import com.desafio.lyncas.contas.domain.dto.contas.ValorPagoPeriodoDTO;
import com.desafio.lyncas.contas.domain.dto.contas.VisualizarContaDTO;
import com.desafio.lyncas.contas.domain.entities.ContasPagar;
import com.desafio.lyncas.contas.domain.entities.Organizacao;
import com.desafio.lyncas.contas.domain.enums.StatusEnum;
import com.desafio.lyncas.contas.domain.exception.BusinessException;
import com.desafio.lyncas.contas.domain.exception.GeneralException;
import com.desafio.lyncas.contas.domain.mapper.ContasMapper;
import com.desafio.lyncas.contas.domain.repository.ContasRepository;
import com.desafio.lyncas.contas.domain.utils.ResponseSuccess;
import com.desafio.lyncas.contas.domain.utils.ResponseUtil;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ContasService {

    private final OrganizacaoService organizacaoService;
    private final ContasRepository repository;
    private final ContasMapper mapper;
    private final ResponseUtil responseUtil;

    public ResponseSuccess cadastrar(CriarEditarContasDTO contasDTO, Long id) {
        verificaSeJaExisteConta(contasDTO.dataVencimento(), contasDTO.valor(), id);
        Organizacao organizacao = organizacaoService.getOrganizacaoPorId(id);
        ContasPagar contasPagar = mapper.toEntity(contasDTO, organizacao);
        repository.save(contasPagar);
        return responseUtil.buildResponse("conta.criada");
    }

    private void verificaSeJaExisteConta(LocalDate dataVencimento, BigDecimal valor, Long id) {
        if (repository.existsByDataVencimentoAndValorAndOrganizacao_Id(dataVencimento, valor, id)) {
            throw new BusinessException("contas.ja.existe");
        }
    }

    public ResponseSuccess atualizar(Long idConta, CriarEditarContasDTO contasDTO, Long idOrganizacao) {
        ContasPagar contasPagar = repository.findByIdAndOrganizacao_Id(idConta, idOrganizacao)
                .orElseThrow(() -> new BusinessException("conta.nao.encontrada"));
        mapper.toUpdate(contasPagar, contasDTO);
        repository.save(contasPagar);
        return responseUtil.buildResponse("conta.atualizado.com.sucesso");
    }

    public ResponseSuccess alterarStatus(Long id, StatusContasDTO statusContasDTO, Long idOrganizacao) {
        ContasPagar contasPagar = repository.findByIdAndOrganizacao_Id(id, idOrganizacao)
                .orElseThrow(() -> new BusinessException("conta.nao.encontrada"));
        contasPagar.setStatus(statusContasDTO.status());
        if (statusContasDTO.status().equals(StatusEnum.PAGO)) {
            contasPagar.setDataPagamento(LocalDate.now());
        }
        repository.save(contasPagar);
        return responseUtil.buildResponse("status.atualizado.com.sucesso");
    }

    public VisualizarContaDTO buscarPorId(Long id, Long idOrganizacao) {
        ContasPagar contasPagar = repository.findByIdAndOrganizacao_Id(id, idOrganizacao)
                .orElseThrow(() -> new BusinessException("conta.nao.encontrada"));
        return mapper.toDTO(contasPagar);
    }

    public Page<VisualizarContaDTO> buscarPorFiltro(String descricao, LocalDate dataVencimento, Pageable pageable, Long idOrganizacao) {
        Page<ContasPagar> contasPagar = repository.getContasPorFiltro(descricao, dataVencimento, idOrganizacao, pageable);
        return contasPagar.map(mapper::toDTO);
    }

    public ValorPagoPeriodoDTO buscarValorTotalPeriodo(LocalDate dataInicial, LocalDate dataFinal, Long idOrganizacao) {
        BigDecimal valorPago = repository.sumAllAccountsByPeriod(dataFinal, dataInicial, idOrganizacao);
        return new ValorPagoPeriodoDTO(valorPago, Period.between(dataInicial, dataFinal).getDays());
    }

    //Como estou considerando que isso aqui é uma api simples, vou usar o saveAll da propria JPA que salva tudo numa transação só,
    // se levarmos em consideração um grande volumes de dados, o interssante seria usarmos o spring batch
    public ResponseSuccess importaContasAPagar(MultipartFile file, Long id) {
        try (InputStreamReader reader = new InputStreamReader(file.getInputStream())) {
            List<ContasPagar> contasList = new CsvToBeanBuilder<ContasPagar>(reader)
                    .withType(ContasPagar.class)
                    .build()
                    .parse();
            Organizacao organizacao = organizacaoService.getOrganizacaoPorId(id);
            for (ContasPagar conta : contasList) {
                conta.setOrganizacao(organizacao);
            }
            repository.saveAll(contasList);
            return responseUtil.buildResponse("contas.importadas.com.sucesso");
        } catch (Exception e) {
            throw new GeneralException("erro.ao.importar.contas");
        }
    }

    public void gerarCsv() {
        String[] headers = {"id", "data_vencimento", "data_pagamento", "valor", "descricao", "status"};
        String fileName = "contas_pagar.csv";
        Random random = new Random();

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.append(String.join(",", headers));
            writer.append("\n");
            for (int i = 1; i <= 200; i++) {
                writer.append(String.valueOf(i)).append(",");
                writer.append(LocalDate.now().plusDays(random.nextInt(30)).toString()).append(",");
                writer.append(LocalDate.now().plusDays(random.nextInt(30)).toString()).append(",");
                writer.append(new BigDecimal(random.nextDouble() * 1000).setScale(2, BigDecimal.ROUND_HALF_UP).toString()).append(",");
                writer.append("Descricao " + i).append(",");
                writer.append(StatusEnum.values()[random.nextInt(StatusEnum.values().length)].name());
                writer.append("\n");
            }
        } catch (IOException e) {
            throw new GeneralException("erro.ao.gerar.csv");
        }
    }
}

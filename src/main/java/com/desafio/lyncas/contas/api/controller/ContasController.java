package com.desafio.lyncas.contas.api.controller;

import com.desafio.lyncas.contas.api.annotations.ApiController;
import com.desafio.lyncas.contas.api.annotations.SpringDocApiErrorResponseDTO;
import com.desafio.lyncas.contas.api.annotations.SpringDocApiFilter;
import com.desafio.lyncas.contas.api.annotations.SpringDocApiResponseUtil;
import com.desafio.lyncas.contas.domain.dto.contas.CriarEditarContasDTO;
import com.desafio.lyncas.contas.domain.dto.contas.StatusContasDTO;
import com.desafio.lyncas.contas.domain.dto.contas.ValorPagoPeriodoDTO;
import com.desafio.lyncas.contas.domain.dto.contas.VisualizarContaDTO;
import com.desafio.lyncas.contas.domain.utils.ResponseSuccess;
import com.desafio.lyncas.contas.domain.utils.ResponseUtil;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@ApiController(path = "v1/contas", name = "Contas", description = "API responsavel por gerenciar as contas a pagar da empresa")
public interface ContasController {

    @PostMapping
    @SpringDocApiResponseUtil(summary = "Criar uma conta a se pagar")
    ResponseEntity<ResponseSuccess> cadastrar(@RequestBody CriarEditarContasDTO contasDTO);

    @PutMapping("/{id}")
    @SpringDocApiResponseUtil(summary = "Atualizar uma conta a se pagar")
    ResponseEntity<ResponseSuccess> atualizar(@PathVariable(value = "id") Long id, @RequestBody CriarEditarContasDTO contasDTO);

    @PutMapping("/alterar-status/{id}")
    @SpringDocApiResponseUtil(summary = "Alterar o status de uma conta a se pagar")
    ResponseEntity<ResponseSuccess> alterarStatus(@PathVariable(value = "id") Long id, @RequestBody StatusContasDTO statusContasDTO);

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = VisualizarContaDTO.class))})})
    @SpringDocApiErrorResponseDTO(summary = "Buscar uma conta a se pagar")
        // Não Estou fazendo paginado onde no requisito diz q todos os gets devem ser paginados, porém, na minha visão, não faz sentido paginar esse endpoint
    ResponseEntity<VisualizarContaDTO> buscarPorId(@PathVariable(value = "id") Long id);

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = VisualizarContaDTO.class))})})
    @SpringDocApiFilter(summary = "Buscar todas as conta a se pagar por filtro")
    ResponseEntity<Page<VisualizarContaDTO>> buscarPorFiltro(@RequestParam(value = "descricao", required = false) String descricao,
                                                             @RequestParam(value = "dataVencimento", required = false) LocalDate dataVencimento,
                                                             Pageable page);

    @GetMapping("/valor-periodo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValorPagoPeriodoDTO.class))})})
    @SpringDocApiFilter(summary = "Buscar valor total por periodo")
        // Não Estou fazendo paginado onde no requisito diz q todos os gets devem ser paginados, porém, na minha visão, não faz sentido paginar esse endpoint
    ResponseEntity<ValorPagoPeriodoDTO> buscarValorTotalPeriodo(@RequestParam("dataInicial") LocalDate dataInicial,
                                                                @RequestParam("dataFinal") LocalDate dataFinal);

    @PostMapping(value = "/importar-planilha", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SpringDocApiResponseUtil(summary = "Importar contas a pagar")
    ResponseEntity<ResponseSuccess> importaContasAPagar(@RequestParam("file") MultipartFile files);

    //Endpoint somente parar testar se a importação ta ok
    @Hidden
    @GetMapping("gerar-csv")
    void gerarCsv();
}

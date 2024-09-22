package com.desafio.lyncas.contas.domain.mapper;


import com.desafio.lyncas.contas.domain.dto.contas.CriarEditarContasDTO;
import com.desafio.lyncas.contas.domain.dto.contas.VisualizarContaDTO;
import com.desafio.lyncas.contas.domain.dto.organizacao.CriarOrganizacaoDTO;
import com.desafio.lyncas.contas.domain.dto.organizacao.UsuarioAutenticacao;
import com.desafio.lyncas.contas.domain.entities.ContasPagar;
import com.desafio.lyncas.contas.domain.entities.Organizacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper
public interface ContasMapper {


    @Mapping(target = "status", expression = "java(com.desafio.lyncas.contas.domain.enums.StatusEnum.PENDENTE)")
    @Mapping(target = "organizacao", source = "organizacao")
    @Mapping(target = "descricao", source = "contasDTO.descricao")
    @Mapping(target = "id", ignore = true)
    ContasPagar toEntity(CriarEditarContasDTO contasDTO, Organizacao organizacao);


    void toUpdate(@MappingTarget ContasPagar contasPagar, CriarEditarContasDTO contasDTO);

    VisualizarContaDTO toDTO(ContasPagar contasPagar);
}

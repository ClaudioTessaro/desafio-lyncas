package com.desafio.lyncas.contas.domain.mapper;


import com.desafio.lyncas.contas.domain.dto.organizacao.CriarOrganizacaoDTO;
import com.desafio.lyncas.contas.domain.dto.organizacao.UsuarioAutenticacao;
import com.desafio.lyncas.contas.domain.entities.Organizacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper
public interface OrganizacaoMapper {

    
    UsuarioAutenticacao mapperAuthentication(Organizacao org);

    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "descricao", target = "descricao")
    @Mapping(target = "id", ignore = true)
    Organizacao toOrganizacao(CriarOrganizacaoDTO criarOrganizacaoDTO);
}

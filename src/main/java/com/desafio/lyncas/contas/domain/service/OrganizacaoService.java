package com.desafio.lyncas.contas.domain.service;


import com.desafio.lyncas.contas.domain.dto.organizacao.CriarOrganizacaoDTO;
import com.desafio.lyncas.contas.domain.entities.Organizacao;
import com.desafio.lyncas.contas.domain.exception.BusinessException;
import com.desafio.lyncas.contas.domain.exception.NotFoundException;
import com.desafio.lyncas.contas.domain.mapper.OrganizacaoMapper;
import com.desafio.lyncas.contas.domain.repository.OrganizacaoRepository;
import com.desafio.lyncas.contas.domain.utils.ResponseSuccess;
import com.desafio.lyncas.contas.domain.utils.ResponseUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class OrganizacaoService {

    private final OrganizacaoRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final OrganizacaoMapper mapper;
    private final ResponseUtil response;

    @Transactional
    public ResponseSuccess cadastrar(CriarOrganizacaoDTO criarOrganizacaoDTO) {
        verificaSeJaExiste(criarOrganizacaoDTO.email());
        Organizacao organizacao = mapper.toOrganizacao(criarOrganizacaoDTO);
        organizacao.setPassword(passwordEncoder.encode(criarOrganizacaoDTO.password()));
        repository.save(organizacao);
        return response.buildResponse("organizacao.criada");
    }

    private void verificaSeJaExiste(String email) {
        if (repository.existsByEmailIgnoreCase(email)) {
            throw new BusinessException("organizacao.ja.existe");
        }
    }

    public Organizacao getOrganizacaoByUserName(String username) {
        return repository.findByEmail(username).orElseThrow(
                () -> new NotFoundException("organizacao.nao.encontrada")
        );
    }

    public Organizacao getOrganizacaoPorId(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException("organizacao.nao.encontrada")
        );
    }
}

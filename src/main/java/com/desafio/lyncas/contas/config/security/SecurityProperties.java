package com.desafio.lyncas.contas.config.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "gerenciador.security.properties", ignoreUnknownFields = false)
public class SecurityProperties {

    private String[] whitelist;
}

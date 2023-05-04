package com.fis.crm.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakClientConfiguration {

    @Value("${keycloak.auth-server-url}")
    private String authUrl;
    @Value("${keycloak.user-keycloak.username}")
    private String username;
    @Value("${keycloak.user-keycloak.password}")
    private String password;
    @Value("${keycloak.resource}")
    private String clientId;
    @Value("${keycloak.credentials.secret}")
    private String clientSecret;
    @Value("${keycloak.realm}")
    private String realm;

    @Bean
    Keycloak keycloak() {
        return KeycloakBuilder.builder()
            .realm(realm)
            .serverUrl(authUrl)
            .clientId(clientId)
            .clientSecret(clientSecret)
            .grantType(OAuth2Constants.PASSWORD)
            .username(username)
            .password(password).build();
    }

}

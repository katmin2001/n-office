package com.fis.crm.service;

import org.keycloak.admin.client.Keycloak;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class KeycloakService {

    private static final Logger logger = LoggerFactory.getLogger(KeycloakService.class);

    private final Keycloak keycloak;

    public KeycloakService(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    public String getAccessToken() throws Exception {
        return keycloak.tokenManager().getAccessTokenString();
    }

    public void removeCurrentToken(String accessToken) {
        keycloak.tokenManager().invalidate(accessToken);
    }

}

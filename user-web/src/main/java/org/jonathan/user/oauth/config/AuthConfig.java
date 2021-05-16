package org.jonathan.user.oauth.config;

public class AuthConfig {

    private String clientId;

    private String clientSecret;

    private String redirectUri;

    public String getClientId() {
        return clientId;
    }

    public AuthConfig setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public AuthConfig setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public AuthConfig setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
        return this;
    }
}

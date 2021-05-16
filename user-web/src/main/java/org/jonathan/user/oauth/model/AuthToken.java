package org.jonathan.user.oauth.model;


import java.io.Serializable;


public class AuthToken implements Serializable {
    private String accessToken;
    private int expireIn;
    private String refreshToken;
    private int refreshTokenExpireIn;
    private String uid;
    private String openId;
    private String accessCode;
    private String unionId;
    private String scope;
    private String tokenType;

    public String getAccessToken() {
        return accessToken;
    }

    public AuthToken setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public int getExpireIn() {
        return expireIn;
    }

    public AuthToken setExpireIn(int expireIn) {
        this.expireIn = expireIn;
        return this;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public AuthToken setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public int getRefreshTokenExpireIn() {
        return refreshTokenExpireIn;
    }

    public AuthToken setRefreshTokenExpireIn(int refreshTokenExpireIn) {
        this.refreshTokenExpireIn = refreshTokenExpireIn;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public AuthToken setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getOpenId() {
        return openId;
    }

    public AuthToken setOpenId(String openId) {
        this.openId = openId;
        return this;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public AuthToken setAccessCode(String accessCode) {
        this.accessCode = accessCode;
        return this;
    }

    public String getUnionId() {
        return unionId;
    }

    public AuthToken setUnionId(String unionId) {
        this.unionId = unionId;
        return this;
    }

    public String getScope() {
        return scope;
    }

    public AuthToken setScope(String scope) {
        this.scope = scope;
        return this;
    }

    public String getTokenType() {
        return tokenType;
    }

    public AuthToken setTokenType(String tokenType) {
        this.tokenType = tokenType;
        return this;
    }
}

package org.jonathan.user.oauth.model;


import java.io.Serializable;

public class AuthCallback implements Serializable {

    private String code;

    private String state;

    public String getCode() {
        return code;
    }

    public AuthCallback setCode(String code) {
        this.code = code;
        return this;
    }

    public String getState() {
        return state;
    }

    public AuthCallback setState(String state) {
        this.state = state;
        return this;
    }
}

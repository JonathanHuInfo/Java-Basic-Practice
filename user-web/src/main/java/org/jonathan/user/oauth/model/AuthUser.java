package org.jonathan.user.oauth.model;


import java.io.Serializable;


public class AuthUser<T> implements Serializable {

    private String uuid;

    private String userName;

    private String nickName;

    private String  avatar;

    private String blog;

    private String company;

    private String location;

    private String email;

    private String remark;

    private String source;

    private AuthToken token;

    private T rawUserInfo;

    public String getUuid() {
        return uuid;
    }

    public AuthUser<T> setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public AuthUser<T> setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getNickName() {
        return nickName;
    }

    public AuthUser<T> setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public AuthUser<T> setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public String getBlog() {
        return blog;
    }

    public AuthUser<T> setBlog(String blog) {
        this.blog = blog;
        return this;
    }

    public String getCompany() {
        return company;
    }

    public AuthUser<T> setCompany(String company) {
        this.company = company;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public AuthUser<T> setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AuthUser<T> setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public AuthUser<T> setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getSource() {
        return source;
    }

    public AuthUser<T> setSource(String source) {
        this.source = source;
        return this;
    }

    public AuthToken getToken() {
        return token;
    }

    public AuthUser<T> setToken(AuthToken token) {
        this.token = token;
        return this;
    }

    public T getRawUserInfo() {
        return rawUserInfo;
    }

    public AuthUser<T> setRawUserInfo(T rawUserInfo) {
        this.rawUserInfo = rawUserInfo;
        return this;
    }
}

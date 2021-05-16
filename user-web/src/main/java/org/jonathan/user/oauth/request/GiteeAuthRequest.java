package org.jonathan.user.oauth.request;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jonathan.user.oauth.config.AuthConfig;
import org.jonathan.user.oauth.config.DefaultAuthSource;
import org.jonathan.user.oauth.exception.AuthException;
import org.jonathan.user.oauth.model.AuthCallback;
import org.jonathan.user.oauth.model.AuthToken;
import org.jonathan.user.oauth.model.AuthUser;

import java.io.IOException;

/**
 * Gitee登录
 *
 * @since 1.0.0
 */
public class GiteeAuthRequest extends DefaultAuthReqesut {

    public GiteeAuthRequest(AuthConfig config) {
        super(config, DefaultAuthSource.GITEE);
    }


    @Override
    protected AuthToken getAccessToken(AuthCallback authCallback) {
        String response = doPostAuthorizationCode(authCallback.getCode());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode node = objectMapper.readTree(response);
            checkResponse(node);
            return new AuthToken()
                    .setAccessToken(node.get("access_token").asText())
                    .setRefreshToken(node.get("refresh_token").asText())
                    .setScope(node.get("scope").asText())
                    .setTokenType(node.get("token_type").asText())
                    .setExpireIn(node.get("expires_in").asInt());
        } catch (IOException e) {
            throw new AuthException(e.getMessage());
        }
    }

    @Override
    protected AuthUser getUserInfo(AuthToken authToken) {
        String userInfo = doGetUserInfo(authToken);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode node = objectMapper.readTree(userInfo);
            checkResponse(node);
            return new AuthUser()
                    .setRawUserInfo(node)
                    .setUuid(node.get("id").asText())
                    .setUserName(node.get("login").asText())
                    .setAvatar(node.get("avatar_url").asText())
                    .setBlog(node.get("blog").asText())
                    .setNickName(node.get("name").asText())
                    .setEmail(node.get("email").asText())
                    .setRemark(node.get("bio").asText())
                    .setToken(authToken)
                    .setSource(source.toString());
        } catch (IOException e) {
            throw new AuthException(e.getMessage());
        }
    }

    /**
     * 检查响应内容是否正确
     *
     * @param object 请求响应内容
     */
    private void checkResponse(JsonNode object) {
        if (object.has("error")) {
            throw new AuthException(object.get("error_description").toString());
        }
    }

}

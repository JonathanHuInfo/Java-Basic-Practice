package org.jonathan.user.oauth.request;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jonathan.rest.util.HttpUtils;
import org.jonathan.user.oauth.config.AuthConfig;
import org.jonathan.user.oauth.config.DefaultAuthSource;
import org.jonathan.user.oauth.exception.AuthException;
import org.jonathan.user.oauth.model.AuthCallback;
import org.jonathan.user.oauth.model.AuthToken;
import org.jonathan.user.oauth.model.AuthUser;
import org.jonathan.user.oauth.util.AuthUtils;

import java.io.IOException;
import java.util.Map;

/**
 * Github登录
 *
 * @since 1.0.0
 */
public class GitHubAuthRequest extends DefaultAuthReqesut {

    public GitHubAuthRequest(AuthConfig config) {
        super(config, DefaultAuthSource.GITHUB);
    }


    @Override
    protected AuthToken getAccessToken(AuthCallback authCallback) {
        String response = doPostAuthorizationCode(authCallback.getCode());
        Map<String, String> params = AuthUtils.parseStringToMap(response);
        this.checkResponse(params.containsKey("error"), params.get("error_description"));
        return new AuthToken()
                .setAccessToken(params.get("access_token"))
                .setScope(params.get("scope"))
                .setTokenType(params.get("token_type"));

    }


    @Override
    protected AuthUser getUserInfo(AuthToken authToken) {
        String url = source.userInfo();
        String userInfo = HttpUtils.get(url,"Authorization","token " + authToken.getAccessToken());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode node = objectMapper.readTree(userInfo);
            this.checkResponse(node.has("error"), node.get("error_description"));
            return new AuthUser()
                    .setRawUserInfo(node)
                    .setUuid(node.get("id").asText())
                    .setUserName(node.get("login").asText())
                    .setAvatar(node.get("avatar_url").asText())
                    .setBlog(node.get("blog").asText())
                    .setNickName(node.get("name").asText())
                    .setEmail(node.get("email").asText())
                    .setCompany(node.get("company").asText())
                    .setLocation(node.get("location").asText())
                    .setRemark(node.get("bio").asText())
                    .setToken(authToken)
                    .setSource(source.toString());
        } catch (IOException e) {
            throw new AuthException(e.getMessage());
        }

    }

    private void checkResponse(boolean error, Object errorDescription) {
        if (error) {
            throw new AuthException(errorDescription.toString());
        }
    }

}

package org.jonathan.user.web.controller;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;
import org.jonathan.user.oauth.config.AuthConfig;
import org.jonathan.user.oauth.model.AuthCallback;
import org.jonathan.user.oauth.model.AuthResponse;
import org.jonathan.user.oauth.request.AuthReqeust;
import org.jonathan.user.oauth.request.GitHubAuthRequest;
import org.jonathan.user.oauth.request.GiteeAuthRequest;
import org.jonathan.web.mvc.controller.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.io.IOException;
import java.util.logging.Logger;

@Path("/login")
public class OAuth2Controller implements Controller {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    @GET
    @Path("/gitee")
    public void loginGitee(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String result = getAuthReqeust("gitee").authorize();
        response.sendRedirect(result);
    }
    @GET
    @Path("/github")
    public void loginGithub(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String result = getAuthReqeust("github").authorize();
        response.sendRedirect(result);
    }


    private AuthReqeust getAuthReqeust(String name) {
        Config config = ConfigProviderResolver.instance().getConfig();
        String clientId = config.getValue(name + ".client_id", String.class);
        String clientSecret = config.getValue(name + ".client_secret", String.class);
        String redirectUri = config.getValue(name + ".redirect_uri", String.class);
        if ("gitee".equals(name)) {
            return new GiteeAuthRequest(new AuthConfig().setClientId(clientId).setClientSecret(clientSecret)
                    .setRedirectUri(redirectUri));
        }
        return new GitHubAuthRequest(new AuthConfig().setClientId(clientId).setClientSecret(clientSecret)
                .setRedirectUri(redirectUri));
    }

    @GET
    @Path("/oauth2/code/github")
    public String loginCallbackGithub(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        String source = "github";
        AuthReqeust authReqeust = getAuthReqeust(source);
        AuthResponse authResponse=authReqeust.login(new AuthCallback().setCode(code).setState(state));
        request.setAttribute("authResponse", authResponse);
        return "login-oauth-success.jsp";
    }

    @GET
    @Path("/oauth2/code/gitee")
    public String loginCallbackGitee(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        String source = "gitee";
        AuthReqeust authReqeust = getAuthReqeust(source);
        AuthResponse authResponse=authReqeust.login(new AuthCallback().setCode(code).setState(state));
        request.setAttribute("authResponse", authResponse);
        return "login-oauth-success.jsp";
    }

}

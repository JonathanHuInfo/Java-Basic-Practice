package org.jonathan.user.web.controller;

import org.jonathan.web.mvc.controller.PageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * @description: 登陆页面跳转
 * @author: Jonathan.Hu
 * @since:
 * @create: 2021-03-13 19:25
 **/
@Path("/login-form")
public class LoginPageController implements PageController {

    @GET
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        return "login-form.jsp";
    }
}
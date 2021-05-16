package org.jonathan.user.web.controller;

import org.jonathan.web.mvc.controller.PageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * @description: 注册页面跳转控制层
 * @author: Jonathan.Hu
 * @since:
 * @create: 2021-03-13 19:28
 **/
@Path("/")
public class RegisterPageController implements PageController {

    @GET
    @Override
    @Path("/register-form")
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        return "register-form.jsp";
    }
}
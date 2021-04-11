package org.jonathan.user.web.controller;

import org.apache.commons.lang.StringUtils;
import org.jonathan.context.ClassicComponentContext;
import org.jonathan.context.ComponentContext;
import org.jonathan.user.doman.User;
import org.jonathan.user.service.UserService;
import org.jonathan.web.mvc.controller.PageController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * @description: 登陆处理控制层
 * @author: Jonathan.Hu
 * @since:
 * @create: 2021-03-13 19:27
 **/
@Path("/register")
public class RegisterActionController implements PageController {

    private UserService userService;


    @Resource(name = "bean/UserService")
    private  UserService service;

    @POST
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        userService= ClassicComponentContext.getInstance().getComponent("bean/UserService");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        if (StringUtils.isBlank(name) || StringUtils.isBlank(password) || StringUtils.isBlank(email) || StringUtils.isBlank(phoneNumber)) {
            return "fail.jsp";
        }
        if (userService.register(new User(name, password, email, phoneNumber))) {
            return "login-form.jsp";
        }
        return "fail.jsp";
    }
}

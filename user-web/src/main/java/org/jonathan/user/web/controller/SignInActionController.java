package org.jonathan.user.web.controller;

import org.jonathan.user.doman.User;
import org.jonathan.user.service.UserService;
import org.jonathan.user.service.UserServiceImpl;
import org.jonathan.web.mvc.controller.PageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * @description: 登陆处理控制层
 * @author: Jonathan.Hu
 * @since:
 * @create: 2021-03-13 19:26
 **/
@Path("/sign-in")
public class SignInActionController implements PageController {

    private final UserService userService = new UserServiceImpl();

    @POST
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        User loginUser = userService.queryUserByNameAndPassword(name, password);
        if (loginUser != null) {
            System.out.println("Login user: " + loginUser);
            return "login-success.jsp";
        }
        return "login-fail.jsp";
    }
}
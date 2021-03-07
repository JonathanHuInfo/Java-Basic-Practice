package org.jonathan.user.web.controller;

import org.jonathan.web.mvc.controller.PageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import java.util.logging.Logger;

/**
 * @description: 注册
 * @author: Jonathan.Hu
 * @since: 1.0
 * @create: 2021-03-07 19:25
 **/
@Path("/register")
public class RegisterController implements PageController {
    private static final Logger logger = Logger.getLogger(RegisterController.class.getName());

    @Path("/register")
    @POST
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("inputEmail: " + request.getParameter("inputEmail"));
        logger.info("inputPassword: " + request.getParameter("inputPassword"));
        return "";
    }
}

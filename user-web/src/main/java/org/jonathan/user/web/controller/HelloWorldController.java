package org.jonathan.user.web.controller;

import org.jonathan.web.mvc.controller.PageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * @description:
 * @author: Jonathan.Hu
 * @since:
 * @create: 2021-03-07 12:20
 **/
@Path("/hello")
public class HelloWorldController implements PageController {

    @GET
    @Path("/world")
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        return "index.jsp";
    }


}

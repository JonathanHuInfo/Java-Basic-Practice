package org.jonathan.web.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 页面控制器，负责服务端页面渲染
 * @author: Jonathan.Hu
 * @since: 1.0
 * @create: 2021-03-07 09:32
 **/
public interface PageController extends Controller {
    String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable;
}

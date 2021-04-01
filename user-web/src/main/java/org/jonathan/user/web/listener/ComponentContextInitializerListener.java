package org.jonathan.user.web.listener;


import org.jonathan.context.ComponentContext;

import javax.servlet.*;

/**
 * {@link ComponentContext} 初始化容器
 * ContextLoaderListener
 */
public class ComponentContextInitializerListener implements ServletContextListener{
    private ServletContext servletContext;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        servletContext = sce.getServletContext();
        servletContext.log("Context initializing");
        ComponentContext context = new ComponentContext();
        context.init(servletContext);
    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        servletContext.log("Context destroying");
//        ComponentContext context = ComponentContext.getInstance();
//        context.destroy();
    }
}

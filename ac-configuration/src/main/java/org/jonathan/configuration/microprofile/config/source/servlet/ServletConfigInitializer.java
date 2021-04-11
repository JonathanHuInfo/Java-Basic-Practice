package org.jonathan.configuration.microprofile.config.source.servlet;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import java.util.ServiceLoader;
import java.util.Set;

public class ServletConfigInitializer implements ServletContainerInitializer {
  private   ServletContext servletContext;
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext servletContext) throws ServletException {
        this.servletContext=servletContext;
        // 增加 ServletContextListener
        ServiceLoader<ServletContextListener> contextListeners = ServiceLoader.load(ServletContextListener.class);
        contextListeners.forEach(this::addServletContextListener);
    }
    private  void addServletContextListener(ServletContextListener servletContextListener){
        servletContext.addListener(servletContextListener);
    }
}

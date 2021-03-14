package org.jonathan.user.web.listener;


import org.jonathan.user.sql.DBConnectionManager;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionInitializerListener implements ServletContextListener {
    private ServletContext servletContext;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        servletContext = sce.getServletContext();
        servletContext.log("Context initializing");
        try {
            Connection connection = getConnectionByJndi();
            DBConnectionManager.getInstance().setConnection(connection);
        } catch (SQLException | NamingException e) {
            servletContext.log(e.getMessage(), e);
        }
    }

    private Connection getConnectionByJndi() throws SQLException, NamingException {
        Context initContext = new InitialContext();
        Context envContext = (Context) initContext.lookup("java:comp/env");
        //依赖查找
        DataSource dataSource = (DataSource) envContext.lookup("jdbc/UserPlatformDB");
        if (initContext != null) {
            servletContext.log("获取 JNDI 数据库连接成功");
            servletContext.log("data source: " + dataSource);
        }
        return dataSource.getConnection();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        servletContext.log("Context destroying");
        DBConnectionManager.getInstance().releaseConnection();
    }
}

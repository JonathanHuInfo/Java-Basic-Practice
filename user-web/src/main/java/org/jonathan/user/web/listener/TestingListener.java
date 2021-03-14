package org.jonathan.user.web.listener;


import org.jonathan.user.context.ComponentContext;
import org.jonathan.user.sql.DBConnectionManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.logging.Logger;

/**
 * 测试用途
 */
@Deprecated
public class TestingListener implements ServletContextListener {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ComponentContext context = ComponentContext.getInstance();
        DBConnectionManager dbConnectionManager = context.getComponent("/bean/DBConnectionManager");
        dbConnectionManager.getConnection();

    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

}

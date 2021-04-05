package org.jonathan.user.web.listener;


import org.jonathan.context.ComponentContext;
import org.jonathan.user.doman.User;
import org.jonathan.user.management.MBeanHelper;
import org.jonathan.user.management.UserManager;
import org.jonathan.user.sql.DBConnectionManager;

import javax.management.ObjectName;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
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
        DBConnectionManager dbConnectionManager = context.getComponent("bean/DBConnectionManager");
        dbConnectionManager.getConnection();

        /*外部化配置START*/
        testPropertyFromServletContext(sce.getServletContext());

        testPropertyFromJNDI(context);
        /*外部化配置END*/

        testUser(dbConnectionManager.getEntityManager());
        logger.info("所有的 JNDI 组件名称：[");
        context.getComponentNames().forEach(logger::info);
        logger.info("]");

        //注册MBean
        registerMBean();
    }

    private void testPropertyFromServletContext(ServletContext servletContext) {
        String propertyName = "application.name";
        logger.info("ServletContext Property[" + propertyName + "] : "
                + servletContext.getInitParameter(propertyName));
    }

    private void registerMBean() {
        try {
            MBeanHelper.registerMBean(new UserManager(new User()),
                    new ObjectName("org.jonathan.user.doman.user.jmx:type=UserManager"));
            logger.info("registerMBean success...");
        } catch (Exception e) {
            logger.info("registerMBean throw exception, msg = " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void testUser(EntityManager entityManager) {
        User user = new User();
        user.setId(null);
        user.setName("小马哥");
        user.setPassword("******");
        user.setEmail("mercyblitz@gmail.com");
        user.setPhoneNumber("13466668888");
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(user);
        transaction.commit();
        logger.info("testInsertUser userId = " + user.getId());
        System.out.println(entityManager.find(User.class, user.getId()));
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

    private void testPropertyFromJNDI(ComponentContext context) {
        String propertyName = "maxValue";
        logger.info("JNDI Property[" + propertyName + "] : "
                + context.lookupComponent(propertyName));
    }
}

package org.jonathan.user.web.listener;


import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigBuilder;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;
import org.jonathan.context.ClassicComponentContext;
import org.jonathan.context.ComponentContext;
import org.jonathan.function.ThrowableAction;
import org.jonathan.user.doman.User;
import org.jonathan.user.management.MBeanHelper;
import org.jonathan.user.management.UserManager;
import org.jonathan.user.sql.DBConnectionManager;

import javax.jms.*;
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
        ClassicComponentContext context = ClassicComponentContext.getInstance();
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

        //外部化配置
        testMicroprofileConfig();
        //注册MBean
        registerMBean();

        ConnectionFactory connectionFactory = context.getComponent("jms/activemq-factory");
        testJms(connectionFactory);
    }

    private void testPropertyFromServletContext(ServletContext servletContext) {
        String propertyName = "application.name";
        logger.info("ServletContext Property[" + propertyName + "] : "
                + servletContext.getInitParameter(propertyName));
    }

    private void testMicroprofileConfig() {
        logger.info("read config by microprofile.config:");
        ConfigProviderResolver provider = ConfigProviderResolver.instance();

        Config providerConfig = provider.getConfig();

        ConfigBuilder builder = provider.getBuilder().addDefaultSources().addDiscoveredConverters();
        String applicationNameKey = "application.name";
        Config buildConfig = builder.build();

        String applicationName = buildConfig.getValue(applicationNameKey, String.class);
        String applicationNameByProviderConfig = providerConfig.getValue(applicationNameKey, String.class);

        Byte byteValue = buildConfig.getValue("id", Byte.class);
        Short shortValue = buildConfig.getValue("id", Short.class);

        Integer integerValue = buildConfig.getValue("id", Integer.class);
        Long longValue = buildConfig.getValue("id", Long.class);
        Boolean debug = buildConfig.getValue("debug", Boolean.class);

        Float floatValue = buildConfig.getValue("version", Float.class);
        Double doubleValue = buildConfig.getValue("version", Double.class);

        logger.info(String.format("converter string value [application.name = %s]", applicationName));
        logger.info(String.format("converter string value by providerConfig [application.name = %s]", applicationNameByProviderConfig));
        logger.info(String.format("converter byte value [id = %s]", byteValue));
        logger.info(String.format("converter short value [id = %s]", shortValue));
        logger.info(String.format("converter int value [id = %s]", integerValue));
        logger.info(String.format("converter long value [id = %s]", longValue));
        logger.info(String.format("converter boolean value [debug switch = %s]", debug));
        logger.info(String.format("converter float value [version = %s]", floatValue));
        logger.info(String.format("converter double value [version = %s]", doubleValue));
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

    private void testPropertyFromJNDI(ClassicComponentContext context) {
        String propertyName = "maxValue";
        logger.info("JNDI Property[" + propertyName + "] : "
                + context.lookupComponent(propertyName));
    }

    private void testJms(ConnectionFactory connectionFactory) {
        ThrowableAction.execute(() -> {
           testMessageProducer(connectionFactory);
            //testMessageConsumer(connectionFactory);
        });
    }

    private void testMessageProducer(ConnectionFactory connectionFactory) throws JMSException {
        // Create a Connection
        Connection connection = connectionFactory.createConnection();
        connection.start();

        // Create a Session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create the destination (Topic or Queue)
        Destination destination = session.createQueue("TEST.FOO");

        // Create a MessageProducer from the Session to the Topic or Queue
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        // Create a messages
        String text = "Hello world! From: " + Thread.currentThread().getName() + " : " + this.hashCode();
        TextMessage message = session.createTextMessage(text);

        // Tell the producer to send the message
        producer.send(message);
        System.out.printf("[Thread : %s] Sent message : %s\n", Thread.currentThread().getName(), message.getText());

        // Clean up
        session.close();
        connection.close();

    }

    private void testMessageConsumer(ConnectionFactory connectionFactory) throws JMSException {

        // Create a Connection
        Connection connection = connectionFactory.createConnection();
        connection.start();

        // Create a Session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create the destination (Topic or Queue)
        Destination destination = session.createQueue("TEST.FOO");

        // Create a MessageConsumer from the Session to the Topic or Queue
        MessageConsumer consumer = session.createConsumer(destination);

        consumer.setMessageListener(m -> {
            TextMessage tm = (TextMessage) m;
            try {
                System.out.printf("[Thread : %s] Received : %s\n", Thread.currentThread().getName(), tm.getText());
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
        });

        // Clean up
        // session.close();
        // connection.close();
    }
}

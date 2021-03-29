package org.jonathan.user.orm.jpa;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.jonathan.user.doman.User;

import javax.persistence.*;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: JpaDemo
 * @author: Jonathan.Hu
 * @since:
 * @create: 2021-03-21 07:52
 **/
public class JpaDemo  {
    @PersistenceContext(name = "emf")
    private EntityManager entityManager;

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("emf", getProperties());
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        User user = new User();
        user.setPassword("******");
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        //需要在实体上加上注解 @Entity @Table @Id @Colum 等
        entityManager.persist(user);
        transaction.commit();

        System.out.println(entityManager.find(User.class, 1L));
    }
     private static Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.DerbyDialect");
        properties.put("hibernate.id.new_generator_mappings", false);
        properties.put("hibernate.connection.datasource", getDataSource());
        return properties;
    }

    private static DataSource getDataSource() {
        EmbeddedDataSource dataSource = new EmbeddedDataSource();
        dataSource.setDatabaseName("/Users/apple/DataDisk/Workspace/logStorage/Databases/UserPlatformDB");
        dataSource.setCreateDatabase("create");
        return dataSource;
    }
}

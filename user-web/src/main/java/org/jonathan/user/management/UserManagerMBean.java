package org.jonathan.user.management;

/**
 * @description:
 * @author: Jonathan.Hu
 * @since: {@link org.jonathan.user.doman.User} MBean接口描述
 * @create: 2021-04-04 16:27
 **/
public interface UserManagerMBean {

    //MBeanAttributeInfo 列表
    Long getId();

    void setId(Long id);

    String getName();

    void setName(String name);

    String getPassword();

    void setPassword(String password);

    String getEmail();

    void setEmail(String email);

    String getPhoneNumber();

    void setPhoneNumber(String phoneNumber);

    String toString();
}

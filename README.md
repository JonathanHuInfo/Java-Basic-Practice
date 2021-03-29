# actual-fromwork

## Web启动
1. ROOT maven  clean <next> install
2. tomcat7:run

# 第一周
## 内容描述
JDBC 封装
JNDI依赖查找简单实现
## 作业描述
运行成功后，可访问 http://localhost:8080/register-form 注册用户

注册成功后跳转至登录页 http://localhost:8080/login-form 进行登录

登录成功会forward到登录成功的提示页，若用户名或密码错误则forward到错误提示页

内部使用JNDI和ServletContextListener初始化获取数据库连接
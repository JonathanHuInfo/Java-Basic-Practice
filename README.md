# actual-fromwork
## 描述
主要用于主脉路学习
## Web启动
1. ROOT maven  clean <next> install
2. tomcat7:run

# 第一周
## 内容描述
JDBC 封装
JNDI依赖查找简单实现
## 作业描述
- 运行成功后，可访问 [注册用户地址](http://localhost:8080/register-form)

- 注册成功后跳转至[登录页](http://localhost:8080/login-form) 进行登录

- 登录成功会forward到登录成功的提示页，若用户名或密码错误则forward到错误提示页

- 内部使用JNDI和ServletContextListener初始化获取数据库连接

# 第二周
## 内容描述
上下文重构 新增依赖注入信息

Bean validator
## 作业描述
问题:

- 无法在Web容器中注入、只是通过硬编码获取

# 第三周
## 内容描述
- 配置
  - 外部化配置
  - 监控 先jmx
- Logging
## 作业描述
- 集成jolokia

  - 加入Maven坐标
  - 编写MBean org.jonathan.user.management.UserManager
  - Test监听中注册

[访问MBean](http://127.0.0.1:8080/jolokia/read/org.jonathan.user.doman.user.jmx:type=UserManager)

[访问MBean name属性](http://127.0.0.1:8080/jolokia/read/org.jonathan.user.doman.user.jmx:type=UserManager/Name)

[写入MBean name属性数据](http://127.0.0.1:8080/jolokia/write/org.jonathan.user.doman.user.jmx:type=UserManager/Name/hello)

[MBean方法调用(toString)](http://127.0.0.1:8080/jolokia/exec/org.jonathan.user.doman.user.jmx:type=UserManager/toString)

- Microprofile config API 中的实现

# 第四周
## 内容描述

- maven管理
- 持续交互、持续集成
## 作业描述
- 通过SPI 动态加载`javax.servlet.ServletContextListener` 实现注入到Servlet容器监听中 摆脱对Web.xml的依赖
- Service层可以依赖注入
- Query无法查询出数据
- 没有完成 config 在mvc中的应用

# 第五周 (需要重复听)
## 内容描述
- 远程调用
- 异步服务
## 作业描述

# 第六周
## 内容描述
- 应用容器安全 (没有应用成功)
- 应用容器高可用
## 作业描述
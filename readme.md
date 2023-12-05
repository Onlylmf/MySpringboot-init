# 简单初始模板

> 功能

- SPringboot 2.7
- Mybatis
- tk-mapper
- mysql 8
- spring MVC
- Spring AOP
- Swagger + Knife4j 接口文档
- JWT
- freemarker 模板代码生成
- 。。。

> 简单用户登录注册

内置Sys_user用户表  	

登录拦截 权限验证 没有使用 shiro或者 springSecurity

使用拦截器 首次登录生成token 

使用aop进行权限验证  (简单验证 使用注解的形式 在用户表中user_role字段进行访问接口控制)




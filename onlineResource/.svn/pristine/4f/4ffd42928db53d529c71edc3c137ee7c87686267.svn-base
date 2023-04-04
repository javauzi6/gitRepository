
## 登录注册：使用Shiro实现登录

shiro是一个轻量级的权限认证系统；
shiro可以帮助我们的系统：拦截url请求，登录校验，权限控制等等；

一、使用Shiro实现登录
    1、功能预览
    2、在pom文件中，加入Shiro依赖
    3、开发Shiro的配置和登录功能
        
二、代码实战
    1、修改pom文件
        <!-- SpringBoot 整合shiro-->
        <dependency>
            <groupId>org.apache.shiro</groupId>
            <artifactId>shiro-spring</artifactId>
            <version>1.7.0</version>
        </dependency>
    2、添加：
        ShiroConfiguration.java
        ShiroContext.java
        
## 祁大聪讲编程


---
117-登录注册：后端实现登录的代码备份

//校验用户名和密码就可以了
AuthUser authUser = authUserService.getByUsername(username);
if(null == authUser){
    return JsonView.failureJson("用户名密码错误");
}

//校验密码就可以了
String authPassword = HashUtil.hmacSha256(password,authUser.getSalt());//盐加密
if(authPassword.equals(authUser.getPassword())){
    return JsonView.successJson();
}else{
    return JsonView.failureJson("用户名密码错误");
}

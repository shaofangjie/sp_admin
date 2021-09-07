# sp_admin
##之前使用actframework实现了一个后台脚手架，但是现在主流的框架还是springboot所以就用springboot+mybatis+beetl又搞了一个项目后台脚手架,权限控制未使用第三方组件，自己用注解+拦截器实现。
##项目初始化步骤：
```
1、git拉取代码到本地
2、导入项目到IDEA
3、修改/src/main/resources/application-dev.yml配置文件中的:
    druid.url=
    druid.username=
    druid.password=    
4、运行com.sp.admin.SpAdminApplication中的main方法即可启动服务
5、导入sql文件夹中的建库脚本
6、浏览器访问 http://localhost:8080/
    用户名：admin
    密码：admin
    
    注意：验证码dev模式下后台不校验，随便填写五位字符即可。

```
用户名：admin 密码：admin
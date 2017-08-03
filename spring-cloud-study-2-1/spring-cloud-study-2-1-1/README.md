### [Config Server 配置管理服务端](https://github.com/timebusker/spring-cloud-study/tree/master/spring-cloud-study-2-1/spring-cloud-study-2-1-1)

- #### 构建Config Server:通过Spring Cloud构建一个Config Server，只需要三步：
  - ##### 第一步：引入`spring-cloud-config-server`依赖，作为配置管理的服务端，引入服务端依赖
    ```
    <dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-config-server</artifactId>
	</dependency>
    ```

   - ##### 第二步：创建Spring Boot的程序主类，并添加`@EnableConfigServer`注解，开启Config Server服务
     ```
     @EnableConfigServer
     @SpringBootApplication
     public class Application {
	     public static void main(String[] args) {
		     new SpringApplicationBuilder(Application.class).web(true).run(args);
	     }
     }
     ```


   - ##### 第三部：在`application.properties`配置服务信息以及git信息
     ```  
     # 应用名称  
     spring.application.name=config-server  
     
     ###########################################################################  
     ########## git管理配置  
     ###########################################################################  
     # 配置git仓库位置
     # 占位符配置URI：{application}、{profile}、{label}这些占位符除了用于标识配置文件的规则之外，
     # 还可以用于ConfigServer 中对 Git 仓库地址的URI配置
     # spring.cloud.config.server.git.uri=http://git.net/timebusker/config-repo/{application}
     spring.cloud.config.server.git.uri=http://git.oschina.net/timebusker/spring--cloud-config-repo  
     # 配置仓库路径下的相对搜索位置，可以配置多个  
     spring.cloud.config.server.git.searchPaths=quick-cloud-config  
     ## 如果是公共仓库可以不设置账户密码  
     # 访问git仓库的用户名  
     spring.cloud.config.server.git.username=***  
     # 访问git仓库的用户密码  
     spring.cloud.config.server.git.password=***  
     
     ## Spring Cloud Config本地存储配置的方式  
     # Config Server会默认从应用的src/main/resource目录下检索配置文件   
     # spring.profiles.active=native  
     # 增加以下配置，指定配置文件的位置  
     # spring.cloud.config.server.native.searchLocations=file:F:/properties/  
     ```

- #### 服务端验证
  在[Config Repostory 配置仓库](http://git.oschina.net/timebusker/spring--cloud-config-repo)下的***quick-cloud-config***，编辑以下几个配置文件。  
  - application.properties   
  - dubbo.properties  
  - quickstart-dev.properties  
  - quickstart-prod.properties  
  - quickstart-test.properties  
  同时，为了测试版本控制，我们创建两个master和develop两个分支，并且两个分支的编辑内容不相同。  
  通过浏览器直接来访问到配置内容，具体的URL与配置文件的映射关系如下：
     + /{application}/{profile}/{label}  
     + /{application}-{profile}.yml  
     + /{label}/{application}-{profile}.yml  
     + /{application}-{profile}.properties  
     + /{label}/{application}-{profile}.properties  
       其中，`{application}-{profile}.properties`对应的配置文件，{label}对应git上不同的分支，默认为master。

- #### 总结 
   + Spring Cloud Config基于git版本管理时，当我们修改配置文件推送到git服务器后，Config Server 配置管理服务端的配置也将及时被更新，但是微服务端的配置信息不会被刷新。
   + 拉取应用配置时，当指定读取分支的配置文件`{application}-{profile}`时，也会自动加载该分支下的`{application}`配置文件。
 
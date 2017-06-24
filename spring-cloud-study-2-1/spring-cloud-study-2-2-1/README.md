### [Config 微服务端](https://github.com/timebusker/spring-cloud-study/tree/master/spring-cloud-study-1-1/spring-cloud-study-2-2-1)

- #### 构建Config 微服务端也是很简单
  - ##### 第一步：创建最基本的spring boot web 项目  
    并重建[配置类信息](https://github.com/timebusker/spring-cloud-study/tree/master/spring-cloud-study-2-1/spring-cloud-study-2-2-1/src/main/java/cn/timebusker/conf/DefinitionConfig.java)读取配置，通过restful api输出到页面。
  
  - ##### 第二步：引入`spring-cloud-config`依赖  
    `
    <dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-config</artifactId>
	</dependency>
    `
  - ##### 第三步：创建`bootstrap.properties`配置，来指定Config Server  
    `
    # 对应前配置文件中的{application}部分
    spring.application.name=didispace
    # 对应前配置文件中的{profile}部分
    spring.cloud.config.profile=dev
    # 对应前配置文件的git分支
    spring.cloud.config.label=master
    # 配置中心的地址
    spring.cloud.config.uri=http://127.0.0.1:1234/
    `

- #### 注意
  + 微服务属性必须配置在`bootstrap.properties`中，config部分内容才能被正确加载。  
    因为config的相关配置会先于`application.properties`，而`bootstrap.properties`的加载也是先于`application.properties`。
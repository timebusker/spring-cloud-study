### [单服务节点——基于一个简单的spring boot web项目实现](https://github.com/timebusker/spring-cloud-study/tree/master/spring-cloud-study-1-1/spring-cloud-study-1-1-1)
- #### 引用依赖:作为注册中心，引用eureka-server依赖
```
    <dependency>
        <groupId>org.springframework.cloud</groupId>
	    <artifactId>spring-cloud-starter-eureka-server</artifactId>
    </dependency>
```

- #### 开注册服务器
```
	/**
	 *
	 *通过@EnableEurekaServer注解启动一个服务注册中心提供给其他应用进行对话
	 */
	@EnableEurekaServer
	@SpringBootApplication
	public class Application {
		public static void main(String[] args) {
			new SpringApplicationBuilder(Application.class).web(true).run(args);
		}
	}
```

- #### eureka 核心配置

```

 ###############################################################  
 ########   eureka配置信息  
 ###############################################################  
 # 通过spring.application.name属性，我们可以指定微服务的名称后续在调用的时候只需要使用该名称就可以进行服务的访问。  
 spring.application.name=eureka-server  
 eureka.instance.hostname= ${spring.cloud.client.ipAddress}  
 # eureka是默认使用hostname进行注册，可通过一下项自动获取注册服务IP或者直接通过eureka.instance.ip-address指定IP  
 # eureka.instance.prefer-ip-address=true  
 eureka.instance.preferIpAddress=true  
 # 在eureka 服务端显示为IP   
 # 不配置，默认显示主机名  
 eureka.instance.instance-id = ${spring.cloud.client.ipAddress}:${server.port}-${spring.application.name}  
 # 是否将eureka自身作为应用注册到eureka注册中心  
 # 是否注册到eureka  
 eureka.client.register-with-eureka=false  
 # 为true时，可以启动，但报异常：Cannot execute request on any known server  
 # 是否从eureka获取注册信息  
 eureka.client.fetch-registry=false  
 # 关闭自我保护  
 # eureka.server.enable-self-preservation=false  
 # 清理间隔（单位毫秒，默认是60*1000）：作为服务端清除down了的客户端  
 eureka.server.eviction-interval-timer-in-ms=30000  
 # eureka服务器的地址（注意：地址最后面的 /eureka/ 这个是默认值）  
 # 如果有根目录，加上根目录  
 # eureka.client.serviceUrl.defaultZone=http://127.0.0.1:${server.port}/${server.context-path}/eureka/  
 eureka.client.serviceUrl.defaultZone=http://eureka.timebusker.cn:82/eureka/  
 

 # 注意：  
 # EMERGENCY! EUREKA MAY BE INCORRECTLY CLAIMING INSTANCES ARE UP WHEN THEY'RE NOT. RENEWALS ARE LESSER THAN        THRESHOLD AND   
 # HENCE THE INSTANCES ARE NOT BEING EXPIRED JUST TO BE SAFE.  
 # 分析：是由于Eureka进入了保护模式。  
 # 在保护模式下，Eureka Server将会尝试保护其服务注册表中的信息，暂时不会注销服务注册表中的服务。  
 # eureka.server.enable-self-preservation=false  

```
### 相关文章

- [Spring Cloud构建微服务架构（一）服务注册与发现](http://blog.didispace.com/springcloud1/)
- [Eureka服务注册过程详解之IpAddress](http://www.tuicool.com/articles/ueYbUnJ)	
- [spring cloud:eureka服务发现](http://blog.csdn.net/zhuchuangang/article/details/51202307)	
- [spring cloud eureka 参数配置](http://www.jianshu.com/p/e2bebfb0d075) 
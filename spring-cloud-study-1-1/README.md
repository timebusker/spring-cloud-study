----
### [Spring-Cloud 简单入门示例](https://github.com/timebusker/spring-cloud-study/tree/master/spring-cloud-study-1-1/)

#### 项目结构图
![image](https://github.com/timebusker/spring-cloud-study/raw/master/static/1-1/modle.png?raw=true)
项目分别为两个服务提供者，一个注册中心，两个消费者。其中消费者分别使用Ribbon、Feign实现负载均衡。
 
#### Spring Cloud简介--为微服务架构提供全套的解决方案
Spring Cloud是一个基于Spring Boot实现的云应用开发工具，它为基于JVM的云应用开发中的配置管理、服务发现、
断路器、智能路由、微代理、控制总线、全局锁、决策竞选、分布式会话和集群状态管理等操作提供了一种简单的开发方式。
     
Spring Cloud包含了多个子项目（针对分布式系统中涉及的多个不同开源产品），
比如：Spring Cloud Config、Spring Cloud Netflix、Spring Cloud CloudFoundry、Spring Cloud AWS、
Spring Cloud Security、Spring Cloud Commons、Spring Cloud Zookeeper、Spring Cloud CLI等项目。

#### 微服务架构
微服务架构就是将一个完整的应用从数据存储开始垂直拆分成多个不同的服务，
每个服务都能独立部署、独立维护、独立扩展，服务与服务间通过诸如RESTful API的方式互相调用。

#### 服务注册与发现
Spring Cloud Netflix，是Spring Cloud的子项目之一，主要内容是对Netflix公司一系列开源产品的包装，
它为Spring Boot应用提供了自配置的Netflix OSS整合。通过一些简单的注解，开发者就可以快速的在应用中
配置一下常用模块并构建庞大的分布式系统。它主要提供的模块包括：服务发现（Eureka），
断路器（Hystrix），智能路有（Zuul），客户端负载均衡（Ribbon）等。

##### [服务注册中心--Eureka](https://github.com/timebusker/spring-cloud-study/tree/master/spring-cloud-study-1-1/spring-cloud-study-1-1-EurekaServer/)
- 引用依赖
	```
	<dependency>
        <groupId>org.springframework.cloud</groupId>
	    <artifactId>spring-cloud-starter-eureka-server</artifactId>
    </dependency>
	
	----------------------------------------------------------------
	
	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>Camden.SR4</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>
    ```

- 开注册服务器
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

##### [服务提供者--ProvideServer](https://github.com/timebusker/spring-cloud-study/tree/master/spring-cloud-study-1-1/spring-cloud-study-1-1-ProvideServer/)
- 引用依赖--该依赖已经配置父项目中
	```
	<dependencies>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-eureka</artifactId>
		</dependency>
	</dependencies>
    ```

- 注册服务
	```
	/**
	 * 开启暴露服务
 	 * 
	 * @EnableDiscoveryClient注解能激活Eureka中的DiscoveryClient实现--实现服务发现注册
 	 */
	@SpringBootApplication
	@EnableDiscoveryClient
	public class Application {
		public static void main(String[] args) {
			new SpringApplicationBuilder(Application.class).web(true).run(args);
		}
	}

    -----------------------------------------------

	/**
	 * 暴露服务
 	 * 
	 * DiscoveryClient
 	 */
	@Autowired
	private DiscoveryClient client;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public Integer add(@RequestParam Integer a, @RequestParam Integer b) {
		ServiceInstance instance = client.getLocalServiceInstance();
		Integer r = a + b;
		logger.info("/add, host:" + instance.getHost() + ", service_id:" + instance.getServiceId() + ", port:" + instance.getPort() + ", result:" + r);
		return r;
	}
	
	```

##### [服务消费者--基于Ribbon实现负载均衡器](https://github.com/timebusker/spring-cloud-study/tree/master/spring-cloud-study-1-1/spring-cloud-study-1-1-ConsumeServer/)
- 引用依赖
	```
	<dependencies>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-ribbon</artifactId>
		</dependency>
	</dependencies>
    ```

- 发现服务
	```
	/**
	 * 开启发现服务
 	 * 
	 * @EnableDiscoveryClient注解能激活Eureka中的DiscoveryClient实现--实现服务发现注册
 	 */
	@SpringBootApplication
	@EnableDiscoveryClient
	public class RibbonApplication {
	    
		/**
	 	 * 创建RestTemplate实例，并通过@LoadBalanced注解开启均衡负载能力
	 	 * @return
	 	 */
		@Bean
		@LoadBalanced
		RestTemplate restTemplate() {
			return new RestTemplate();
		}

		public static void main(String[] args) {
			SpringApplication.run(RibbonApplication.class, args);
		}
	}

    -----------------------------------------------

	/**
	 * 消费服务
 	 * 
	 * DiscoveryClient
 	 */
	
	@Autowired
	RestTemplate restTemplate;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public Integer add(@RequestParam Integer a, @RequestParam Integer b) {
		logger.info("传递进来的参数分别为\ta=" + a + "\tb=" + b);
		Integer r = restTemplate.getForEntity("http://provide-server/add?a=" + a + "&b=" + b, Integer.class).getBody();
		return r;
	}
	```

##### [服务消费者--基于Feign实现负载均衡器](https://github.com/timebusker/spring-cloud-study/tree/master/spring-cloud-study-1-1/spring-cloud-study-1-2-ConsumeServer/)
- 引用依赖
	```
	<dependencies>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-feign</artifactId>
		</dependency>
	</dependencies>
    ```

- 发现服务
	```
	/**
	 * 开启发现服务
 	 * @EnableFeignClients注解开启Feign功能
	 * @EnableDiscoveryClient注解能激活Eureka中的DiscoveryClient实现--实现服务发现注册
 	 */
	@SpringBootApplication
	@EnableDiscoveryClient
	@EnableFeignClients
	public class FeignApplication {
		
		public static void main(String[] args) {
		SpringApplication.run(FeignApplication.class, args);
		}
	}

    -----------------------------------------------

	/**
	 * 消费服务
 	 * 
	 * DiscoveryClient
 	 */
	
	@FeignClient("provide-server")
	public interface ConsumerInterface {

		@RequestMapping(method = RequestMethod.GET, value = "/add")
    	Integer add(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") Integer b);
	
	}
	
	@Autowired
	ConsumerInterface consumer;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public Integer add(@RequestParam Integer a, @RequestParam Integer b) {
		logger.info("传递进来的参数分别为\ta=" + a + "\tb=" + b);
		Integer r =consumer.add(a, b);
		return r;
	}
	```

	
### 相关文章

- [Spring Cloud构建微服务架构（一）服务注册与发现](http://blog.didispace.com/springcloud1/)
- [Spring Cloud构建微服务架构（二）服务消费者](http://blog.didispace.com/springcloud2/)
- [Eureka服务注册过程详解之IpAddress](http://www.tuicool.com/articles/ueYbUnJ)	
- [spring cloud:eureka服务发现](http://blog.csdn.net/zhuchuangang/article/details/51202307)	
- [spring cloud eureka 参数配置](http://www.jianshu.com/p/e2bebfb0d075)		 
----
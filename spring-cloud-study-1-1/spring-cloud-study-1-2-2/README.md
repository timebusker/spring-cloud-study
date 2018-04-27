### [服务提供者](https://github.com/timebusker/spring-cloud-study/tree/master/spring-cloud-study-1-1/spring-cloud-study-1-2-1)  

- #### 引用依赖
	```

	<dependencies>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-eureka</artifactId>
		</dependency>
	</dependencies>

    ```

- #### 服务的注册与发现

	```  
   /**
 	* 服务的注册与发现
 	* 
 	* @EnableDiscoveryClient注解能激活Eureka中的DiscoveryClient实现，能够完成服务的注册和发现
 	* 
 	* 服务端可以通过@EnableDiscoveryClient注解将应用注册到注册中心，并把标注有 @RestController类下标注@RequestMapping的方法暴露出去
 	* 
 	* 客户端可以通过@EnableDiscoveryClient注解将应用注册到注册中心，并根据 serviceId（对应着spring.application.name）发现服务
 	*/
	@SpringBootApplication
	@EnableDiscoveryClient
	public class ServiceApplication {
		public static void main(String[] args) {
			new SpringApplicationBuilder(ServiceApplication.class).web(true).run(args);
		}
	}

    -----------------------------------------------

	/**
	 * DiscoveryClient服务发现的核心实现
	 * 
	 * 能够通过服务的逻辑标识符提供位置信息(例如网络地址)以及其它与已注册至Eureka的服务实例相关的元数据。
 	 */
	@Autowired
	private DiscoveryClient client;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public Integer add(@RequestParam Integer a, @RequestParam Integer b) {
        /**
		 * 通过服务的 serviceId （对应着spring.application.name）检索服务
		 * 返回的实例中包含实例相关的元数据信息
		 */
		List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
		Integer r = a + b;
		return r;
	}  
	
	```

### 相关文章

- [Spring Cloud构建微服务架构（一）服务注册与发现](http://blog.didispace.com/springcloud1/)
- [Spring Cloud构建微服务架构（二）服务消费者](http://blog.didispace.com/springcloud2/)
- [spring cloud:eureka服务发现](http://blog.csdn.net/zhuchuangang/article/details/51202307)			 


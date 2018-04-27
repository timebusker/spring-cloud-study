### [服务消费者--基于Ribbon实现负载均衡器](https://github.com/timebusker/spring-cloud-study/tree/master/spring-cloud-study-1-1/spring-cloud-study-1-1-ConsumeServer/)

- #### Ribbon
  	+ Ribbon是一个基于HTTP和TCP客户端的负载均衡器。Feign中也使用Ribbon，后续会介绍Feign的使用。
    + Ribbon可以在通过客户端中配置的ribbonServerList服务端列表去轮询访问以达到均衡负载的作用。
    + 当Ribbon与Eureka联合使用时，ribbonServerList会被DiscoveryEnabledNIWSServerList重写，扩展成从Eureka注册中心中获取服务端列表。同时它也会用NIWSDiscoveryPing来取代IPing，它将职责委托给Eureka来确定服务端是否已经启动。


- #### 引用依赖
	```

		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-ribbon</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-eureka</artifactId>
		</dependency>

    ```

- #### 服务的发现与消费
	```  
	/**
	 * 通过@EnableDiscoveryClient注解来添加发现服务能力。
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
	 * 通过直接RestTemplate来调用服务
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

### 相关文章
- [Spring Cloud构建微服务架构（二）服务消费者](http://blog.didispace.com/springcloud2/)		
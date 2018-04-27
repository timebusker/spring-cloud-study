### [服务消费者--基于Feign实现负载均衡器](https://github.com/timebusker/spring-cloud-study/tree/master/spring-cloud-study-1-1/spring-cloud-study-1-4-1/)

- #### Feign
  + Feign是一个声明式的Web Service客户端，它使得编写Web Serivce客户端变得更加简单。我们只需要使用Feign来创建一个接口并用注解来配置它既可完成。它具备可插拔的注解支持，包括Feign注解和JAX-RS注解。Feign也支持可插拔的编码器和解码器。Spring Cloud为Feign增加了对Spring MVC注解的支持，还[**整合了Ribbon和Eureka**](#)来提供均衡负载的HTTP客户端实现。  

- #### 引用依赖
	```

		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-feign</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-eureka</artifactId>
		</dependency>

    ```

- #### 服务的发现与消费

	```  

	/**
 	 * @EnableFeignClients注解开启Feign功能
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
	 * 编辑service服务接口
 	 */
	@FeignClient("provide-server")
	public interface ConsumerInterface {

		@RequestMapping(method = RequestMethod.GET, value = "/add")
    	Integer add(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") Integer b);
	
	}
	
    -----------------------------------------------
    /**
     * 注入service服务接口使用
     */
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

- [Spring Cloud构建微服务架构（二）服务消费者](http://blog.didispace.com/springcloud2/)		 



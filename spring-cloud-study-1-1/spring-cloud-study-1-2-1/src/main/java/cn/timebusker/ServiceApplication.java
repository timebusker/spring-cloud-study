package cn.timebusker;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

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
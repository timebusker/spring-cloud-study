package cn.timebusker;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 暴露服务
 * 
 * @EnableDiscoveryClient注解能激活Eureka中的DiscoveryClient实现--实现服务发现注册
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ServiceApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder(ServiceApplication.class).web(true).run(args);
	}
}
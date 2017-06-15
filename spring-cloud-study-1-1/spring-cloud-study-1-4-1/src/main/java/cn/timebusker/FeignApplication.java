package cn.timebusker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class FeignApplication {
	
	/**
	 * @EnableFeignClients注解开启Feign功能
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(FeignApplication.class, args);
	}
}

package cn.timebusker;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class App {
	
	
	public static void main(String[] args) {
		// devtools：是spring boot的一个热部署工具
		//设置 spring.devtools.restart.enabled 属性为false，可以关闭该特性.
		//System.setProperty("spring.devtools.restart.enabled","false");  
		
		// 启动Sprign Boot
		ApplicationContext ctx = SpringApplication.run(App.class, args);
		System.out.println("Let's inspect the beans provided by Spring Boot:");
		String[] beanNames = ctx.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		for (String beanName : beanNames) {
			System.out.println(beanName);
		}
	}
}

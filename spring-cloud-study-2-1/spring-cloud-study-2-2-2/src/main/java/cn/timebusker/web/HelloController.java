package cn.timebusker.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 快速启动 spring-boot
 */
@RestController
public class HelloController {

	@RequestMapping("/**")
	public String hello() {
		System.out.println("Hello Spring-Boot");
		return "Hello spring-boot-1-QuickStart！！！";
	}

}

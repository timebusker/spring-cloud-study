package cn.timebusker.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

/**
 * @RestController表明这个类为RESTful的Controller
 */
@RestController
public class ServiceController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final static String serviceId = "service";

	@Autowired
	private DiscoveryClient discoveryClient;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public Integer add(@RequestParam Integer a, @RequestParam Integer b) {
		List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
		Integer r = a + b;
		logger.info(JSON.toJSONString(instances));
		return r;
	}
	
	@RequestMapping(value = "/sub", method = RequestMethod.GET)
	public Integer sub(@RequestParam Integer a, @RequestParam Integer b) {
		Integer r = a - b;
		return r;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String addUrl() {
		return "Hello Spring-Could Service...";
	}
	
	@RequestMapping("/instance-info")
	public List<ServiceInstance> instanceInfo() {
		List<ServiceInstance> instances  = discoveryClient.getInstances(serviceId);
		logger.info(JSON.toJSONString(instances));
		return instances;
	}

	@RequestMapping("/getServices")
	public List<String> getServices() {
		List<String> services = discoveryClient.getServices();
		logger.info(JSON.toJSONString(services));
		return services;
	}
}
package cn.timebusker.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.timebusker.facade.ServiceInterface;

@RestController
public class FeignController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	ServiceInterface service;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public Integer add(@RequestParam Integer a, @RequestParam Integer b) {
		logger.info("传递进来的参数分别为\ta=" + a + "\tb=" + b);
		Integer r = service.add(a, b);
		return r;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "Hello Spring-Cloud...feign";
	}
}

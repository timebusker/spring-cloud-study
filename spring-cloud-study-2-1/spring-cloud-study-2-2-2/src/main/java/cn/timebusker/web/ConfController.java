package cn.timebusker.web;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.timebusker.conf.DefinitionConfig;

@RestController
public class ConfController {
	
	@Resource
	DefinitionConfig conf;
	
	/**
	 * 测试自定义配置属性加载
	 * @return
	 */
	@RequestMapping("/conf")
	public String getConfig() {
		System.out.println(conf.toString());
		return conf.toString();
	}
}

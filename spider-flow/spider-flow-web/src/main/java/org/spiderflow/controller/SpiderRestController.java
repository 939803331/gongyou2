package org.spiderflow.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spiderflow.core.Spider;
import org.spiderflow.core.model.SpiderFlow;
import org.spiderflow.core.service.SpiderFlowService;
import org.spiderflow.model.JsonBean;
import org.spiderflow.model.SpiderOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class SpiderRestController {
	
	private static Logger logger = LoggerFactory.getLogger(SpiderRestController.class);
	
	@Autowired
	private SpiderFlowService spiderFlowService;
	
	@Autowired
	private Spider spider;
	
	@RequestMapping("/run/{id}")
	public JsonBean<List<SpiderOutput>> run(@PathVariable("id")String id,@RequestBody(required = false)Map<String,Object> params){
		SpiderFlow flow = spiderFlowService.get(id);
		if(flow == null){
			return new JsonBean<>(0, "找不到此爬虫信息");
		}
		List<SpiderOutput> outputs = null;
		try{
			outputs = spider.run(flow,params);	
		}catch(Exception e){
			logger.error("执行爬虫失败",e);
			return new JsonBean<>(-1, "执行失败");
		}
		return new JsonBean<>(outputs);
	}
	
}

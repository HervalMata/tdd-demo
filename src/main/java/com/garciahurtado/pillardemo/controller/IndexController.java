package com.garciahurtado.pillardemo.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.garciahurtado.pillardemo.model.AdModel;
import com.garciahurtado.pillardemo.service.AdService;

@Controller
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class IndexController {
	@Autowired private AdService db;
	
	static final Logger logger = Logger.getLogger(IndexController.class); 
	
	@RequestMapping("/")
	public String index(){
		logger.info("Index controller");
		return "index";
	}
	
	@RequestMapping("/ad")
	public String adList(){
		return "List of ads";
		
	}
}

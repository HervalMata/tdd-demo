package com.garciahurtado.pillardemo.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import com.garciahurtado.pillardemo.model.AdModel;
import com.garciahurtado.pillardemo.service.AdService;

@RestController
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class IndexController {
	@Autowired private AdService db;
	
	static final Logger logger = Logger.getLogger(IndexController.class); 
	
	
	@RequestMapping("/")
	public String index(){
		return "Welcome to the homepage!";
	}
	
	@RequestMapping("/ad")
	public String adList(){
		return "List of ads";
	}
}

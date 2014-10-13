package com.garciahurtado.pillardemo.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import com.garciahurtado.pillardemo.model.AdModel;
import com.garciahurtado.pillardemo.service.AdService;

@RestController
public class IndexController {
	@Autowired private AdService db;
	
	static final Logger logger = Logger.getLogger(IndexController.class); 
	
	
	@RequestMapping("/")
	public String index(){
		AdModel newAd = new AdModel("IndexTest");
		this.db.create(newAd);
		
		AdModel ad = this.db.findById(1L);
		return "Welcome to the homepage!";
	}
	
	@RequestMapping("/companies")
	public String companies(){
		return "List of companies";
	}
}

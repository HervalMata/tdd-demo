package com.garciahurtado.pillardemo.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.garciahurtado.pillardemo.service.AdService;
import com.garciahurtado.pillardemo.service.NewspaperService;

/**
 * Base controller including common properties
 */
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class BaseController {
	@Autowired protected AdService adFinder;
	@Autowired protected NewspaperService newsFinder;

	static final Logger logger = Logger.getLogger(IndexController.class); 
}

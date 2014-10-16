package com.garciahurtado.pillardemo.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.garciahurtado.pillardemo.exception.PageNotFoundException;

@Controller
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class ErrorPageController implements ErrorController {
	static final Logger logger = Logger.getLogger(IndexController.class); 

    private static final String ERROR_PATH = "/error";
    
	@ExceptionHandler(PageNotFoundException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	public ModelAndView handleResourceNotFoundException() {
		System.out.println("In 404 handler");
	    return new ModelAndView("404");
	}
	
	@RequestMapping(ERROR_PATH)
    public String errorPage() {
        throw new PageNotFoundException(); // This is bad, since we are converting all error pages into 404s
    }
	
	@Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}

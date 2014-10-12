package com.garciahurtado.pillardemo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ads")
public class AdController {
	public String getAd(){
		return "ad";
	}
}

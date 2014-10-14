package com.garciahurtado.pillardemo.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.garciahurtado.pillardemo.model.AdModel;
import com.garciahurtado.pillardemo.model.NewspaperModel;
import com.garciahurtado.pillardemo.service.AdService;
import com.garciahurtado.pillardemo.service.NewspaperService;

@Controller
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class AdController {
	@Autowired private AdService adFinder;
	@Autowired private NewspaperService newsFinder;
	
	static final Logger logger = Logger.getLogger(IndexController.class); 
	
	@RequestMapping("/ad")
	public String index(Model model){
		List<AdModel> adList = adFinder.findAll();
		List<NewspaperModel> newspaperList = newsFinder.findAll();
		
		model.addAttribute("ads", adList);
		model.addAttribute("newspapers", newspaperList);
		
		return "ad-list";
	}
	
	/**
	 * Handle creation of a new Ad object via POST
	 * @param name Name of the new Ad
	 */
	@RequestMapping(value="/ad/create", method=RequestMethod.POST)
	public String create(
			@RequestParam("name") String name, 
			@RequestParam(value="newspapers") String[] newspapers,
			Model model){
		
		try{
			AdModel ad = new AdModel(name);
			for (String newspaperId : newspapers) {
				Long id = Long.parseLong(newspaperId, 10);
		    	ad.addNewspaper(newsFinder.findById( id ));
		    	logger.info("Added Newspaper to Ad");
			}
			
			// Persist the newly created Ad
			adFinder.create(ad);
			logger.info("New Ad Saved");
			return "redirect:/ad/";
		} catch(Exception e) {
			logger.warn("There was a problem saving the ad: " + e.getMessage());
			model.addAttribute("error", e.getMessage());
			
			return "ad-list";
		}
		
	}
}

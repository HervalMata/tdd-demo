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
public class NewspaperController extends BaseController {
	
	/**
	 * Shows a list of existing Newspapers and a form to create newspapers
	 * @param model
	 * @return
	 */
	@RequestMapping("/newspaper")
	public String index(Model model){
		List<AdModel> adList = adFinder.findAll();
		List<NewspaperModel> newspaperList = newsFinder.findAll();
		
		model.addAttribute("ads", adList);
		model.addAttribute("newspapers", newspaperList);
		
		return "newspaper-list";
	}
	
	/**
	 * Handle creation of a new newspaper object via POST
	 * @param name Name of the newspaper
	 * @param ads (optional) List of ads to associate with this newspaper
	 */
	@RequestMapping(value="/newspaper/create", method=RequestMethod.POST)
	public String create(
			@RequestParam("name") String name, 
			@RequestParam(value="ads", required = false) String[] ads,
			Model model){
		
		// TODO: Change to use validators!
		try{
			NewspaperModel newspaper = new NewspaperModel(name);
			
			if(ads != null){
				for (String newspaperId : ads) {
					Long id = Long.parseLong(newspaperId, 10);
			    	newspaper.addAd(adFinder.findById( id ));
			    	logger.info("Added Ad to Newspaper");
				}
			}
			
			// Persist the newly created Ad
			newsFinder.create(newspaper);
			logger.info("New Ad Saved");
			return "redirect:/newspaper/";
		} catch(Exception e) {
			logger.warn("There was a problem saving the newspaper: " + e.getMessage());
			model.addAttribute("error", e.getMessage());
			
			return "newspaper-list";
		}
		
	}
}

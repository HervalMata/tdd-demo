package com.garciahurtado.pillardemo.test.controller;

import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.annotation.DirtiesContext.*;
import org.springframework.test.web.servlet.MvcResult;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD) // Deletes mock data after each test to prevent test contamination
public class NewspaperControllerTest extends BaseControllerTest {
	private static final String XPATH_FORM = "//form[@action='/newspaper/create']";
	private static final String XPATH_ADS = "/select[@name='ads']";

	/**
	 * Check that we are in the right page
	 */
	@Test
	public void testCorrectPage() throws Exception {
		
		mvc.perform(get("/newspaper/"))
			.andExpect(
					status().isOk())
			.andExpect(
					content().string(containsString("<h2>Newspaper List</h2>")));
	}
	
	/**
	 * Check that the page contains no Newspapers to begin with
	 */
	@Test
	public void testNewspaperListEmpty() throws Exception {
		mvc.perform(get("/newspaper/"))
			.andExpect(
				status().isOk())
			.andExpect(
				xpath("//span[text()='There are no newspapers. Please create one.']").exists());
	}
	
	/**
	 * After adding some mock newspapers, check that the page contains a list of those models
	 */
	@Test
	public void testContainsNewspaperList() throws Exception {		
		// Insert three mock models into the DB and capture their IDs
		Long[] ids = this.createNewspapers();
		
		// xpath expression which we will use to find each mock in the response HTML 
		String xpathTest = "//table//td/a[@href='/newspaper/%s'][text()='%s']";
		
		mvc.perform(get("/newspaper/"))
			.andDo(print()) // debug HTML via console / log
			.andExpect(
					status().isOk())
			.andExpect(
					xpath(String.format(xpathTest, ids[0], this.mockNews1Name)).exists())
			.andExpect(
					xpath(String.format(xpathTest, ids[1], this.mockNews2Name)).exists())
			.andExpect(
					xpath(String.format(xpathTest, ids[2], this.mockNews3Name)).exists());
	}
	
	/**
	 * Check that the page contains a form to create a newspaper
	 */
	@Test
	public void testHasCreateNewspaperForm() throws Exception {
		mvc.perform(get("/newspaper/"))
			.andExpect(status().isOk())
			.andExpect(
				xpath(XPATH_FORM + XPATH_ADS).exists())
			.andExpect(
				xpath(XPATH_FORM + "/input[@name='name'][@type='text']").exists());
	}
	
	/**
	 * Check that the newspaper form in the page contains a list of ads to associate with. We first create
	 * some mock ads and then expect them to appear in the select element of the page.
	 */
	@Test
	public void testCreateNewspaperFormHasAds() throws Exception {
		Long[] ids = this.createAds();
		String xPath = XPATH_FORM + XPATH_ADS;
		
		mvc.perform(get("/newspaper/"))
			.andExpect(
					status().isOk())
			.andExpect(
					xpath(xPath + "/option").nodeCount(3))
			.andExpect(
					xpath(xPath + "/option[text()='" + mockAd1Name + "']").exists())
			.andExpect(
					xpath(xPath + "/option[text()='" + mockAd2Name + "']").exists())
			.andExpect(
					xpath(xPath + "/option[text()='" + mockAd3Name + "']").exists());
	}
	
	
	/**
	 * Check that we can create a newspaper with ads by submitting a POST request to the
	 * NewspaperController, then check we receive a "success" message and a valid display of the newspaper 
	 */
	@Test
	public void testCanCreateNewspaper() throws Exception {		
		Long[] ids = this.createAds();
		String newspaperName = "Newly Created Newspaper";
		
		mvc.perform(
			post("/newspaper/create") // simulate form submission
		        .param("name", newspaperName)
		        .param("ads", ids[0].toString())
		        .param("ads", ids[1].toString())	)
			.andExpect(
			    status().isMovedTemporarily());  // test redirect
			
		
		// Now, check the resulting page for the newspaper we just created (and related ads)
		mvc.perform(get("/newspaper/"))
			.andExpect(
				xpath("//table//td/a[contains(@href, '/newspaper/')][text()='" + newspaperName + "']").exists())
			.andExpect(
				xpath("//table//td/ul/li/a[contains(@href, '/ad/')][text()='" + mockAd1Name + "']").exists())
			.andExpect(
				xpath("//table//td/ul/li/a[contains(@href, '/ad/')][text()='" + mockAd2Name + "']").exists());
		
	}
}

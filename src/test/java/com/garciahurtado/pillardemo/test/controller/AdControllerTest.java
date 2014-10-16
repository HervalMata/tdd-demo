package com.garciahurtado.pillardemo.test.controller;

import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.annotation.DirtiesContext.*;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD) // Deletes mock data after each test to prevent test contamination
public class AdControllerTest extends BaseControllerTest {
	private static final String XPATH_FORM = "//form[@action='/ad/create']";
	private static final String XPATH_NEWSPAPERS = "/select[@name='newspapers']";

	/**
	 * Check that we are in the right page
	 */
	@Test
	public void testCorrectPage() throws Exception {
		
		mvc.perform(get("/ad/"))
			.andExpect(
					status().isOk())
			.andExpect(
					content().string(containsString("<h2>Ad List</h2>")));
	}
	
	/**
	 * Check that the page contains no Ads in the first place
	 */
	@Test
	public void testAdListEmpty() throws Exception {
		mvc.perform(get("/ad/"))
			.andExpect(
				status().isOk())
			.andExpect(
				xpath("//span[text()='There are no ads. Please create one.']").exists());
	}
	
	/**
	 * After adding some mock ads, check that the page contains a list of ads in a table
	 */
	@Test
	public void testContainsAdsList() throws Exception {		
		// Insert three mock Ad models into the DB and capture their IDs
		Long[] ids = this.createAds();
		
		// xpath expression which we will use to find each mock Ad in the response HTML 
		String xpathTest = "//table//td/a[@href='/ad/%s'][text()='%s']";
		
		mvc.perform(get("/ad/"))
			.andDo(print()) // debug HTML via console / log
			.andExpect(
					status().isOk())
			.andExpect(
					xpath(String.format(xpathTest, ids[0], this.mockAd1Name)).exists())
			.andExpect(
					xpath(String.format(xpathTest, ids[1], this.mockAd2Name)).exists())
			.andExpect(
					xpath(String.format(xpathTest, ids[2], this.mockAd3Name)).exists());
	}
	
	/**
	 * Check that the page contains a form to create a new Ad
	 */
	@Test
	public void testHasNewAdForm() throws Exception {
		mvc.perform(get("/ad/"))
			.andExpect(status().isOk())
			.andExpect(
				xpath(XPATH_FORM + XPATH_NEWSPAPERS).exists())
			.andExpect(
				xpath(XPATH_FORM + "/input[@name='name'][@type='text']").exists());
	}
	
	/**
	 * Check that the new ad form in the page contains a list of newspapers. We first create
	 * some mock newspapers and then expect them to appear in the select element of the page.
	 */
	@Test
	public void testNewAdFormHasNewspapers() throws Exception {
		Long[] ids = this.createNewspapers();
		String xPath = XPATH_FORM + XPATH_NEWSPAPERS;
		
		mvc.perform(get("/ad/"))
			.andExpect(
					status().isOk())
			.andExpect(
					xpath(xPath + "/option").nodeCount(3))
			.andExpect(
					xpath(xPath + "/option[text()='" + mockNews1Name + "']").exists())
			.andExpect(
					xpath(xPath + "/option[text()='" + mockNews2Name + "']").exists())
			.andExpect(
					xpath(xPath + "/option[text()='" + mockNews3Name + "']").exists());
	}
	
	
	/**
	 * Check that we can create a new ad with newspapers by submitting a POST request to the
	 * AdController, then check we receive a "success" message and a valid display of the new ad 
	 */
	@Test
	public void testCanCreateAd() throws Exception {		
		Long[] ids = this.createNewspapers();
		String adName = "Newly Created Ad";
		
		mvc.perform(
			post("/ad/create")
		        .param("name", adName)
		        .param("newspapers", ids[0].toString())
		        .param("newspapers", ids[1].toString())	)
			.andExpect(
			    status().isMovedTemporarily());  // test redirect
			
		
		// Now, check the resulting page for the Ad we just created (and associated newspapers)
		mvc.perform(get("/ad/"))
			.andExpect(
				xpath("//table//td/a[contains(@href, '/ad/')][text()='" + adName + "']").exists())
			.andExpect(
				xpath("//table//td/ul/li/a[contains(@href, '/newspaper/')][text()='" + mockNews1Name + "']").exists())
			.andExpect(
				xpath("//table//td/ul/li/a[contains(@href, '/newspaper/')][text()='" + mockNews2Name + "']").exists());
	}
}

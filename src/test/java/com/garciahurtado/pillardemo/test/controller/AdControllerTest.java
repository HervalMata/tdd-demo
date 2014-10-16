package com.garciahurtado.pillardemo.test.controller;

import java.util.Arrays;

import javax.annotation.Resource;
import javax.validation.Path.Node;
import javax.xml.namespace.NamespaceContext;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.thymeleaf.expression.Ids;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import static org.junit.Assert.*;
import static org.hamcrest.core.StringContains.containsString;
import static org.hamcrest.Matchers.*;

import org.apache.log4j.Logger;

import com.garciahurtado.pillardemo.controller.IndexController;
import com.garciahurtado.pillardemo.model.AdModel;
import com.garciahurtado.pillardemo.model.NewspaperModel;
import com.garciahurtado.pillardemo.service.AdService;
import com.garciahurtado.pillardemo.service.NewspaperService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.annotation.DirtiesContext.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-config.xml" })
@WebAppConfiguration("classpath:")
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD) // Deletes mock data after each test to prevent test contamination
public class AdControllerTest {
	private static final String XPATH_FORM = "//form[@action='/ad/create']";
	private static final String XPATH_NEWSPAPERS = "/select[@name='newspapers']";

	@Resource
	private WebApplicationContext webApplicationContext;

	@Resource
	private AdService dbAds;
	
	@Resource
	private NewspaperService dbNews;

	private MockMvc mvc;
	static final Logger logger = Logger.getLogger(IndexController.class);

	final String mockAd1Name = "Viral Ad Campaign";
	final String mockAd2Name = "Social Ad Campaign";
	final String mockAd3Name = "Cheap Online Ad Campaign";
	
	final String mockNews1Name = "Daily Gossip";
	final String mockNews2Name = "News of the World";
	final String mockNews3Name = "Finance and Money";
	
	@Before
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

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
	
	/**
	 * Insert some mock ads into the DB so that we may test that they are displayed in the page.
	 * So that we may also check REST links including their IDs, we capture and return the auto
	 * generated IDs
	 */
	protected Long[] createAds(){
		AdModel ad1 = new AdModel(this.mockAd1Name);
		AdModel ad2 = new AdModel(this.mockAd2Name);
		AdModel ad3 = new AdModel(this.mockAd3Name);
		
		dbAds.create(ad1);
		dbAds.create(ad2);
		dbAds.create(ad3);
		
		Long[] ids = new Long[]{ad1.getId(), ad2.getId(), ad3.getId()};
		return ids;
	}

	/**
	 * Insert some mock newspapers into the DB so that we may test that they are displayed in the page.
	 * Return the auto generated IDs
	 */
	protected Long[] createNewspapers(){
		NewspaperModel news1 = new NewspaperModel(this.mockNews1Name);
		NewspaperModel news2 = new NewspaperModel(this.mockNews2Name);
		NewspaperModel news3 = new NewspaperModel(this.mockNews3Name);
		
		dbNews.create(news1);
		dbNews.create(news2);
		dbNews.create(news3);
		
		Long[] ids = new Long[]{news1.getId(), news2.getId(), news3.getId()};
		return ids;
	}
}

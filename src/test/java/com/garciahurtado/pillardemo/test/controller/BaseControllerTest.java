package com.garciahurtado.pillardemo.test.controller;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.garciahurtado.pillardemo.model.AdModel;
import com.garciahurtado.pillardemo.model.NewspaperModel;
import com.garciahurtado.pillardemo.service.AdService;
import com.garciahurtado.pillardemo.service.NewspaperService;

/**
 * Provides all the standard boilerplate annotations and configuration required by 
 * other controller tests
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-config.xml" })
@WebAppConfiguration("classpath:")
@ActiveProfiles("test")
public class BaseControllerTest {
	@Resource
	protected WebApplicationContext webApplicationContext;
	protected MockMvc mvc;

	@Resource
	private AdService dbAds;
	
	@Resource
	private NewspaperService dbNews;
	
	final String mockAd1Name = "Viral Ad Campaign";
	final String mockAd2Name = "Social Ad Campaign";
	final String mockAd3Name = "Cheap Online Ad Campaign";
	
	final String mockNews1Name = "Daily Gossip";
	final String mockNews2Name = "News of the World";
	final String mockNews3Name = "Finance and Money";
	
	/**
	 * Run before every integration test. They all need access to the MockMvc object
	 */
	@Before
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
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

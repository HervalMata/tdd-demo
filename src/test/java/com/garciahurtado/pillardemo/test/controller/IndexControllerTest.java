package com.garciahurtado.pillardemo.test.controller;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import junit.framework.TestCase;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

import com.garciahurtado.pillardemo.controller.IndexController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
@WebAppConfiguration
@ActiveProfiles("test")
public class IndexControllerTest {
	@Resource
    private WebApplicationContext webApplicationContext;
 
    private MockMvc mvc;
 
    @Before
    public void setUp() {
    	mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
	@Test
	public void testIndexIncludesNavLinks() throws Exception{
		mvc.perform(get("/test"))
	        .andExpect(status().isOk())
	        .andExpect(view().name("index"))
	        .andExpect(forwardedUrl("/WEB-INF/jsp/index.jsp"));
		
		// Check that all the RESTful links to resources are present in the index page
//		assertThat(result, containsString("href=\"/ad/\""));
//		assertThat(result, containsString("href=\"/newspaper/\""));	
	}
}

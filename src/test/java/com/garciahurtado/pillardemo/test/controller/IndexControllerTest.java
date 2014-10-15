package com.garciahurtado.pillardemo.test.controller;

import java.util.Arrays;

import javax.annotation.Resource;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

import org.apache.log4j.Logger;

import com.garciahurtado.pillardemo.controller.IndexController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(locations = { "classpath:spring-config.xml" })
//@ContextConfiguration(locations = { "classpath:spring-config.xml" })
//@ActiveProfiles("test")
public class IndexControllerTest {
	@Resource
	private WebApplicationContext webApplicationContext;

	private MockMvc mvc;
	static final Logger logger = Logger.getLogger(IndexController.class);

	@Before
	public void setUp() {
//		MockitoAnnotations.initMocks(this);
//		mvc = MockMvcBuilders.standaloneSetup(new IndexController())
//                 .setViewResolvers(viewResolver())
//                 .build();
		 
		mvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext)
				.build();
	}

	@Test
	public void testIndexIncludesNavLinks() throws Exception {
		
		MvcResult result = mvc.perform(get("/").accept("*/*"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		
		String content = result.getResponse().getContentAsString();

//		logger.warn("======== Result: " + content);
//
//		// Check that all the RESTful links to resources are present in the
//		// index page
		assertThat(content, containsString("href=\"/ad/\""));
		assertThat(content, containsString("href=\"/newspaper/\""));
	}

	private InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/templates/");
		viewResolver.setSuffix(".html");
		return viewResolver;
	}
}

package com.garciahurtado.pillardemo.test.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import static org.hamcrest.core.StringContains.containsString;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-config.xml" })
@WebAppConfiguration("classpath:")
@ActiveProfiles("test")
public class IndexControllerTest extends BaseControllerTest {
	/**
	 * Check that all the resource links are present in the index page
	 */
	@Test
	public void testIndexIncludesNavLinks() throws Exception {
		
		mvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(content().string(
						containsString("href=\"/\"")))
				.andExpect(content().string(
						containsString("href=\"/ad/\"")))
				.andExpect(content().string(
						containsString("href=\"/newspaper/\"")));
	}
}

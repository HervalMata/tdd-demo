package com.garciahurtado.pillardemo.test.controller;

import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ErrorControllerTest extends BaseControllerTest {
		
	/**
	 * Test that our custom error page is working properly
	 * @throws Exception
	 */
	@Test
	public void testPageNotFound() throws Exception {
		mvc.perform(get("/not-a-valid-url"))
			.andExpect(status().isNotFound())
			.andReturn();
	}
}

package com.garciahurtado.pillardemo.test.controller;

import org.junit.Test;
import junit.framework.TestCase;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import com.garciahurtado.pillardemo.controller.IndexController;

public class IndexControllerTest extends TestCase {
	
	@Test
	public void testIndexIncludesNavLinks(){
		IndexController controller = new IndexController();
		String result = controller.index();
		
		// Check that all the RESTful links to resources are present in the index page
		assertThat(result, containsString("href=\"/ad/list\""));
		assertThat(result, containsString("href=\"/newspaper/list\""));	
	}
}

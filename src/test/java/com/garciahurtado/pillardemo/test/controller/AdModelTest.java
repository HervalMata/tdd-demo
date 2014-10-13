package com.garciahurtado.pillardemo.test.controller;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

import com.garciahurtado.pillardemo.model.AdModel;
import com.garciahurtado.pillardemo.model.NewspaperModel;
import com.garciahurtado.pillardemo.service.AdService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
@ActiveProfiles("test")
public class AdModelTest {
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Mock NewspaperModel newspaper1;
	@Mock NewspaperModel newspaper2;
	@Mock NewspaperModel newspaper3;
	
	@Resource private AdService db;
	
	String dbAdName = "TEST - DB Mock Ad Campaign"; //
	
	
	@Test
	public void testInvalidNameThrowsException(){
		exception.expect(IllegalArgumentException.class);
		AdModel ad = new AdModel("*&%^!#(");
	}
	
	@Test
	public void testCanCreate(){
		AdModel ad = new AdModel("Test Campaign");
		assertThat(ad, instanceOf(AdModel.class));
	}
	
	@Test
	public void testCanAddNewspapers(){
		AdModel ad = new AdModel("Test Campaign");
		assertEquals(ad.getNewspapers().size(), 0);
		
		ad.addNewspaper(newspaper1);
		assertEquals(ad.getNewspapers().size(), 1);
		
		ad.addNewspaper(newspaper2);
		assertEquals(ad.getNewspapers().size(), 2);
			
		ad.addNewspaper(newspaper3);
		assertEquals(ad.getNewspapers().size(), 3);
	}
		
	@Test
	public void testCanSaveAdToDb() throws Exception{
		AdModel ad = createAdModelForDB(this.dbAdName);
		assertNull(ad.getId());
		db.create(ad);
		assertEquals(ad.getName(), this.dbAdName);
		
		// TODO: Insert code to test for proper "insert" method called in data service class
	}
	
	@Test
	public void testCanFindAdInDb() throws Exception{
		AdModel savedAd = createAdModelForDB(this.dbAdName);
		db.create(savedAd);
		AdModel foundAd = db.findById(savedAd.getId());
		
		// Let's check that this is the correct object
		assertEquals(foundAd.getName(), this.dbAdName);
		assertEquals(foundAd.getNewspapers().size(), 3);
	}
	
	// Internal method for fixture creation
	private AdModel createAdModelForDB(String adName){
		AdModel ad = new AdModel(adName);
		ad.addNewspaper(newspaper1);
		ad.addNewspaper(newspaper2);
		ad.addNewspaper(newspaper3);
		
		return ad;
	}
}

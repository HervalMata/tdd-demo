package com.garciahurtado.pillardemo.test.controller;

import javax.annotation.Resource;

import junit.framework.TestCase;

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
import com.garciahurtado.pillardemo.service.NewspaperService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
@ActiveProfiles("test")
public class NewspaperModelTest {
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Mock AdModel ad1;
	@Mock AdModel ad2;
	@Mock AdModel ad3;

	@Resource private NewspaperService db;
	
	Long dbNewspaperId = 777L; // Mock AdId used to save and retrieve from DB
	String dbNewspaperName = "TEST - DB Mock Newspaper"; //
	
	@Test
	public void testInvalidNameThrowsException(){
		exception.expect(IllegalArgumentException.class);
		NewspaperModel newspaper = new NewspaperModel("*&%^!#(");
	}
	
	@Test
	public void testCanCreate(){
		NewspaperModel newspaper = new NewspaperModel("Test Newspaper");
		assertThat(newspaper, instanceOf(NewspaperModel.class));
	}
	
	@Test
	public void testCanAddAds(){
		NewspaperModel newspaper = new NewspaperModel("Test Newspaper");
		assertEquals(newspaper.getAds().size(), 0);
		
		newspaper.addAd(ad1);
		assertEquals(newspaper.getAds().size(), 1);
		
		newspaper.addAd(ad2);
		assertEquals(newspaper.getAds().size(), 2);
			
		newspaper.addAd(ad3);
		assertEquals(newspaper.getAds().size(), 3);
	}
		
	@Test
	public void testCanSaveNewspaperToDb(){
		NewspaperModel newspaper = createNewspaperModelForDB(this.dbNewspaperName);
		assertNull(newspaper.getId());
		db.create(newspaper);
		assertEquals(newspaper.getName(), this.dbNewspaperName);
	}
	
	@Test
	public void testCanFindNewspaperInDb(){
		NewspaperModel savedNewspaper = createNewspaperModelForDB(this.dbNewspaperName);
		db.create(savedNewspaper);
		NewspaperModel foundNewspaper = db.findById(savedNewspaper.getId());
		
		// Let's check that this is the correct object
		assertEquals(foundNewspaper.getId(), savedNewspaper.getId());
		assertEquals(foundNewspaper.getName(), this.dbNewspaperName);
		assertEquals(foundNewspaper.getAds().size(), 3);
		
	}
	
	// Internal method for fixture creation
	private NewspaperModel createNewspaperModelForDB(String name){
		NewspaperModel news = new NewspaperModel(name);
		news.addAd(ad1);
		news.addAd(ad2);
		news.addAd(ad3);
		
		return news;
	}
}

package com.garciahurtado.pillardemo.test.controller;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

import com.garciahurtado.pillardemo.model.AdModel;
import com.garciahurtado.pillardemo.model.NewspaperModel;
import com.garciahurtado.pillardemo.service.NewspaperService;

public class NewspaperModelTest {
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Mock AdModel ad1;
	@Mock AdModel ad2;
	@Mock AdModel ad3;
	@Mock NewspaperService db;
	
	int dbNewspaperId = 777; // Mock AdId used to save and retrieve from DB
	String dbNewspaperName = "TEST - DB Mock Newspaper"; //
	
	@Test
	public void testInvalidNameThrowsException(){
		exception.expect(IllegalArgumentException.class);
		NewspaperModel newspaper = new NewspaperModel("*&%^!#(");
	}
	
	@Test
	public void testCanCreate(){
		NewspaperModel newspaper = new NewspaperModel("Test Campaign");
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
		NewspaperModel newspaper = createNewspaperModelForDB();
		assertNull(newspaper.getId());
		db.insert(newspaper);
		assertEquals(newspaper.getId(), this.dbNewspaperId);
	}
	
	@Test
	public void testCanFindNewspaperInDb(){
		NewspaperModel newspaper = db.findById(this.dbNewspaperId);
		
		// Let's check that this is the correct object
		assertEquals(newspaper.getId(), this.dbNewspaperId);
		assertEquals(newspaper.getName(), this.dbNewspaperName);
		assertEquals(newspaper.getAds().size(), 3);
	}
	
	// Internal method for fixture creation
	private NewspaperModel createNewspaperModelForDB(){
		NewspaperModel news = new NewspaperModel("DB Newspaper");
		news.setId(this.dbNewspaperId);
		news.addAd(ad1);
		news.addAd(ad2);
		news.addAd(ad3);
		
		return news;
	}
}

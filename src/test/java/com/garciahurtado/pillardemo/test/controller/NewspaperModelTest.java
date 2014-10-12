package com.garciahurtado.pillardemo.test.controller;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

import com.garciahurtado.pillardemo.model.AdModel;
import com.garciahurtado.pillardemo.model.NewspaperModel;

public class NewspaperModelTest extends TestCase {
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Mock AdModel ad1;
	@Mock AdModel ad2;
	@Mock AdModel ad3;
	@Mock NewspaperManager db;
	
	int dbNewspaperId = 777; // Mock AdId used to save and retrieve from DB
	String dbNewspaperName = "TEST - DB Mock Newspaper"; //
	
	@Test
	public void testInvalidNameThrowsException(){
		exception.expect(IllegalArgumentException.class);
		Newspaper newspaper = new NewspaperModel("*&%^!#(");
	}
	
	@Test
	public void testCanCreate(){
		NewspaperModel news = new NewspaperModel("Test Campaign");
		assertThat(news, instanceOf(NewspaperModel.class));
	}
	
	@Test
	public void canAddAds(){
		AdModel ad = new NewspaperModel("Test Newspaper");
		assertEqual(ad.getAds().size(), 0);
		
		ad.addAd(ad1);
		assertEqual(ad.getAds().size(), 1);
		
		ad.addAd(ad2);
		assertEqual(ad.getAds().size(), 2);
			
		ad.addAd(ad3);
		assertEqual(ad.getAds().size(), 3);
	}
		
	@Test
	public void canSaveNewspaperToDb(){
		NewspaperModel newspaper = createNewspaperModelForDB();
		assertNull(newspaper.getId());
		db.insert(newspaper);
		assertEqual(newspaper.getId(), this.dbNewspaperId);
	}
	
	@Test
	public void canFindNewspaperInDb(){
		NewspaperModel newspaper = db.findById(this.dbAdId);
		
		// Let's check that this is the correct object
		assertEqual(newspaper.getId(), this.dbNewspaperId);
		assertEqual(newspaper.getName(), this.dbNewspaperName);
		assertEqual(newspaper.getAds().size(), 3);
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

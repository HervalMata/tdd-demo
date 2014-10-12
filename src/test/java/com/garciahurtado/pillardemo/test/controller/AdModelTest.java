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

public class AdModelTest extends TestCase {
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Mock NewspaperModel newspaper1;
	@Mock NewspaperModel newspaper2;
	@Mock NewspaperModel newspaper3;
	@Mock AdModelManager db;
	
	int dbAdId = 999; // Mock AdId used to save and retrieve from DB
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
	public void canAddNewspapers(){
		AdModel ad = new AdModel("Test Campaign");
		assertEqual(ad.getNewspapers().size(), 0);
		
		ad.addNewspaper(newspaper1);
		assertEqual(ad.getNewspapers().size(), 1);
		
		ad.addNewspaper(newspaper2);
		assertEqual(ad.getNewspapers().size(), 2);
			
		ad.addNewspaper(newspaper3);
		assertEqual(ad.getNewspapers().size(), 3);
	}
		
	@Test
	public void canSaveAdToDb(){
		AdModel ad = createAdModelForDB();
		assertNull(ad.getId());
		db.insert(ad);
		assertEqual(ad.getId(), this.dbAdId);
	}
	
	@Test
	public void canFindAdInDb(){
		AdModel ad = db.findById(this.dbAdId);
		
		// Let's check that this is the correct object
		assertEqual(ad.getId(), this.dbAdId);
		assertEqual(ad.getName(), this.dbAdName);
		assertEqual(ad.getNewspapers().size(), 3);
	}
	
	// Internal method for fixture creation
	private AdModel createAdModelForDB(){
		AdModel ad = new AdModel("DB Campaign");
		ad.setId(this.dbAdId);
		ad.addNewspaper(newspaper1);
		ad.addNewspaper(newspaper2);
		ad.addNewspaper(newspaper3);
		
		return ad;
	}
}

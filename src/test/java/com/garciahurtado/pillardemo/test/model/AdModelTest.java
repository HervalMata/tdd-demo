package com.garciahurtado.pillardemo.test.model;

import java.util.Collection;

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
import com.garciahurtado.pillardemo.service.NewspaperService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
@ActiveProfiles("test")
public class AdModelTest {
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Resource private AdService adDb;
	@Resource private NewspaperService newsDb;
	
	String dbAdName = "TEST - DB Mock Ad Campaign"; //

	@Test
	public void testNoNameThrowsException(){
		exception.expect(IllegalArgumentException.class);
		AdModel ad = new AdModel("");
	}
	
	@Test
	public void testEmptyNameThrowsException(){
		exception.expect(IllegalArgumentException.class);
		AdModel ad = new AdModel("  ");
	}
	
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
		
		ad.addNewspaper(createNewspaper("Newspaper 1"));
		assertEquals(ad.getNewspapers().size(), 1);
		
		ad.addNewspaper(createNewspaper("Newspaper 2"));
		assertEquals(ad.getNewspapers().size(), 2);
			
		ad.addNewspaper(createNewspaper("Newspaper 3"));
		assertEquals(ad.getNewspapers().size(), 3);
	}
		
	@Test
	public void testCanSaveAdToDb() throws Exception{
		AdModel ad = createAd(this.dbAdName);
		assertNull(ad.getId());
		adDb.create(ad);
		assertEquals(ad.getName(), this.dbAdName);
		
		// TODO: Insert code to test for proper "insert" method called in data service class
	}
	
	@Test
	public void testCanFindAdInDb() throws Exception{
		AdModel savedAd = createAd(this.dbAdName);
	
		adDb.create(savedAd);
		
		AdModel foundAd = adDb.findById(savedAd.getId());
		Collection<NewspaperModel> newspapers = foundAd.getNewspapers();
		
		// Let's check that this is the correct object
		assertEquals(this.dbAdName, foundAd.getName());
		assertEquals(3, newspapers.size());
	}
	

	/**
	 * Create Ad fixture
	 * @param name
	 * @return
	 */
	private AdModel createAd(String adName){
		AdModel ad = new AdModel(adName);
		ad.addNewspaper(createNewspaper("Newspaper 1"));
		ad.addNewspaper(createNewspaper("Newspaper 2"));
		ad.addNewspaper(createNewspaper("Newspaper 3"));
		
		return ad;
	}
	
	/**
	 * Create Newspaper fixture
	 * @param name
	 * @return
	 */
	private NewspaperModel createNewspaper(String name){
		NewspaperModel newspaper = new NewspaperModel(name);
		return newspaper;
	}
}

package com.garciahurtado.pillardemo.model;

import java.util.List;

public class NewspaperModel {
	private String name;

	public NewspaperModel(String string) {
		// TODO Auto-generated constructor stub
	}

	public void setName(String message){
		this.name = message;
	}
	
	public String getName(){
		return this.name;
	}

	public void setId(int dbNewspaperId) {
		// TODO Auto-generated method stub
		
	}
	
	public int getId() {
		// TODO Auto-generated method stub
		return 1;
	}

	public List<AdModel> getAds() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addAd(AdModel ad) {
		// TODO Auto-generated method stub
		
	}

}

package com.garciahurtado.pillardemo.models;

public class CompanyModel {
	private String name;

	public void setName(String message){
		this.name = message;
	}
	
	public String getName(){
		System.out.println("Company name is: " + this.name);
		return this.name;
	}
}

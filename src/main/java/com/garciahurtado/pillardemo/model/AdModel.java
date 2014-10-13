package com.garciahurtado.pillardemo.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="ads")
public class AdModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	private ArrayList<NewspaperModel> newspapers;
	
	public AdModel(){
		
	}
	
	public AdModel(String name){
		// TODO: externalize name regexp into custom validator 
		// Only Alphanumeric, space and dash are allowed
		if(!name.matches("^[a-zA-Z0-9-\\s]*$")){
			throw new IllegalArgumentException("The ad name can only contain alphanumeric characters, spaces or dashes");
		}
		this.name = name;
		this.newspapers = new ArrayList<NewspaperModel>();
	}

	public List<NewspaperModel> getNewspapers() {
		return newspapers;
	}

	public void addNewspaper(NewspaperModel newspaper) {
		newspapers.add(newspaper);
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setName(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}
}

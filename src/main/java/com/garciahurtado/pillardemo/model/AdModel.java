package com.garciahurtado.pillardemo.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;















import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name="ads")
public class AdModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ads_to_newspapers", joinColumns = { @JoinColumn(name ="ads_id") }, inverseJoinColumns = { @JoinColumn(name = "newspapers_id") })
	private Set<NewspaperModel> newspapers = new HashSet<NewspaperModel>();
	
	public AdModel(){
		
	}
	
	public AdModel(String name){
		if(name.trim().isEmpty()){
			throw new IllegalArgumentException("Please provide a name");
		}
		
		// TODO: externalize name regexp into custom validator 
		// Only Alphanumeric, space and dash are allowed
		if(!name.matches("^[a-zA-Z0-9-\\s]*$")){
			throw new IllegalArgumentException("The ad name can only contain alphanumeric characters, spaces or dashes");
		}
		this.name = name;
	}

	public Set<NewspaperModel> getNewspapers() {
		// Defensive copy to avoid external interference with collection
		return new HashSet<NewspaperModel>(newspapers);
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

package com.example.furniture.business;

import java.util.List;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;

import com.example.furniture.entities.Furniture;

public interface FurnitureDAO {
	
	@CommitAfter
	void save(Furniture furniture);
	
	@CommitAfter
	List<Furniture> getAllFurniture();
	
	@CommitAfter
	Furniture getById(Long id);
	
	@CommitAfter
	List<Furniture> getFurnitureByType(String type);
	
	@CommitAfter
	List<Furniture> getFurnitureByName(String name);
	
	@CommitAfter
	void editFurniture(Furniture furnitrue);
	
	@CommitAfter
	void delete(Furniture furniture);
	
	long countFurniture();
    	
}

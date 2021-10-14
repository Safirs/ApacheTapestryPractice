package com.example.furniture.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.hibernate.Session;

import com.example.furniture.business.FurnitureDAO;
import com.example.furniture.entities.Furniture;

@Import(stylesheet = "css/about.css")
public class About
{
	@Property
	private Furniture furniture;

	@Inject
	private Session session;
	
	@Inject
	private FurnitureDAO furnitureService;
	
	@Property
	private List<String> typeList;
	
	@Property
	private String type;
	
	private static final Logger logger = LogManager.getLogger(Index.class);
	
	void setupRender() {
		
		//for furniture type select
        typeList = new ArrayList<>();
        typeList.add("Chair");
        typeList.add("Table");  
    }
	
	@CommitAfter
	public void onSuccess() {
		logger.info("furniture to add: {}", furniture);
		furnitureService.save(furniture);
		logger.info("Added furniture : {}", furniture);
	}
}

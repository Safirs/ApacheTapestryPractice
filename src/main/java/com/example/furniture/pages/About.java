package com.example.furniture.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
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
	
//	@InjectComponent
//    private Form addFurnitureForm;
//	
//	@Property
//	Furniture furniture;
//	
//	@Property
//	private Long furnitureId;
//	
//	@Property
//	private String name;
//	
//	@Inject
//	private FurnitureDAO furnitureService;
//	
    @PageActivationContext
    private String learn;
    
	private static final Logger logger = LogManager.getLogger(Index.class);
//    
//    void onActivate(Long furnitureId) {
//        this.furnitureId = furnitureId;
//    }
//    
//    Long onPassivate() {
//        return furnitureId;
//    }
//    
//    void setupRender() throws Exception {
//        furniture = furnitureService.getById(furnitureId);
//        
//        if (furniture == null) {
//            throw new Exception("Sorry, there has been a problem");
//        }
//    }
	
	void setupRender() {

        typeList = new ArrayList<>();
        typeList.add("Chair");
        typeList.add("Table");
        
    }
    
	@CommitAfter
	public void onSuccess() {
		furnitureService.save(furniture);
		logger.info("Added furniture : {}", furniture);
	}
	
//	void onValidateAdd() {
//		if (newFurniture.getType() == null) {
//          
//            System.out.println("Type is empty");
//		}
//    }

    public String getLearn() {
        return learn;
    }

    public void setLearn(String learn) {
        this.learn = learn;
    }
}

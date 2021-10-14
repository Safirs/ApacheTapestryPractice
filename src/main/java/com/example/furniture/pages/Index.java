package com.example.furniture.pages;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.http.services.Request;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.HttpError;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.ajax.JavaScriptCallback;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.hibernate.Session;

import com.example.furniture.business.FurnitureDAO;
import com.example.furniture.entities.Furniture;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;


/**
 * Start page of application furniture.
 */

@Import(stylesheet = "css/modal.css")
public class Index {

	@Property
	private Furniture newFurniture;

	@Property
	@Persist
	private Furniture furniture;

	@Inject
	private Session session;
	
	@Property
	private Long furnitureId;
	
	@Property
	@Persist
	private List<String> typeList;
	
	@Property
	@Persist
	private List<String> productNameList;
	
	@Property
	@Persist
	private List <Furniture> furnitureList;
	
	@Property
	@Persist
	private String type;
	
	@Property
	@Persist
	private String productName;
	
	@InjectComponent
    private Zone furnituresZone;
	
	@Inject
	private FurnitureDAO furnitureService;

    @Property
    private String furnitureUpdateModalId = "furnitureUpdateModal";

    @InjectComponent
    private Zone modalZone;
 
	@Inject
	private Request request;
	
	@InjectComponent("furnitureEditForm")
	private BeanEditForm form;
	
	private static final Logger logger = LogManager.getLogger(Index.class);

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	@Property
	@Inject
	@Symbol(SymbolConstants.TAPESTRY_VERSION)
	private String tapestryVersion;

	@Inject
	private JavaScriptSupport javaScriptSupport;

	void setupRender() {
    	furnitureList = furnitureService.getAllFurniture();
    	logger.info("Furniture list : {}",  furnitureList);
        TreeSet<String> productNameSet = new TreeSet<String>();

        for (Furniture furniture : furnitureList) {
        	productNameSet.add(furniture.getName());
        	logger.info("Furniture name  : {}",  furniture.getName());
        }

        productNameList = new ArrayList<String>(productNameSet);
        logger.info("Product name List : {}",  productNameList);
        typeList = new ArrayList<>();
        typeList.add("Chair");
        typeList.add("Table");
    }

		public void afterRender() {
			// Give "textbox hints" to the Search field.
			// Resource : http://jumpstart.doublenegative.com.au/jumpstart/examples/javascript/reusable	
			javaScriptSupport.require("textbox-hint").with("productName", "Search by product name...", "#808080");
			javaScriptSupport.require("setSelectField");
        
		}
		

	 	void onValidateFromFilterCriteria() {
	        if (request.isXHR()) {
	            ajaxResponseRenderer.addRender(furnituresZone);
	        }
	    }
	 	
	    public List<Furniture> getFurnitures(){ 
	    	logger.info("Type is : {}", type);
	    	logger.info("Name is : {}", productName);
	    	if(type == null && productName == null) {
	    		return furnitureService.getAllFurniture();
	    	}
	    	else if(type != null) {
	    	logger.info("Executes other statement");
	    		return furnitureService.getFurnitureByType(type);
	    	}
	    	return furnitureService.getFurnitureByName(productName);
	    	//return new ArrayList<>();
	    }
	    
	    public List<Furniture> getFurnitureByType(String type){
	    	return furnitureService.getFurnitureByType(type);
	    }
	    

	    List<String> onProvideCompletionsFromProductName(String partial){
	    	 List<String> matches = new ArrayList<String>();

	         for (String name : productNameList) {
	             if (StringUtils.containsIgnoreCase(name, partial)) {
	                 matches.add(name);
	             }
	         }
	         return matches;
	    }

	    void onActionFromDelete (Furniture furniture) {
	    	furnitureService.delete(furniture);
	    }
	      
	    void onUpdate(Furniture furniture) {
	    	
	        this.furniture = furniture;
	        logger.info("from to update function : {}", furniture);

	        if (request.isXHR()) {
	            ajaxResponseRenderer.addCallback(makeScriptToShowModal());
	            ajaxResponseRenderer.addRender(modalZone);
	        }
	    }
	    
	    
	    void onValidateFromFurnitureEditForm () {
	        logger.info("from success furniture edit : {}", furniture);

	    	furnitureService.editFurniture(furniture);
	    	ajaxResponseRenderer.addRender(furnituresZone);
	    }

	    private JavaScriptCallback makeScriptToShowModal() {
	        return new JavaScriptCallback() {
	            public void run(JavaScriptSupport javascriptSupport) {
	                javaScriptSupport.require("simple-modal").invoke("activate")
	                        .with(furnitureUpdateModalId, new JSONObject());
	            }
	        };
	    }

	    private JavaScriptCallback makeScriptToHideModal() {
	        return new JavaScriptCallback() {
	            public void run(JavaScriptSupport javascriptSupport) {
	                javaScriptSupport.require("simple-modal").invoke("hide").with(furnitureUpdateModalId);
	            }
	        };
	    }

	// Handle call with an unwanted context
	Object onActivate(EventContext eventContext) {
		return eventContext.getCount() > 0 ? new HttpError(404, "Resource not found") : null;
	}
}

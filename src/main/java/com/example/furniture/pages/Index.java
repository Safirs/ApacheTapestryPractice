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
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.corelib.components.Checkbox;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
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
import com.example.furniture.util.ExceptionUtil;

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

//    @InjectComponent
//    private Zone paneZone;
//    
	@Inject
	private Request request;
	
	@InjectComponent("furnitureEditForm")
	private BeanEditForm form;
	
	private Function function;
	
	private static final Logger logger = LogManager.getLogger(Index.class);

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	@Property
	@Inject
	@Symbol(SymbolConstants.TAPESTRY_VERSION)
	private String tapestryVersion;

	@InjectPage
	private About about;

	@Inject
	private Block block;

	@Inject
	private JavaScriptSupport javaScriptSupport;

	/*
	 * @InjectComponent("filterCriteria") private Form form;
	 */
    

//	@CommitAfter
//	public void onSuccessFromAddFurniture() {
//		furnitureService.save(newFurniture);
//		logger.info("Added furniture : {}", newFurniture);
//	}
	 public enum Function {
	        REVIEW, UPDATE;
	    }
	 
	void setupRender() {
    	furnitureList = furnitureService.getAllFurniture();
    	logger.info("Furniture list : {}",  furnitureList);
        TreeSet<String> productNameSet = new TreeSet<String>();
        TreeSet<String> typeSet = new TreeSet<>();
	    typeSet.add("Chair");
	    typeSet.add("Table");

        for (Furniture furniture : furnitureList) {
        
        	productNameSet.add(furniture.getName());
        	logger.info("Furniture name  : {}",  furniture.getName());

        }

        productNameList = new ArrayList<String>(productNameSet);
        logger.info("Product name List : {}",  productNameList);
        typeList = new ArrayList<String>(typeSet);
        
    }

		public void afterRender() {
			// Give "textbox hints" to the Search field.
			// Resource : http://jumpstart.doublenegative.com.au/jumpstart/examples/javascript/reusable	
			javaScriptSupport.require("textbox-hint").with("productName", "Search by product name...", "#808080");
			javaScriptSupport.require("setSelectField");
        
		}
		
//		@OnEvent(value="action", component="type")
//		void setTypeField() {
//			javaScriptSupport.require("setSelectField").with("type");
//		}

		

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
	    
//	    public GridDataSource getFurnitures() {
//	    	return new FurnitureFilteredDataSource(typeInitial);
//	    }
//	    
	    
	    List<String> onProvideCompletionsFromProductName(String partial){
	    	 List<String> matches = new ArrayList<String>();

	         for (String name : productNameList) {
	             if (StringUtils.containsIgnoreCase(name, partial)) {
	                 matches.add(name);
	             }
	         }
	         return matches;
	    }
	    
//	    void onActionFromUpdate(Furniture furniture) {
//	    	
//	    }
	    
	    void onActionFromDelete (Furniture furniture) {
	    	furnitureService.delete(furniture);
	    }
	    
//	    void onActionFromUpdate (Furniture furniture) {
//	    	logger.info("TO UPDATE");
//
//	        this.furniture = furniture;
//	    }

//	    
	    void onUpdate(Furniture furniture) {
	    	
	        this.furniture = furniture;
	        logger.info("from to update function : {}", furniture);
	        function = Function.UPDATE;

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
//	    boolean onValidateFromFurnitureEditForm() {
////
////	        if (form.getHasErrors()) {
////	            return true;
////	        }
////
////	        try {
////	        //	logger.info("trying to edit");
////	          //  furnitureService.editFurniture(furniture);
////	        }
////	        catch (Exception e) {
////	            // Display the cause. In a real system we would try harder to get a
////	            // user-friendly message.
////	            form.recordError(ExceptionUtil.getRootCauseMessage(e));
////	        }
////
////	        return true;
//	    }

//	    void onCanceledFromFurnitureUpdate(Long furnitureId) {
//	    	this.furnitureId = furnitureId;
//	    	function = Function.REVIEW;
//
//	        populateBody();
//
//	        if (request.isXHR()) {
//	            ajaxResponseRenderer.addCallback(makeScriptToHideModal());
//	            ajaxResponseRenderer.addRender(paneZone);
//	        }
//	    }
//
//	    void onUpdatedFromFurnitureUpdate(Furniture furniture) {
//	        this.furnitureId = furniture.getId();
//	        function = Function.REVIEW;
//
//	        populateBody();
//
//	        if (request.isXHR()) {
//	            ajaxResponseRenderer.addCallback(makeScriptToHideModal());
//	            ajaxResponseRenderer.addRender(paneZone);
//	        }
//	    }

//	    public void populateBody() {
//
//	        // Get person with id 1 - ask business service to find it (from the
//	        // database).
//
//	        furniture = furnitureService.getById(furnitureId);
//
//	        if (furniture == null) {
//	            throw new IllegalStateException("Database data has not been set up!");
//	        }
//	    }

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

	    public boolean isFunction(Function function) {
	        return function == this.function;
	    }

	// Handle call with an unwanted context
	Object onActivate(EventContext eventContext) {
		return eventContext.getCount() > 0 ? new HttpError(404, "Resource not found") : null;
	}

	Object onActionFromLearnMore() {
		about.setLearn("LearnMore");

		return about;
	}

	@Log
	void onComplete() {
		logger.info("Complete call on Index page");
	}

	@Log
	void onAjax() {
		logger.info("Ajax call on Index page");

		ajaxResponseRenderer.addRender("middlezone", block);
	}

	public ZonedDateTime getCurrentTime() {
		return ZonedDateTime.now();
	}
}

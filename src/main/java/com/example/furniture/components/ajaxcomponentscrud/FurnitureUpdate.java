package com.example.furniture.components.ajaxcomponentscrud;

import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.example.furniture.business.FurnitureDAO;
import com.example.furniture.entities.Furniture;
import com.example.furniture.util.ExceptionUtil;

public class FurnitureUpdate {

	    // Screen fields

	    @Property
	    private Furniture furniture;

	    // Other pages

	    @InjectPage
	    private Furniture indexPage;

	    // Generally useful bits and pieces

	    @InjectComponent
	    private BeanEditForm form;

	    @Inject
	    private FurnitureDAO furnitureService;

	    // The code

	    void onActivate(Furniture furniture) {
	        this.furniture = furniture;
	    }

//	    Long onPassivate() {
//	        return furniture;
//	    }

	    void setupRender() {

	        // We're doing this here instead of in onPrepareForRender() because person is used outside the form...

	        // If fresh start, make sure there's a Person object available.

	        if (form.isValid()) {
	           // person = personFinderService.findPerson(personId);
	            // Handle null person in the template.
	        }

	    }
//
//	    void onPrepareForSubmit() {
//
//	        // Get Person object for the form fields to overlay.
//	        person = personFinderService.findPerson(personId);
//
//	        if (person == null) {
//	            form.recordError("Person has been deleted by another process.");
//	            // Instantiate an empty person to avoid NPE in the BeanEditForm.
//	            person = new Person();
//	        }
//	    }

	    Object onCanceled() {
	        return indexPage;
	    }

//	    void onValidateFromForm() {
//
//	        if (person.getFirstName() != null && person.getFirstName().equals("Acme")) {
//	            form.recordError("First name must not be Acme.");
//	        }
//
//	        if (personId == 2 && !person.getFirstName().equals("Mary")) {
//	            form.recordError("First Name for this person must be Mary.");
//	        }
//
//	        if (form.getHasErrors()) {
//	            return;
//	        }
//
//	        try {
//	            personManagerService.changePerson(person);
//	        }
//	        catch (Exception e) {
//	            // Display the cause. In a real system we would try harder to get a user-friendly message.
//	            form.recordError(ExceptionUtil.getRootCauseMessage(e));
//	        }
//	    }

	    Object onSuccess() {
	        return indexPage;
	    }

	
}

package edu.kea.pm.bookkeeper.uitest;

import android.widget.TextView;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;


public class LaunchBookKeeper extends UiAutomatorTestCase
{
	public void testDemo() throws UiObjectNotFoundException {   
	      
	      // Simulate a short press on the HOME button.
	      getUiDevice().pressHome();
	      
	    	  // We’re now in the home screen. Next, we want to simulate 
	    	  // a user bringing up the All Apps screen.
	    	  // If you use the uiautomatorviewer tool to capture a snapshot 
	    	  // of the Home screen, notice that the All Apps button’s 
	    	  // content-description property has the value “Apps”.  We can 
	    	  // use this property to create a UiSelector to find the button. 
	    	  UiObject allAppsButton = new UiObject(new UiSelector()
		         .description("Apps"));
	    	  
	    	  
	     if (!allAppsButton.exists()) {
	    	 getUiDevice().pressHome();
	     }
	      
	      
	      
	      // Simulate a click to bring up the All Apps screen.
	      allAppsButton.clickAndWaitForNewWindow();


	      // Next, in the apps tabs, we can simulate a user swiping until
	      // they come to the Settings app icon.  Since the container view 
	      // is scrollable, we can use a UiScrollable object.
	      UiScrollable appViews = new UiScrollable(new UiSelector()
	         .scrollable(true));
	      
	      // Set the swiping mode to horizontal (the default is vertical)
	      appViews.setAsHorizontalList();
	      
	      // Create a UiSelector to find the Settings app and simulate      
	      // a user click to launch the app. 
	      UiObject settingsApp = appViews.getChildByText(new UiSelector()
	         .className(TextView.class.getName()), 
	         "Book Keeper");
	      settingsApp.clickAndWaitForNewWindow();
	      
	      // Validate that the package name is the expected one
	      UiObject settingsValidation = new UiObject(new UiSelector()
	         .packageName("edu.kea.pm.bookkeeper"));
	      assertTrue("Unable to detect Book Keeper", 
	         settingsValidation.exists());   
	  }   
}

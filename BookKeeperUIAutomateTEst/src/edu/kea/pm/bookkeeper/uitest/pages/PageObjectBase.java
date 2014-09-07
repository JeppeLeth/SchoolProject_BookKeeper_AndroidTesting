package edu.kea.pm.bookkeeper.uitest.pages;

import android.os.SystemClock;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;

import edu.kea.pm.bookkeeper.uitest.Constants;

/**
 * Implementation of page object pattern to make dealing with UI pages easier
 * http://docs.seleniumhq.org/docs/06_test_design_considerations.jsp#page-object-design-pattern
 * 
 * The goal here is to allow us to have a base class for methods that make it easier to interact with 
 * views in the application
 */
abstract class PageObjectBase {
	private UiDevice mDevice;
	
	/**
	 * Constructor
	 * 
	 * @param device
	 */
	public PageObjectBase(UiDevice device) {
		mDevice = device;
	}
	
	protected UiDevice getDevice() {
		return mDevice;
	}
	
	/**
	 * Helper method for inputting text into an EditText field. It will click on the text field,
	 * type in the given text, and press back again to hide the keyboard
	 * 
	 * @param textField UISelector for the text field
	 * @param text The text to enter into the field
	 * @throws UiObjectNotFoundException
	 */
	protected void enterText(UiObject textField, String text) throws UiObjectNotFoundException {
		textField.click();
		textField.setText(text);
		mDevice.pressBack();
	}
	
	/**
	 * Helper method for clicking a view. It will click on the view and wait for Constants.TIMEOUT_IN_MS
	 * 
	 * @param view UiObject to click
	 * @throws UiObjectNotFoundException
	 */
	protected void clickAndWait(UiObject view) throws UiObjectNotFoundException {
		view.clickAndWaitForNewWindow(Constants.TIMEOUT_IN_MS);
	}
	
	protected void clearTextField(UiObject editText) throws UiObjectNotFoundException {
	    editText.longClick();
	    SystemClock.sleep(250);
	    mDevice.pressDelete();
	    mDevice.pressBack();
	}
}

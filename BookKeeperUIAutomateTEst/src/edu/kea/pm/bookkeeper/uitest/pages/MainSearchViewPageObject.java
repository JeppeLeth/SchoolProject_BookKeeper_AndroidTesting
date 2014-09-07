package edu.kea.pm.bookkeeper.uitest.pages;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;

/**
 * A page object handling interactions with the main search for isbn view
 */
public class MainSearchViewPageObject extends PageObjectBase {
	private UiObject isbnField = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/isbnEditText"));
	private UiObject scanButton = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/buttonScan"));
	private UiObject loopUpButton = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/buttonLookUp"));
	
	public MainSearchViewPageObject(UiDevice device) {
		super(device);
	}
	
	public void enterText(String text) throws UiObjectNotFoundException {
		enterText(isbnField, text);
	}
	
	public void clearText() throws UiObjectNotFoundException {
		clearTextField(isbnField);
	}
	
	public void enterTextAndLoopUp(String message) throws UiObjectNotFoundException {
		enterText(isbnField, message);
		clickAndWait(loopUpButton);
	}
	
	public void scan() throws UiObjectNotFoundException {
		clickAndWait(scanButton);
	}
	
	public String getIsbnText() throws UiObjectNotFoundException{
		return isbnField.getText();
	}
	
	
}

package edu.kea.pm.bookkeeper.uitest.pages;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;

import edu.kea.pm.bookkeeper.uitest.Constants;

/**
 * A page object representing all the actions we can do with the menu
 */
public class MenuViewPageObject extends PageObjectBase {
	
	private static final String HOME_HEADLINE = "Look up book";
	
	private UiObject mActionBarTitle = new UiObject(new UiSelector().resourceId("android:id/action_bar_title"));
	private UiObject mAlertTitle = new UiObject(new UiSelector().resourceId("android:id/alertTitle"));
	private UiObject mAlertOK = new UiObject(new UiSelector().resourceId("android:id/button2"));
	private UiObject mLeftDrawer = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/left_drawer"));
	private UiObject isbnField = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/isbnEditText"));
    
	private UiObject mMenuHomeButton = new UiObject(new UiSelector().resourceId("android:id/home"));
	
	/**
	 * Constructor
	 * 
	 * @param device
	 */
	public MenuViewPageObject(UiDevice device) {
		super(device);
	}

	public void openHome() throws UiObjectNotFoundException {
		if (!mActionBarTitle.exists() || !mActionBarTitle.getText().equalsIgnoreCase(HOME_HEADLINE)) {
			navigateBackToParent();
			clickAndWait(mLeftDrawer.getChild(new UiSelector().clickable(true).index(0)));
		}
		if (!isbnField.getText().equalsIgnoreCase(Constants.TEXT_HINT)) {
			clearTextField(isbnField);
		}
	}
	
	
	/**
	 * Open the My Books view
	 * @throws UiObjectNotFoundException
	 */
	public void openMyBooks() throws UiObjectNotFoundException {
		mMenuHomeButton.clickAndWaitForNewWindow(Constants.TIMEOUT_IN_MS);
		mLeftDrawer.getChild(new UiSelector().clickable(true).index(1)).clickAndWaitForNewWindow(Constants.TIMEOUT_IN_MS);
	}
	
	/**
	 * Open the look up / scan view
	 * 
	 * @throws UiObjectNotFoundException
	 */
	public void openLookUp() throws UiObjectNotFoundException {
		mMenuHomeButton.clickAndWaitForNewWindow(Constants.TIMEOUT_IN_MS);
		mLeftDrawer.getChild(new UiSelector().clickable(true).index(0)).clickAndWaitForNewWindow(Constants.TIMEOUT_IN_MS);
	}
	
	public void toggleLeftDrawer(boolean open) throws UiObjectNotFoundException {
		if (open != isLeftDrawerOpen()) {
			mMenuHomeButton.clickAndWaitForNewWindow(Constants.TIMEOUT_IN_MS);
		}
	}
	
	public boolean isLeftDrawerOpen() throws UiObjectNotFoundException {
		return mLeftDrawer.exists();
	}
	
	private void dismissAnyDialog() throws UiObjectNotFoundException{
		if (mAlertTitle.exists() && mAlertOK.exists()){
			mAlertOK.clickAndWaitForNewWindow(Constants.TIMEOUT_IN_MS);
		}
	}
	
	/**
	 * Helper method to navigate backwards in the fragment stack until we are back
	 * to a parent view that has the menu available
	 * 
	 * @throws UiObjectNotFoundException
	 */
	private void navigateBackToParent() throws UiObjectNotFoundException {
		// Reset back to a parent screen where the menu button is available
		dismissAnyDialog();
		while (mActionBarTitle.exists() && !mActionBarTitle.getText().equalsIgnoreCase("Book Keeper")) {
			mMenuHomeButton.clickAndWaitForNewWindow(Constants.TIMEOUT_IN_MS);
		}
	}
}

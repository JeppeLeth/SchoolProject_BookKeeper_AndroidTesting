package edu.kea.pm.bookkeeper.uitest.pages;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;

/**
 * A page object handling interactions with the barcode scanner view
 */
public class ScannerViewPageObject extends PageObjectBase {
	private UiObject actionBarTitle = new UiObject(new UiSelector().resourceId("android:id/action_bar_title"));
	private UiObject alertTitle = new UiObject(new UiSelector().resourceId("android:id/alertTitle"));
	
	public ScannerViewPageObject(UiDevice device) {
		super(device);
	}
	
	public boolean isBarcodeScannerLaunched() throws UiObjectNotFoundException {
		return actionBarTitle.exists() && actionBarTitle.getText().equalsIgnoreCase("Barcode Scanner");
	}
	
	public boolean isAlertShown(String message) throws UiObjectNotFoundException {
		return alertTitle.exists() && alertTitle.getText().contains(message);
	}
	
	public void returnFromView() throws UiObjectNotFoundException {
		getDevice().pressBack();
	}
	
	
}

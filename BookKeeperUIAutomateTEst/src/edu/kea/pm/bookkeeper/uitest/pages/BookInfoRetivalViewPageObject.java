package edu.kea.pm.bookkeeper.uitest.pages;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;

/**
 * A page object handling interactions with the book information view, that fetches information online
 */
public class BookInfoRetivalViewPageObject extends PageObjectBase {
	private UiObject actionBarTitle = new UiObject(new UiSelector().resourceId("android:id/action_bar_title"));
	private UiObject alertTitle = new UiObject(new UiSelector().resourceId("android:id/alertTitle"));
	private UiObject addButton = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/action_add"));
	private UiObject isbnText = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/isbn"));
	private UiObject titleText = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/title"));
	private UiObject authorText = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/author"));
	private UiObject languageText = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/language"));
	private UiObject descriptionText = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/desc"));
	private UiObject pagesText = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/pages"));
	private UiObject publishedText = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/published"));
	
	public BookInfoRetivalViewPageObject(UiDevice device) {
		super(device);
	}
	
	public boolean isLaunched() throws UiObjectNotFoundException {
		return (actionBarTitle.exists() && actionBarTitle.getText().equalsIgnoreCase("Book info")) || isAlertShown();
	}
	
	public boolean isAlertShown() throws UiObjectNotFoundException {
		return alertTitle.exists() && alertTitle.getText().contains("Error");
	}
	
	public void returnFromView() throws UiObjectNotFoundException {
		getDevice().pressBack();
	}
	
	public String getIsbnText() throws UiObjectNotFoundException {
		return isbnText.getText();
	}
	
	public boolean areAllFieldsAccessible() throws UiObjectNotFoundException {
		boolean found = true;
		if (new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/ScrollView1")).isScrollable()) {
			UiScrollable view = new UiScrollable(new UiSelector().scrollable(true));
			view.setAsVerticalList();
			found = found && view.scrollIntoView(addButton);
			found = found && view.scrollIntoView(isbnText);
			found = found && view.scrollIntoView(titleText);
			found = found && view.scrollIntoView(titleText);
			found = found && view.scrollIntoView(authorText);
			found = found && view.scrollIntoView(languageText);
			found = found && view.scrollIntoView(descriptionText);
			found = found && view.scrollIntoView(pagesText);
			found = found && view.scrollIntoView(publishedText);
		} else {
			found = addButton.exists();
			found = found && isbnText.exists();;
			found = found && titleText.exists();
			found = found && titleText.exists();
			found = found && authorText.exists();
			found = found && languageText.exists();
			found = found && descriptionText.exists();
			found = found && pagesText.exists();
			found = found && publishedText.exists();
		}
		return found;
	}
	
	public void addBook() throws UiObjectNotFoundException{
		clickAndWait(addButton);
	}
	
	
}

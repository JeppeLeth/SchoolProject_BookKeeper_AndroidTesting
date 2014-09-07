package edu.kea.pm.bookkeeper.uitest.pages;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;

/**
 * A page object handling interactions with the book information view, that fetches information online
 */
public class BookSaveInfoViewPageObject extends PageObjectBase {
	private UiObject actionBarTitle = new UiObject(new UiSelector().resourceId("android:id/action_bar_title"));
	private UiObject saveButton = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/action_save"));
	private UiObject isbnNotEditableText = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/isbn"));
	private UiObject titleText = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/editTitle"));
	private UiObject authorText = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/editAuthor"));
	private UiObject languageText = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/Language"));
	private UiObject descriptionText = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/editDesc"));
	private UiObject pagesText = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/editNumberOfPages"));
	private UiObject publishedText = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/editPublished"));
	private UiObject imageUrlText = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/editCoverImgThumbnailURL"));
	private UiObject commentsText = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/editComments"));
	
	public BookSaveInfoViewPageObject(UiDevice device) {
		super(device);
	}
	
	public boolean isLaunched() throws UiObjectNotFoundException {
		return (actionBarTitle.exists() && actionBarTitle.getText().equalsIgnoreCase("Save book info"));
	}
	
	public void returnFromView() throws UiObjectNotFoundException {
		getDevice().pressBack();
	}
	
	public String getIsbnText() throws UiObjectNotFoundException {
		return isbnNotEditableText.getText();
	}
	
	public boolean isIsbnEditable() throws UiObjectNotFoundException {
		return isbnNotEditableText.isFocusable();
	}
	
	public boolean areAllFieldsAccessible() throws UiObjectNotFoundException {
		boolean found = true;
		if (new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/ScrollView1")).isScrollable()) {
			UiScrollable view = new UiScrollable(new UiSelector().scrollable(true));
			view.setAsVerticalList();
			found = found && view.scrollIntoView(saveButton);
			found = found && view.scrollIntoView(isbnNotEditableText);
			found = found && view.scrollIntoView(titleText);
			found = found && view.scrollIntoView(titleText);
			found = found && view.scrollIntoView(authorText);
			found = found && view.scrollIntoView(languageText);
			found = found && view.scrollIntoView(descriptionText);
			found = found && view.scrollIntoView(pagesText);
			found = found && view.scrollIntoView(publishedText);
			found = found && view.scrollIntoView(imageUrlText);
			found = found && view.scrollIntoView(commentsText);
		} else {
			found = saveButton.exists();
			found = found && isbnNotEditableText.exists();;
			found = found && titleText.exists();
			found = found && titleText.exists();
			found = found && authorText.exists();
			found = found && languageText.exists();
			found = found && descriptionText.exists();
			found = found && pagesText.exists();
			found = found && publishedText.exists();
			found = found && imageUrlText.exists();
			found = found && commentsText.exists();
		}
		return found;
	}
	
	public void saveBook() throws UiObjectNotFoundException{
		clickAndWait(saveButton);
	}
	
	private void scrollTo(UiObject view) throws UiObjectNotFoundException{
		if (new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/ScrollView1")).isScrollable()) {
			UiScrollable scroll = new UiScrollable(new UiSelector().scrollable(true));
			scroll.setAsVerticalList();
			scroll.scrollIntoView(view);
		}
	}
	
	public void editTitle(String text) throws UiObjectNotFoundException {
		scrollTo(titleText);
		clearTextField(titleText);
		enterText(titleText, text);
	}
	
	public void editComment(String text) throws UiObjectNotFoundException {
		scrollTo(commentsText);
		enterText(commentsText, text);
	}
	
	
}

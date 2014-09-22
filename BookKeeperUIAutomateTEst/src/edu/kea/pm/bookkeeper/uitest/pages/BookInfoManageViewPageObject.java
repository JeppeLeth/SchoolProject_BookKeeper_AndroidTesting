package edu.kea.pm.bookkeeper.uitest.pages;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;

/**
 * A page object handling interactions with the book information view, that fetches information online
 */
public class BookInfoManageViewPageObject extends PageObjectBase {
	private UiObject actionBarTitle = new UiObject(new UiSelector().resourceId("android:id/action_bar_title"));
	private UiObject deleteTitle = new UiObject(new UiSelector().resourceId("android:id/alertTitle"));
	private UiObject loanerTitle = new UiObject(new UiSelector().resourceId("android:id/title"));
	private UiObject editButton = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/action_edit"));
	private UiObject deleteButton = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/action_delete"));
	private UiObject addLoanButton = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/action_loan_status_add"));
	private UiObject removeLoanButton = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/action_loan_status_remove"));
	private UiObject isbnText = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/isbn"));
	private UiObject titleText = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/title"));
	private UiObject authorText = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/author"));
	private UiObject languageText = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/language"));
	private UiObject descriptionText = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/desc"));
	private UiObject pagesText = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/pages"));
	private UiObject publishedText = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/published"));
	private UiObject notesText = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/note"));
	private UiObject loanerText = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/loaner"));
	
	public BookInfoManageViewPageObject(UiDevice device) {
		super(device);
	}
	
	public boolean isLaunched() throws UiObjectNotFoundException {
		return (actionBarTitle.exists() && actionBarTitle.getText().equalsIgnoreCase("Book info"));
	}
	
	public boolean isAlertDeleteShown() throws UiObjectNotFoundException {
		return deleteTitle.exists() && deleteTitle.getText().contains("Delete");
	}
	
	public boolean isAlertLoanOutShown() throws UiObjectNotFoundException {
		return loanerTitle.exists() && loanerTitle.getText().contains("lender");
	}
	
	public void confirmAlert() throws UiObjectNotFoundException {
		clickAndWait( new UiObject(new UiSelector().resourceId("android:id/button1")));
	}
	
	public void returnFromView() throws UiObjectNotFoundException {
		getDevice().pressBack();
	}
	
	public String getIsbnText() throws UiObjectNotFoundException {
		return isbnText.getText();
	}
	
	public String getLoanerText() throws UiObjectNotFoundException {
		if (!loanerText.exists()) {
			scrollTo(loanerText);
		}
		return loanerText.getText();
	}
	
	public boolean isLoanerVisible() throws UiObjectNotFoundException{
		scrollTo(loanerText);
		return loanerText.exists();
	}
	
	public boolean areAllFieldsAccessible(boolean loanedOut) throws UiObjectNotFoundException {
		boolean found = true;
		found = editButton.exists();
		found = deleteButton.exists();
		found = loanedOut ? removeLoanButton.exists() : addLoanButton.exists();
		if (new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/ScrollView1")).isScrollable()) {
			UiScrollable view = new UiScrollable(new UiSelector().scrollable(true));
			view.setAsVerticalList();
			found = found && view.scrollIntoView(isbnText);
			found = found && view.scrollIntoView(titleText);
			found = found && view.scrollIntoView(titleText);
			found = found && view.scrollIntoView(authorText);
			found = found && view.scrollIntoView(languageText);
			found = found && view.scrollIntoView(descriptionText);
			found = found && view.scrollIntoView(pagesText);
			found = found && view.scrollIntoView(publishedText);
			found = found && view.scrollIntoView(notesText);
			found = found && loanedOut ? view.scrollIntoView(loanerText) : true;
		} else {
			found = found && isbnText.exists();;
			found = found && titleText.exists();
			found = found && titleText.exists();
			found = found && authorText.exists();
			found = found && languageText.exists();
			found = found && descriptionText.exists();
			found = found && pagesText.exists();
			found = found && publishedText.exists();
			found = found && notesText.exists();
			found = found && loanedOut ? loanerText.exists() : true;
		}
		return found;
	}
	
	private void scrollTo(UiObject view) throws UiObjectNotFoundException{
		if (new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/ScrollView1")).isScrollable()) {
			UiScrollable scroll = new UiScrollable(new UiSelector().scrollable(true));
			scroll.setAsVerticalList();
			scroll.scrollIntoView(view);
		}
	}
	
	public void editBook() throws UiObjectNotFoundException{
		clickAndWait(editButton);
	}
	
	public void deleteBook() throws UiObjectNotFoundException{
		clickAndWait(deleteButton);
	}
	
	public void editLoanerOfBook() throws UiObjectNotFoundException{
		clickAndWait(addLoanButton);
	}
	
	public void enterLoaner(String text) throws UiObjectNotFoundException{
		enterText(new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/loaner_text")), text);
	}
	
	public void clearLoaner() throws UiObjectNotFoundException{
		clickAndWait(removeLoanButton);
	}
	
	
}

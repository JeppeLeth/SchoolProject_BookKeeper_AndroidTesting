package edu.kea.pm.bookkeeper.uitest.pages;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;

/**
 * A page object handling interactions with the main search for isbn view
 */
public class ListBooksViewPageObject extends PageObjectBase {
	private UiObject listView = new UiObject(new UiSelector().resourceId("android:id/list"));
	private UiObject scanButton = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/buttonScan"));
	private UiObject loopUpButton = new UiObject(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/buttonLookUp"));
	
	public ListBooksViewPageObject(UiDevice device) {
		super(device);
	}
	
	public void scrollToBeginning() throws UiObjectNotFoundException {
		if (listView.isScrollable()) {
			scrollTo(true);
		}
	}
	
	public void scrollToEnd() throws UiObjectNotFoundException {
		if (listView.isScrollable()) {
			scrollTo(false);
		}
	}
	
	public void clickFirstBook() throws UiObjectNotFoundException {
		clickAndWait(listView.getChild(new UiSelector().clickable(true).index(0)));
	}
	
	public void clickLastBook() throws UiObjectNotFoundException {
		int last = listView.getChildCount()-1;
		clickAndWait(listView.getChild(new UiSelector().clickable(true).index(last)));
	}
	
	private void scrollTo(boolean top) throws UiObjectNotFoundException{
		UiScrollable scroll = new UiScrollable(new UiSelector().scrollable(true));
		scroll.setAsVerticalList();
		if (top) {
			scroll.flingToBeginning(10);
		} else {
			scroll.flingToEnd(10);
		}
	}
	
	public int getDisplayedChildrenCount() throws UiObjectNotFoundException {
		return listView.getChildCount();
	}
	
	
	public String getTitleOfFirstBook() throws UiObjectNotFoundException {
		UiObject book = listView.getChild(new UiSelector().clickable(true).index(0));
		return book.getChild(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/title")).getText();
	}
	
	public String getTitleOfLastBook() throws UiObjectNotFoundException {
		int last = listView.getChildCount()-1;
		UiObject book = listView.getChild(new UiSelector().clickable(true).index(last));
		return book.getChild(new UiSelector().resourceId("edu.kea.pm.bookkeeper:id/title")).getText();
	}
	
}

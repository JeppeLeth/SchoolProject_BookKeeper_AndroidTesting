package edu.kea.pm.bookkeeper.uitest.runnable;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObjectNotFoundException;

import edu.kea.pm.bookkeeper.uitest.AbstractUiAutomatorTestCase;
import edu.kea.pm.bookkeeper.uitest.pages.BookInfoManageViewPageObject;
import edu.kea.pm.bookkeeper.uitest.pages.ListBooksViewPageObject;
import edu.kea.pm.bookkeeper.uitest.pages.MenuViewPageObject;

/**
 * A test suite used to ensure that a book can be deleted from the library.
 * It is assumed that the application is opened and there is at least 1 book in the library. 
 */
public class DeleteBookTests extends AbstractUiAutomatorTestCase {	
	private UiDevice mDevice;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		mDevice = getDevice();
	}
	
	public void testDeleteBook() throws UiObjectNotFoundException {
		MenuViewPageObject menuView = new MenuViewPageObject(mDevice);
		menuView.openMyBooks();
		
		ListBooksViewPageObject listView = new ListBooksViewPageObject(mDevice);
		listView.scrollToBeginning();
		String targetTitle = listView.getTitleOfFirstBook();
		listView.clickFirstBook();
		
		BookInfoManageViewPageObject manageView = new BookInfoManageViewPageObject(mDevice);
		assertTrue("The Book info is not launched", manageView.isLaunched());
		
		manageView.deleteBook();
		assertTrue("The delete alert is not shown", manageView.isAlertDeleteShown());
		manageView.confirmAlert();
		assertTrue("The book remains on list",listView.getDisplayedChildrenCount() == 0 || !targetTitle.equals(listView.getTitleOfFirstBook()));
	}
 
}

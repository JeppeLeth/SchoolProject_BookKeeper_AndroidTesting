package edu.kea.pm.bookkeeper.uitest.runnable;

import static edu.kea.pm.bookkeeper.uitest.Constants.TEST_LOANER_NAME;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObjectNotFoundException;

import edu.kea.pm.bookkeeper.uitest.AbstractUiAutomatorTestCase;
import edu.kea.pm.bookkeeper.uitest.pages.BookInfoManageViewPageObject;
import edu.kea.pm.bookkeeper.uitest.pages.ListBooksViewPageObject;
import edu.kea.pm.bookkeeper.uitest.pages.MenuViewPageObject;

/**
 * A test suite used to ensure that a book can be indicated as loaned out.
 * It is assumed that the application is opened and there is at least 1 book in the library. 
 */
public class LoanBookTests extends AbstractUiAutomatorTestCase {	
	private UiDevice mDevice;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		mDevice = getDevice();
	}
	
	public void testReturnLoanedBook() throws UiObjectNotFoundException {
		navigateToBook();
		
		BookInfoManageViewPageObject manageView = new BookInfoManageViewPageObject(mDevice);
		assertTrue("View for managing books was not launched", manageView.isLaunched());
		assertTrue("Fiels are missing from the view", manageView.areAllFieldsAccessible(true));
		
		manageView.editLoanerOfBook();
		assertTrue("Alert for editing loaner is not shown",manageView.isAlertLoanOutShown());
		manageView.clearLoaner();
		manageView.confirmAlert();
		assertFalse("The loaner is still visible after return of book", manageView.isLoanerVisible());
	}
	
	public void testLoanOutBook() throws UiObjectNotFoundException {
		navigateToBook();
		
		BookInfoManageViewPageObject manageView = new BookInfoManageViewPageObject(mDevice);
		assertTrue("View for managing books was not launched", manageView.isLaunched());
		assertTrue("Fiels are missing from the view", manageView.areAllFieldsAccessible(false));
		
		manageView.editLoanerOfBook();
		assertTrue("Alert for editing loaner is not shown", manageView.isAlertLoanOutShown());
		manageView.enterLoaner(TEST_LOANER_NAME);
		manageView.confirmAlert();
		assertEquals("The entered loaners name is not displayed", TEST_LOANER_NAME, manageView.getLoanerText());
	}

	private void navigateToBook() throws UiObjectNotFoundException {
		MenuViewPageObject menuView = new MenuViewPageObject(mDevice);
		menuView.openMyBooks();
		
		ListBooksViewPageObject listView = new ListBooksViewPageObject(mDevice);
		listView.scrollToBeginning();
		listView.clickFirstBook();
	} 
	
 
}

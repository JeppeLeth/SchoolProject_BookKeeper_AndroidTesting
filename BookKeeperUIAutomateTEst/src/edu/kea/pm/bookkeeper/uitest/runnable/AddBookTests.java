package edu.kea.pm.bookkeeper.uitest.runnable;

import static edu.kea.pm.bookkeeper.uitest.Constants.TEST_BOOK_TITLE;
import static edu.kea.pm.bookkeeper.uitest.Constants.TEST_VALID_ISBN;
import android.os.SystemClock;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObjectNotFoundException;

import edu.kea.pm.bookkeeper.uitest.AbstractUiAutomatorTestCase;
import edu.kea.pm.bookkeeper.uitest.pages.BookInfoRetivalViewPageObject;
import edu.kea.pm.bookkeeper.uitest.pages.BookSaveInfoViewPageObject;
import edu.kea.pm.bookkeeper.uitest.pages.ListBooksViewPageObject;
import edu.kea.pm.bookkeeper.uitest.pages.MainSearchViewPageObject;
import edu.kea.pm.bookkeeper.uitest.pages.MenuViewPageObject;

/**
 * A test suite used to ensure that adding a book to the library works.
 * It is assumed that the application is opened and internet access is available. 
 */
public class AddBookTests extends AbstractUiAutomatorTestCase {	
	private UiDevice mDevice;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		mDevice = getDevice();
	}
	
	public void testSaveInformation() throws UiObjectNotFoundException {
		MainSearchViewPageObject lookUpView = new MainSearchViewPageObject(mDevice);
		lookUpView.enterTextAndLoopUp(TEST_VALID_ISBN);
		SystemClock.sleep(5000l);
		
		BookInfoRetivalViewPageObject bookInfoView = new BookInfoRetivalViewPageObject(mDevice);
		String isbn = bookInfoView.getIsbnText();
		bookInfoView.addBook();
		
		BookSaveInfoViewPageObject saveView = new BookSaveInfoViewPageObject(mDevice);
		assertTrue("Book save is not launched",saveView.isLaunched());
		assertFalse("ISBN is editable",saveView.isIsbnEditable());
		assertEquals("ISBN not the same as in previous view",isbn, saveView.getIsbnText());
		
		String testTitle = String.format("-#%020d", Long.MAX_VALUE-System.currentTimeMillis()) + TEST_BOOK_TITLE;
		
		saveView.editTitle(testTitle);
		saveView.editComment("this book was created from a test case");
		saveView.saveBook();
		
		MenuViewPageObject menuView = new MenuViewPageObject(mDevice);
		menuView.openMyBooks();
		
		ListBooksViewPageObject listView = new ListBooksViewPageObject(mDevice);
		listView.scrollToBeginning();
		assertEquals("New book is not displayed as first in the view", testTitle, listView.getTitleOfFirstBook());
	} 
	
 
}

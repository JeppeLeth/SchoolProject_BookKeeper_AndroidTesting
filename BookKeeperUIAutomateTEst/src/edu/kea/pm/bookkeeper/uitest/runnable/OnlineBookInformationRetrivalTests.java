package edu.kea.pm.bookkeeper.uitest.runnable;

import android.os.RemoteException;

import android.os.SystemClock;

import com.android.uiautomator.core.UiDevice;
import static edu.kea.pm.bookkeeper.uitest.Constants.*;
import com.android.uiautomator.core.UiObjectNotFoundException;

import edu.kea.pm.bookkeeper.uitest.AbstractUiAutomatorTestCase;
import edu.kea.pm.bookkeeper.uitest.pages.BookInfoRetivalViewPageObject;
import edu.kea.pm.bookkeeper.uitest.pages.MainSearchViewPageObject;

/**
 * A test suite used to ensure that retrieval of book information online works.
 * It is assumed that the application is opened. 
 */
public class OnlineBookInformationRetrivalTests extends AbstractUiAutomatorTestCase {	
	private UiDevice mDevice;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		mDevice = getDevice();
	}
	
	public void testShowErrorWhenBookNotFound() throws UiObjectNotFoundException {
		MainSearchViewPageObject lookUpView = new MainSearchViewPageObject(mDevice);
		lookUpView.enterTextAndLoopUp(TEST_UNKNOWN_ISBN);
		mDevice.waitForIdle(5000);
		
		BookInfoRetivalViewPageObject bookInfoView = new BookInfoRetivalViewPageObject(mDevice);
		assertTrue("An error alert is not shown, when book cannot be retrieved", bookInfoView.isAlertShown());
	} 
	
	public void testRetrieveBookInformation() throws UiObjectNotFoundException {
		MainSearchViewPageObject lookUpView = new MainSearchViewPageObject(mDevice);
		lookUpView.enterTextAndLoopUp(TEST_VALID_ISBN);
		SystemClock.sleep(5000l);
		
		BookInfoRetivalViewPageObject bookInfoView = new BookInfoRetivalViewPageObject(mDevice);
		assertTrue("Fields are not all being displayed",bookInfoView.areAllFieldsAccessible());
		assertEquals("The correct ISBN information is not shown after retrieval", TEST_VALID_ISBN, bookInfoView.getIsbnText());
	} 
	
	public void testTextRetainedWhenOrientationChange() throws UiObjectNotFoundException, RemoteException {
		testRetrieveBookInformation();
		
		mDevice.setOrientationLeft();
		mDevice.waitForIdle(1000);
		
		BookInfoRetivalViewPageObject bookInfoView = new BookInfoRetivalViewPageObject(mDevice);
		assertTrue("Fields are not all being displayed after rotation", bookInfoView.areAllFieldsAccessible());
		assertEquals("The correct ISBN information is not shown after rotation", TEST_VALID_ISBN, bookInfoView.getIsbnText());
		
		mDevice.setOrientationNatural();
	} 
	
}

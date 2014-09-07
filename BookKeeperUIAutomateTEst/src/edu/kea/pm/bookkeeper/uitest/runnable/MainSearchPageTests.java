package edu.kea.pm.bookkeeper.uitest.runnable;

import android.os.RemoteException;
import android.os.SystemClock;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObjectNotFoundException;

import edu.kea.pm.bookkeeper.uitest.Constants;
import edu.kea.pm.bookkeeper.uitest.AbstractUiAutomatorTestCase;
import edu.kea.pm.bookkeeper.uitest.pages.MainSearchViewPageObject;

/**
 * A test suite used to ensure that main page works works.
 * It is assumed that the application is opened. 
 */
public class MainSearchPageTests extends AbstractUiAutomatorTestCase {	
	private UiDevice mDevice;
	private static final String TEST_VALID_ISBN = "9781906124762";
	private static final String TEST_INVALID_ISBN = "abcdef";
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		mDevice = getDevice();
	}
	
	public void testTextNumbersEntered() throws UiObjectNotFoundException {
		MainSearchViewPageObject lookUpView = new MainSearchViewPageObject(mDevice);
		lookUpView.enterText(TEST_VALID_ISBN);
		
		assertEquals("Valid ISBN was not displayed after entering",TEST_VALID_ISBN, lookUpView.getIsbnText());
	} 
	
	public void testTextLettersEntered() throws UiObjectNotFoundException {
		MainSearchViewPageObject lookUpView = new MainSearchViewPageObject(mDevice);
		lookUpView.enterText(TEST_INVALID_ISBN);
		
		assertEquals("Invalid text is shown in field", Constants.TEXT_HINT, lookUpView.getIsbnText());
	}
	
	public void testTextRetainedWhenOrientationChange() throws UiObjectNotFoundException, RemoteException {
		MainSearchViewPageObject lookUpView = new MainSearchViewPageObject(mDevice);
		lookUpView.enterText(TEST_VALID_ISBN);
		
		mDevice.setOrientationLeft();
		assertEquals("The isbn entered is not the same before and after rotation",TEST_VALID_ISBN, lookUpView.getIsbnText());
		mDevice.setOrientationNatural();
		SystemClock.sleep(400);
	} 
	
}

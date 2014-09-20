package edu.kea.pm.bookkeeper.uitest.runnable;

import android.os.RemoteException;
import android.os.SystemClock;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;

import edu.kea.pm.bookkeeper.uitest.AbstractUiAutomatorTestCase;
import edu.kea.pm.bookkeeper.uitest.pages.MenuViewPageObject;

/**
 * A test suite used to ensure that menu navigation works.
 * It is assumed that the application is opened. 
 */
public class NavigationTests extends AbstractUiAutomatorTestCase {	
	private UiObject mActionBarTitle = new UiObject(new UiSelector().resourceId("android:id/action_bar_title"));
	
	private UiDevice mDevice;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		mDevice = getDevice();
	}
	
	public void testNavigateToLookUpPage() throws UiObjectNotFoundException {
		MenuViewPageObject menuView = new MenuViewPageObject(mDevice);
		menuView.openLookUp();
		
		assertEquals("The title was not 'Look up book'","Look up book", mActionBarTitle.getText());
	} 
	
	public void testNavigateToMyBookPage() throws UiObjectNotFoundException {
		MenuViewPageObject menuView = new MenuViewPageObject(mDevice);
		menuView.openMyBooks();
		
		assertEquals("The title was not 'My books'", "My books", mActionBarTitle.getText());
	} 
	
	public void testMenuStateIsRetainedWhileRotating() throws UiObjectNotFoundException, RemoteException {
		MenuViewPageObject menuView = new MenuViewPageObject(mDevice);
		
		menuView.toggleLeftDrawer(true);
		mDevice.setOrientationLeft();
		assertTrue("The drawer menu is not open anymore", menuView.isLeftDrawerOpen());
		
		mDevice.setOrientationRight();
		assertTrue("The drawer menu is not open anymore", menuView.isLeftDrawerOpen());
		
		menuView.toggleLeftDrawer(false);
	}
}

package edu.kea.pm.bookkeeper.uitest.runnable;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObjectNotFoundException;

import edu.kea.pm.bookkeeper.uitest.AbstractUiAutomatorTestCase;
import edu.kea.pm.bookkeeper.uitest.pages.MainSearchViewPageObject;
import edu.kea.pm.bookkeeper.uitest.pages.ScannerViewPageObject;

/**
 * A test suite used to ensure that menu navigation works.
 * It is assumed that the application is opened. 
 */
public class ScannerLaunchTests extends AbstractUiAutomatorTestCase {	
	private UiDevice mDevice;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		mDevice = getDevice();
	}
	
	public void testScannerCanLaunch() throws UiObjectNotFoundException {
		MainSearchViewPageObject lookUpView = new MainSearchViewPageObject(mDevice);
		lookUpView.scan();
		
		ScannerViewPageObject scannerView = new ScannerViewPageObject(mDevice);

		assertTrue("Barcode scanner won't open neigter application or installation instructions", scannerView.isBarcodeScannerLaunched() || scannerView.isAlertShown("Install"));
		
		scannerView.returnFromView();
	} 
}

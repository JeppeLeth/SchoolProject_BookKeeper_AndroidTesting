package edu.kea.pm.bookkeeper.test.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.app.Instrumentation.ActivityResult;
import android.app.UiAutomation;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.test.MoreAsserts;
import android.test.TouchUtils;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import edu.kea.pm.bookkeeper.R;
import edu.kea.pm.bookkeeper.activity.BookInfoActivity;
import edu.kea.pm.bookkeeper.activity.MainActivity;
import edu.kea.pm.bookkeeper.database.Database;
import edu.kea.pm.bookkeeper.database.DatabaseImpl;
import edu.kea.pm.bookkeeper.fragment.BookListFragment;
import edu.kea.pm.bookkeeper.fragment.LoopUpFragment;
import edu.kea.pm.bookkeeper.model.Book;

@SuppressLint("NewApi")
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity>
{
	public MainActivityTest()
	{
		super(MainActivity.class);
	}

	public MainActivityTest(Class<MainActivity> activityClass)
	{
		super(activityClass);
	}
	
	private MainActivity mTestActivity;
    private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;



    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getInstrumentation().getUiAutomation().setRotation(UiAutomation.ROTATION_FREEZE_0);
        mTestActivity = getActivity();
        mDrawerLayout = (DrawerLayout) mTestActivity.findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) mTestActivity.findViewById(R.id.left_drawer);
    }
    
    public void testPreconditions() {
        assertNotNull("mTestActivity is null", mTestActivity);
        assertNotNull("mDrawerLayout is null", mDrawerLayout);
        assertNotNull("mDrawerList is null", mDrawerList);
    }
    
    public void testListView_menuItemCount() {
        final int numberOfMenuItems = 2;
        final int count = mDrawerList.getAdapter().getCount();
        assertEquals(numberOfMenuItems, count);
    }
    
    public void testListView_isFirstItemSelected() {
        final boolean selected = mDrawerList.getChildAt(0).isSelected();
        assertTrue(selected);
    }
    
    public void testListView_isSecondItemUnselected() {
        final boolean selected = mDrawerList.getChildAt(1).isSelected();
        assertFalse(selected);
    }
    
    public void testLookUpFragment_exists() {
    	List<Fragment> list = mTestActivity.getSupportFragmentManager().getFragments();
        assertTrue( list.get(0) instanceof LoopUpFragment );
    }
    
    public void testHomeButton_opensAndClosesDrawerLayout() {
    	assertFalse(mDrawerLayout.isDrawerOpen(mDrawerList));
    	TouchUtils.clickView(this, mTestActivity.findViewById(android.R.id.home));
    	assertTrue(mDrawerLayout.isDrawerOpen(mDrawerList));
    	TouchUtils.clickView(this, mTestActivity.findViewById(android.R.id.home));
    	assertFalse(mDrawerLayout.isDrawerOpen(mDrawerList));
    }
    
    public void testListFragment_navigateToMyBooks() {
    	TouchUtils.clickView(this, mTestActivity.findViewById(android.R.id.home));
    	assertTrue(mDrawerLayout.isDrawerOpen(mDrawerList));
    	TouchUtils.clickView(this, mDrawerList.getChildAt(1));
    	List<Fragment> list = mTestActivity.getSupportFragmentManager().getFragments();
        assertTrue( list.get(0) instanceof BookListFragment );
    }
    
    @MediumTest
    public void testLookUpFragment_layout() {
        final View decorView = mTestActivity.getWindow().getDecorView();
        ViewAsserts.assertOnScreen(decorView, mTestActivity.findViewById(R.id.isbnEditText));
        ViewAsserts.assertOnScreen(decorView, mTestActivity.findViewById(R.id.buttonLookUp));
        ViewAsserts.assertOnScreen(decorView, mTestActivity.findViewById(R.id.buttonScan));
    }
    
    public void testEditTextIsbn_isEmptyInitally() {
        EditText editText = (EditText) mTestActivity.findViewById(R.id.isbnEditText);
        String expected = "";
        assertEquals(expected, editText.getText().toString());
    }
    
    public void testEditTextIsbn_filledWhenBarcodeScanOK() {
    	final String expected = "123";
        EditText editText = (EditText) mTestActivity.findViewById(R.id.isbnEditText);
        IntentFilter filter = getBarcodeScanIntentFilter();
        Intent returnedIntent = new Intent();
        returnedIntent.putExtra("SCAN_RESULT", expected);
        getInstrumentation().addMonitor(new ActivityMonitor(filter, new ActivityResult(Activity.RESULT_OK, returnedIntent), true));
        TouchUtils.clickView(this, mTestActivity.findViewById(R.id.buttonScan));
        assertEquals(expected, editText.getText().toString());
    }
    
    public void testEditTextIsbn_leaveUnchangedWhenBarcodeScanBad() throws Throwable {
    	final String expected = "123";
        final EditText editText = (EditText) mTestActivity.findViewById(R.id.isbnEditText);
    	runTestOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				editText.setText(expected);
			}
		});
        IntentFilter filter = getBarcodeScanIntentFilter();
        getInstrumentation().addMonitor(new ActivityMonitor(filter, new ActivityResult(Activity.RESULT_CANCELED, null), true));
        TouchUtils.clickView(this, mTestActivity.findViewById(R.id.buttonScan));
        assertEquals(expected, editText.getText().toString());
    }

	private IntentFilter getBarcodeScanIntentFilter() {
		IntentFilter filter = new IntentFilter();
        filter.addCategory("android.intent.category.DEFAULT");
        filter.addAction("com.google.zxing.client.android.SCAN");
		return filter;
	}
    
    private ActivityMonitor getBookInfoActivityMonitor() {
    	ActivityMonitor monitor = new ActivityMonitor(BookInfoActivity.class.getName(), new ActivityResult(Activity.RESULT_CANCELED, null), true);
    	return monitor;
    }
    
    public void testLoopUp_willLaunchNewActivityWhenIsbnIsValid() throws Throwable {
        final EditText editText = (EditText) mTestActivity.findViewById(R.id.isbnEditText);
    	runTestOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				editText.setText("123");
			}
		});
    	ActivityMonitor monitor = getBookInfoActivityMonitor();
        getInstrumentation().addMonitor(monitor);
        TouchUtils.clickView(this, mTestActivity.findViewById(R.id.buttonLookUp));
        assertEquals(1, monitor.getHits());
    }
    
    public void testLoopUp_willNotLaunchNewActivityWhenIsbnIsEmpty() throws Throwable {
    	ActivityMonitor monitor = getBookInfoActivityMonitor();
        getInstrumentation().addMonitor(monitor);
        TouchUtils.clickView(this, mTestActivity.findViewById(R.id.buttonLookUp));
        assertEquals(0, monitor.getHits());
    }
    
//    public void testDb() throws Throwable {
//    	Database db = new DatabaseImpl(mTestActivity);
//    	Book b = new Book();
//    	b.setAuthors("GG");
//    	b.setComment("gsd");
//    	b.setDescription("gsd");
//    	b.setIsbn("123456");
//    	b.setLanguage("Eng");
//    	b.setLoaner("mom");
//    	b.setPageCount(1);
//    	b.setPublished("1294");
//    	b.setThumbnailURL(null);
//    	b.setTitle("Test");
//    	db.saveBook(b);
//    }
    
    public void testEditTextIsbn_willOnlyAcceptNumbers() {
    	final EditText editText = (EditText) mTestActivity.findViewById(R.id.isbnEditText);
    	final String expected = "123";
    	
    	sendKeys(KeyEvent.KEYCODE_A);
    	sendKeys(KeyEvent.KEYCODE_B);
    	assertTrue(editText.getText().toString().isEmpty());
    	sendKeys(KeyEvent.KEYCODE_1);
    	sendKeys(KeyEvent.KEYCODE_2);
    	sendKeys(KeyEvent.KEYCODE_3);
    	sendKeys(KeyEvent.KEYCODE_C);
        assertEquals(expected, editText.getText().toString());
    }
    
    public void testEditTextIsbn_willHandleSaveAndRestoreState() throws Throwable {
    	final EditText editText = (EditText) mTestActivity.findViewById(R.id.isbnEditText);
    	final String expected = "123";
    	runTestOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				editText.setText(expected);
			}
		});
    	// Stop the activity - The onDestroy() method should save the state of the Spinner
        mTestActivity.finish();

        // Re-start the Activity - the onResume() method should restore the state of the Spinner
        mTestActivity = getActivity();
        
        assertEquals(expected, editText.getText().toString());
    }
    
    public void testEditTextIsbn_willHandleRotation() throws Throwable  {
    	final EditText editText = (EditText) mTestActivity.findViewById(R.id.isbnEditText);
    	final String expected = "123";
    	runTestOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				editText.setText(expected);
				
			}
		});
    	getInstrumentation().getUiAutomation().setRotation(UiAutomation.ROTATION_FREEZE_90);

    	// Re-start the Activity - the onResume() method should restore the state of the Spinner
        Activity newActivity = getActivity();
        EditText newEditText = (EditText) newActivity.findViewById(R.id.isbnEditText);
        assertEquals(expected, newEditText.getText().toString());
    }
    

    
}

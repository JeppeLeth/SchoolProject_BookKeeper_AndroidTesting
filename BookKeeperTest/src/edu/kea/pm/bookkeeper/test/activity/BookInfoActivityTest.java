package edu.kea.pm.bookkeeper.test.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.UiAutomation;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.test.ActivityInstrumentationTestCase2;
import android.test.MoreAsserts;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import edu.kea.pm.bookkeeper.R;
import edu.kea.pm.bookkeeper.activity.BookInfoActivity;
import edu.kea.pm.bookkeeper.fragment.BookSavedInfoFragment;
import edu.kea.pm.bookkeeper.fragment.LoopUpFragment;
import edu.kea.pm.bookkeeper.model.Book;

@SuppressLint("NewApi")
public class BookInfoActivityTest extends ActivityInstrumentationTestCase2<BookInfoActivity>
{
	public BookInfoActivityTest()
	{
		super(BookInfoActivity.class);
	}

	public BookInfoActivityTest(Class<BookInfoActivity> activityClass)
	{
		super(activityClass);
	}
	
	private BookInfoActivity mTestActivity;




    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getInstrumentation().getUiAutomation().setRotation(UiAutomation.ROTATION_FREEZE_0);
        
        Intent intent = new Intent();
        intent.putExtra(BookInfoActivity.BUNDLE_BARCODE, "9781906124762");
        //setActivityIntent(intent);
        mTestActivity = getActivity();
    }
    
    public void testPreconditions() throws InterruptedException {
        assertNotNull("mTestActivity is null", mTestActivity);
    }
    
    
//    public void testSpinner_showingOnStartup() {
//        assertNotNull("mTestActivity is null", mTestActivity);
//        mTestActivity.
//    }
    
//    public void testFieldsFilled_whenWebserviceResponseOK() {
//        getInstrumentation().waitForIdleSync();
//        MoreAsserts.assertContainsRegex("Scrum",((TextView) mTestActivity.findViewById(R.id.title)).getText().toString());
//    }

//    public void testPopUp_whenNoConnection() {
//    	getInstrumentation().
//      getInstrumentation().waitForIdleSync();
//      MoreAsserts.assertContainsRegex("Scrum",((TextView) mTestActivity.findViewById(R.id.title)).getText().toString());
//  }
    
}

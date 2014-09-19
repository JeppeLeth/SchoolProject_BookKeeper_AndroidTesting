package edu.kea.pm.bookkeeper.test.activity;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.UiAutomation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import edu.kea.pm.bookkeeper.activity.BookInfoActivity;
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
        setActivityIntent(intent);
        mTestActivity = getActivity();
    }
    
    public void testPreconditions() throws InterruptedException {
        assertNotNull("mTestActivity is null", mTestActivity);
    }
    
    private static Book getTestBook(){
    	Book b = new Book();
    	b.setIsbn("9781906124762");
    	b.setTitle("Title test");
    	b.setAuthors("Authors");
    	b.setComment("comment");
    	b.setDescription("description");
    	b.setIsbn("123456");
    	b.setLanguage("Eng");
    	b.setLoaner("Loaner");
    	b.setPageCount(1);
    	b.setPublished("2041");
    	b.setThumbnailURL(null);
    	return b;
    }
    
    public void testSpinner_showingOnStartup() throws IOException, JSONException, InterruptedException, ExecutionException {
//        assertNotNull("mTestActivity is null", mTestActivity);
//        mTestActivity.

//    	DownloadBooksTask cl = mock(DownloadBooksTask.class);
//    	Mockito.when(cl.download("9781906124762")).thenReturn(getTestBook());
//    	Thread.sleep(5000);

    }
    
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

package edu.kea.pm.bookkeeper.test.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.UiAutomation;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import edu.kea.pm.bookkeeper.R;
import edu.kea.pm.bookkeeper.activity.BookSavedInfoActivity;
import edu.kea.pm.bookkeeper.fragment.BookSavedInfoFragment;
import edu.kea.pm.bookkeeper.model.Book;

@SuppressLint("NewApi")
public class BookSavedInfoActivityTest extends ActivityInstrumentationTestCase2<BookSavedInfoActivity>
{
	public BookSavedInfoActivityTest()
	{
		super(BookSavedInfoActivity.class);
	}

	public BookSavedInfoActivityTest(Class<BookSavedInfoActivity> activityClass)
	{
		super(activityClass);
	}
	
	private BookSavedInfoActivity mTestActivity;
	private Book mBook;



    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getInstrumentation().getUiAutomation().setRotation(UiAutomation.ROTATION_FREEZE_0);
        
        Intent intent = new Intent();
        intent.putExtra(BookSavedInfoActivity.BUNDLE_BOOK, getTestBook());
        setActivityIntent(intent);
        mTestActivity = getActivity();
        mBook = getActivity().getBook();
    }
    
    public void testPreconditions() throws InterruptedException {
        assertNotNull("mTestActivity is null", mTestActivity);
        assertNotNull("mBook is null", mBook);
    }
    
    private static Book getTestBook(){
    	Book b = new Book();
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
    
    public void testBook_hasCorrectValues() {
    	Book expected = getTestBook();
        assertEquals(expected.getTitle(), mBook.getTitle());
        assertEquals(expected.getAuthors(), mBook.getAuthors());
        assertEquals(expected.getComment(), mBook.getComment());
        assertEquals(expected.getDescription(), mBook.getDescription());
        assertEquals(expected.getIsbn(), mBook.getIsbn());
        assertEquals(expected.getLoaner(), mBook.getLoaner());
        assertEquals(expected.getLanguage(), mBook.getLanguage());
        assertEquals(expected.getPageCount(), mBook.getPageCount());
        assertEquals(expected.getPublished(), mBook.getPublished());
        assertEquals(expected.getThumbnailURL(), mBook.getThumbnailURL());
    }
    
    public void testBookSavedInfoFragment_exists() {
    	List<Fragment> list = mTestActivity.getSupportFragmentManager().getFragments();
        assertTrue( list.get(0) instanceof BookSavedInfoFragment );
    }
    
    public void testBookSavedInfoFragment_layout() {
        final View decorView = mTestActivity.getWindow().getDecorView();
        ViewAsserts.assertOnScreen(decorView, mTestActivity.findViewById(R.id.isbn));
        ViewAsserts.assertOnScreen(decorView, mTestActivity.findViewById(R.id.status));
        ViewAsserts.assertOnScreen(decorView, mTestActivity.findViewById(R.id.title));
        ViewAsserts.assertOnScreen(decorView, mTestActivity.findViewById(R.id.author));
        ViewAsserts.assertOnScreen(decorView, mTestActivity.findViewById(R.id.language));
        ViewAsserts.assertOnScreen(decorView, mTestActivity.findViewById(R.id.desc));
        ViewAsserts.assertOnScreen(decorView, mTestActivity.findViewById(R.id.pages));
        ViewAsserts.assertOnScreen(decorView, mTestActivity.findViewById(R.id.published));
        ViewAsserts.assertOnScreen(decorView, mTestActivity.findViewById(R.id.note));
        ViewAsserts.assertOnScreen(decorView, mTestActivity.findViewById(R.id.loaner));
        ViewAsserts.assertOnScreen(decorView, mTestActivity.findViewById(R.id.imageCover));
    }
    
    public void testBookSavedInfoFragment_fieldFilledCorrectly() {
		TextView isbn = (TextView) mTestActivity.findViewById(R.id.isbn);
		ImageView status = (ImageView) mTestActivity.findViewById(R.id.status);
        TextView title = (TextView) mTestActivity.findViewById(R.id.title);
        TextView author = (TextView) mTestActivity.findViewById(R.id.author);
        TextView language = (TextView) mTestActivity.findViewById(R.id.language);
        TextView description = (TextView) mTestActivity.findViewById(R.id.desc);
        TextView pages = (TextView) mTestActivity.findViewById(R.id.pages);
        TextView published = (TextView) mTestActivity.findViewById(R.id.published);
        TextView comment = (TextView) mTestActivity.findViewById(R.id.note);
        TextView loaner = (TextView) mTestActivity.findViewById(R.id.loaner);
        
        assertEquals(title.getText(), mBook.getTitle());
        assertEquals(isbn.getText(), mBook.getIsbn());
        assertEquals(author.getText(), mBook.getAuthors());
        assertEquals(comment.getText(), mBook.getComment());
        assertEquals(description.getText(), mBook.getDescription());
        assertEquals(loaner.getText(), mBook.getLoaner());
        assertEquals(pages.getText(), mBook.getPageCount());
        assertEquals(published.getText(), mBook.getPublished());
        assertEquals(language.getText(), mBook.getLanguage());
    }
    
    //TODO: Test actionbar items and actions
}

package edu.kea.pm.bookkeeper.uitest;

/**
 * A set of constants used globally within the tests
 */
public class Constants {
	/**
	 * Timeout used for asynchronous operations. This is necessary so that we don't
	 * have any infinite running tests that are blocked waiting on results.
	 */
	public static final long TIMEOUT_IN_MS = 10000L;
	
	public static final String TEXT_HINT = "ISBN / barcode";
	public static final String TEST_VALID_ISBN = "9781906124762";
	public static final String TEST_UNKNOWN_ISBN = "123";
	public static final String TEST_BOOK_TITLE = "00 test book";
	public static final String TEST_LOANER_NAME = "John Doe";
}

package edu.kea.pm.bookkeeper.test.database;

import static edu.kea.pm.bookkeeper.database.DatabaseImpl.getInt;
import static edu.kea.pm.bookkeeper.database.DatabaseImpl.getString;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.test.AndroidTestCase;
import edu.kea.pm.bookkeeper.database.BookTable;
import edu.kea.pm.bookkeeper.database.DatabaseHelper;
import edu.kea.pm.bookkeeper.database.DatabaseImpl;
import edu.kea.pm.bookkeeper.database.LoanTable;
import edu.kea.pm.bookkeeper.model.Book;

public class DatabaseImplTest extends AndroidTestCase {

	private DatabaseImpl database;

	private void init() {
		database = new DatabaseImpl(mContext);
		prepareDatabase(getWritableDb());

		assertBookCountInDatabase(0);
		assertLoanerCountInDatabase(0);
	}

	public void test_should_insert_book_without_loaner() {
		init();

		Book book = createBook();
		database.saveBook(book);

		Cursor allBooks = assertBookCountInDatabase(1);

		assertBook(book, allBooks);
		assertNull(getString(allBooks, LoanTable.LOANER));
		assertLoanerCountInDatabase(0);

	}

	public void test_should_insert_book_with_loaner() {
		init();
		
		Book book = createBook();
		book.setLoaner("James Bond");
		database.saveBook(book);

		Cursor allBooks = assertBookCountInDatabase(1);

		assertBook(book, allBooks);
		assertEquals("James Bond", getString(allBooks, LoanTable.LOANER));

		Cursor allLoaners = assertLoanerCountInDatabase(1);

		String bookTableId = getString(allBooks, BookTable.ID);
		String loanTableBookId = getString(allLoaners, LoanTable.BOOK_ID);

		assertEquals(bookTableId, loanTableBookId);
	}

	public void test_should_update_book_without_loaner() {
		init();

		Book book = createBookWithISBN("123456789");

		insertBook(book);

		Cursor allBooks = assertBookCountInDatabase(1);

		int id = getInt(allBooks, BookTable.ID);
		book.setBookId(id);
		book.setIsbn("987654321");

		database.saveBook(book);

		allBooks = assertBookCountInDatabase(1);

		assertEquals(id, getInt(allBooks, BookTable.ID));
		assertEquals("987654321", getString(allBooks, BookTable.ISBN));

		assertNull(getString(allBooks, LoanTable.LOANER));
		assertLoanerCountInDatabase(0);
	}

	public void test_should_update_book_with_loaner() {
		init();

		Book book = createBookWithISBN("123456789");
		book.setLoaner("James Bond");
		
		insertBook(book);

		Cursor allBooks = assertBookCountInDatabase(1);

		int id = getInt(allBooks, BookTable.ID);
		book.setBookId(id);
		book.setIsbn("987654321");
		book.setLoaner("Michael Knight");

		database.saveBook(book);

		allBooks = assertBookCountInDatabase(1);

		assertEquals(id, getInt(allBooks, BookTable.ID));

		assertEquals("987654321", getString(allBooks, BookTable.ISBN));
		assertEquals("Michael Knight", getString(allBooks, LoanTable.LOANER));

		Cursor allLoaners = assertLoanerCountInDatabase(1);

		String bookTableId = getString(allBooks, BookTable.ID);
		String loanTableBookId = getString(allLoaners, LoanTable.BOOK_ID);

		assertEquals(bookTableId, loanTableBookId);
		assertEquals("Michael Knight", getString(allLoaners, LoanTable.LOANER));
	}

	public void test_should_delete_book() {
		init();

		Book book = createBookWithISBN("123456789");

		insertBook(book);

		Cursor allBooks = assertBookCountInDatabase(1);

		int id = getInt(allBooks, BookTable.ID);
		book.setBookId(id);
		
		database.deleteBook(book);

		assertBookCountInDatabase(0);
	}

	public void test_should_get_book_by_id() {
		init();

		Book book = createBookWithISBN("123456789");
		book.setLoaner("James Bond");

		database.saveBook(book);

		Cursor allBooks = assertBookCountInDatabase(1);

		int id = getInt(allBooks, BookTable.ID);

		Book bookWithId = database.getBookWithId(id);

		assertEquals(id, bookWithId.getBookId());
		assertEquals("123456789", bookWithId.getIsbn());
		assertEquals("James Bond", bookWithId.getLoaner());
	}

	private Cursor assertBookCountInDatabase(int expectedCount) {
		Cursor allBooks = database.getAllBooks();
		assertEquals(expectedCount, allBooks.getCount());
		allBooks.moveToFirst();
		return allBooks;
	}

	private Cursor assertLoanerCountInDatabase(int expectedLoanerCount) {
		SQLiteDatabase writableDb = getWritableDb();

		SQLiteQueryBuilder query = new SQLiteQueryBuilder();
		query.setTables(LoanTable.TABLE_NAME);
		String[] columns = new String[] { LoanTable.ID, LoanTable.BOOK_ID,
				LoanTable.LOANER,
				LoanTable.TIMESTAMP };
		Cursor allLoaners = query.query(writableDb, columns, null, null, null,
				null, null);
		assertEquals(expectedLoanerCount, allLoaners.getCount());
		allLoaners.moveToFirst();
		return allLoaners;
	}
	private void assertBook(Book expectedBook, Cursor allBooks) {
		assertEquals(expectedBook.getIsbn(),
				getString(allBooks, BookTable.ISBN));
		assertEquals(expectedBook.getTitle(),
				getString(allBooks, BookTable.TITLE));
		assertEquals(expectedBook.getAuthors(),
				getString(allBooks, BookTable.AUTHORS));
		assertEquals(expectedBook.getDescription(),
				getString(allBooks, BookTable.DESCRIPTION));
		assertEquals(expectedBook.getLanguage(),
				getString(allBooks, BookTable.LANGUAGE));
		assertEquals(expectedBook.getPageCount(),
				getInt(allBooks, BookTable.PAGE_COUNT));
		assertEquals(expectedBook.getComment(),
				getString(allBooks, BookTable.COMMENT));
		assertEquals(expectedBook.getPublished(),
				getString(allBooks, BookTable.PUBLISHED));
	}

	private SQLiteDatabase getWritableDb() {
		DatabaseHelper dbHelper = database.getDb();
		return dbHelper.getWritableDatabase();
	}

	private void prepareDatabase(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + BookTable.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + LoanTable.TABLE_NAME);

		db.execSQL(BookTable.CREATE_TABLE);
		db.execSQL(LoanTable.CREATE_TABLE);
	}

	private void insertBook(Book book) {
		ContentValues contentValues = database.createBookContentValues(book);
		SQLiteDatabase writableDb = getWritableDb();
		writableDb.insert(BookTable.TABLE_NAME, null, contentValues);
	}

	private Book createBook() {
		Book book = new Book();
		book.setIsbn("1234567890");
		book.setTitle("Software Testing");
		book.setDescription("Bestselling book ever..");
		book.setAuthors("Angelina Samaroo, Geoff Thompson, Peter Williams");
		book.setLanguage("en");
		book.setPageCount(224);
		book.setComment("Greate book!");
		book.setPublished("2010");
		return book;
	}

	private Book createBookWithISBN(String isbn) {
		Book book = new Book();
		book.setIsbn(isbn);
		return book;
	}
}

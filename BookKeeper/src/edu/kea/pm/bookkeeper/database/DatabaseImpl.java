package edu.kea.pm.bookkeeper.database;

import static android.text.TextUtils.isEmpty;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import edu.kea.pm.bookkeeper.model.Book;

public class DatabaseImpl implements Database {

	private DatabaseHelper db;

	public DatabaseImpl(Context context) {
		db = new DatabaseHelper(context);
	}

	@Override
	public Book getBookWithId(int id) {
		SQLiteDatabase database = db.getReadableDatabase();

		List<String> columns = getBookTableColumns();
		columns.add(LoanTable.LOANER);
		String[] cols = columns.toArray(new String[columns.size()]);

		SQLiteQueryBuilder query = new SQLiteQueryBuilder();
		query.setTables(BookTable.TABLE_NAME + " LEFT JOIN "
				+ LoanTable.TABLE_NAME + " ON (" + BookTable.TABLE_NAME + "."
				+ BookTable.ID + " = " + LoanTable.TABLE_NAME + "."
				+ LoanTable.BOOK_ID + ")");

		String selection = BookTable.TABLE_NAME + "." + BookTable.ID + " = ?";
		String[] selectionArgs = args(Long.valueOf(id));

		Cursor c = query.query(database, cols, selection, selectionArgs, null,
				null, BookTable.TITLE + " DESC");

		Book b = null;
		if (c.moveToFirst()) {
			b = createBook(c);
		}
		c.close();
		return b;
	}

	@Override
	public Cursor getAllBooks() {
		List<String> columns = getBookTableColumns();
		columns.add(LoanTable.LOANER);
		columns.add(LoanTable.TIMESTAMP);
		String[] cols = columns.toArray(new String[columns.size()]);

		SQLiteDatabase database = db.getReadableDatabase();
		SQLiteQueryBuilder query = new SQLiteQueryBuilder();
		query.setTables(BookTable.TABLE_NAME + " LEFT JOIN "
				+ LoanTable.TABLE_NAME + " ON (" + BookTable.TABLE_NAME + "."
				+ BookTable.ID + " = " + LoanTable.TABLE_NAME + "."
				+ LoanTable.BOOK_ID + ")");
		Cursor c = query.query(database, cols, null, null, null, null,
				BookTable.TITLE + " ASC");

		return c;
	}

	@Override
	public void saveBook(Book book) {
		ContentValues values = createBookContentValues(book);

		if (book.getBookId() > 0) {
			updateBook(book, values);
		} else {
			// Create new book:
			SQLiteDatabase database = db.getWritableDatabase();
			long bookId = database.insert(BookTable.TABLE_NAME, null, values);

			if (!isEmpty(book.getLoaner())) {
				updateLoaner(database, bookId, book.getLoaner());
			}
		}
	}

	@Override
	public void deleteBook(Book book) {
		SQLiteDatabase database = db.getWritableDatabase();
		String[] args = args(book.getBookId());
		database.delete(BookTable.TABLE_NAME, BookTable.ID + " = ?", args);
		database.delete(LoanTable.TABLE_NAME, LoanTable.BOOK_ID + " = ?", args);
	}

	private void updateBook(Book book, ContentValues values) {
		SQLiteDatabase database = db.getWritableDatabase();
		// Update existing book:
		String[] args = args(book.getBookId());
		database.update(BookTable.TABLE_NAME, values, BookTable.ID + " = ?",
				args);
		database.delete(LoanTable.TABLE_NAME, LoanTable.BOOK_ID + " = ?", args);

		if (!isEmpty(book.getLoaner())) {
			updateLoaner(database, book.getBookId(), book.getLoaner());
		}
	}

	private void updateLoaner(SQLiteDatabase database, long bookId,
			String loaner) {
		ContentValues valuesLoaner = new ContentValues();
		valuesLoaner.put(LoanTable.BOOK_ID, bookId);
		valuesLoaner.put(LoanTable.LOANER, loaner);
		database.insert(LoanTable.TABLE_NAME, null, valuesLoaner);
	}

	private Book createBook(Cursor cursor) {
		Book book = new Book();
		book.setBookId(getInt(cursor, BookTable.ID));
		book.setTitle(getString(cursor, BookTable.TITLE));
		book.setIsbn(getString(cursor, BookTable.ISBN));
		book.setAuthors(getString(cursor, BookTable.AUTHORS));
		book.setDescription(getString(cursor, BookTable.DESCRIPTION));
		book.setLanguage(getString(cursor, BookTable.LANGUAGE));
		book.setPageCount(getInt(cursor, BookTable.PAGE_COUNT));
		book.setPublished(getString(cursor, BookTable.PUBLISHED));
		book.setThumbnailURL(getString(cursor, BookTable.PUBLISHED));
		book.setComment(getString(cursor, BookTable.COMMENT));
		book.setLoaner(getString(cursor, LoanTable.LOANER));
		return book;
	}

	public ContentValues createBookContentValues(Book book) {
		ContentValues values = new ContentValues();
		values.put(BookTable.ISBN, book.getIsbn());
		values.put(BookTable.TITLE, book.getTitle());
		values.put(BookTable.DESCRIPTION, book.getDescription());
		values.put(BookTable.AUTHORS, book.getAuthors());
		values.put(BookTable.LANGUAGE, book.getLanguage());
		values.put(BookTable.PAGE_COUNT, book.getPageCount());
		values.put(BookTable.COMMENT, book.getComment());
		values.put(BookTable.IMAGE, book.getThumbnailURL());
		values.put(BookTable.PUBLISHED, book.getPublished());
		return values;
	}



	public List<String> getBookTableColumns() {
		List<String> columns = new ArrayList<String>();
		columns.add(BookTable.TABLE_NAME + "." + BookTable.ID);
		columns.add(BookTable.ISBN);
		columns.add(BookTable.AUTHORS);
		columns.add(BookTable.COMMENT);
		columns.add(BookTable.DESCRIPTION);
		columns.add(BookTable.IMAGE);
		columns.add(BookTable.LANGUAGE);
		columns.add(BookTable.PAGE_COUNT);
		columns.add(BookTable.PUBLISHED);
		columns.add(BookTable.TITLE);
		return columns;
	}

	public static String getString(Cursor c, String columnName) {
		int i = c.getColumnIndex(columnName);
		return c.getString(i);
	}

	public static int getInt(Cursor c, String columnName) {
		return c.getInt(c.getColumnIndex(columnName));
	}

	private String[] args(long l) {
		return new String[] { String.valueOf(l) };
	}

	public DatabaseHelper getDb() {
		return db;
	}
}

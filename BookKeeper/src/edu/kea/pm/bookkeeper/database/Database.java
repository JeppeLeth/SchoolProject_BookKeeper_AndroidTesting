package edu.kea.pm.bookkeeper.database;

import android.database.Cursor;
import edu.kea.pm.bookkeeper.model.Book;

public interface Database {
    public Book getBookWithId(int id);

    public Cursor getAllBooks();

    public void saveBook(Book book);

    public void deleteBook(Book book);
}

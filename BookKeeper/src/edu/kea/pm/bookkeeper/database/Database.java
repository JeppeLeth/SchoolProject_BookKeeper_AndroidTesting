package edu.kea.pm.bookkeeper.database;

import java.util.List;

import edu.kea.pm.bookkeeper.model.*;

public interface Database
{
	
	//Getters
	//TODO: Jesper.
	public Book getBookWithId(String id);
	public List<Book> getAllBooks();

	//Setters:
	public void saveBook(Book book);
	
	public void deleteBook(Book book);
	
	
}

package edu.kea.pm.bookkeeper.model;

import java.io.Serializable;

public class Book implements Serializable {
	private static final long serialVersionUID = -399086889791219587L;

	public final static String BOOK_BUNDLE_KEY = "BOOK_BUNDLE_KEY";
	private long bookId;
	private String isbn;
	private String title;
	private String authors;
	private String description;
	private String language;
	private int pageCount;
	private String published;
	private String thumbnailURL;
	private String loaner;
	private String comment;

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getIsbn() {
		return this.isbn;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public String getPublished() {
		return published;
	}

	public void setPublished(String published) {
		this.published = published;
	}

	public String getThumbnailURL() {
		return thumbnailURL;
	}

	public void setThumbnailURL(String thumbnailURL) {
		this.thumbnailURL = thumbnailURL;
	}

	public String getLoaner() {
		return loaner;
	}

	public void setLoaner(String loaner) {
		this.loaner = loaner;
	}

	public long getBookId() {
		return bookId;
	}

	public void setBookId(long bookId) {
		this.bookId = bookId;
	}

	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", isbn=" + isbn + ", title=" + title
				+ ", authors=" + authors + ", description=" + description
				+ ", language=" + language + ", pageCount=" + pageCount
				+ ", published=" + published + ", thumbnailURL=" + thumbnailURL
				+ ", loaner=" + loaner + ", comment=" + comment + "]";
	}

}

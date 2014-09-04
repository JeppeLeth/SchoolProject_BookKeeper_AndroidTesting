package edu.kea.pm.bookkeeper.api;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import edu.kea.pm.bookkeeper.model.Book;

public class BookAPI {

    protected static final String GOOGLE_API_URL = "https://www.googleapis.com/books/v1/volumes?q=isbn:";

    protected static final String KEY_ITEMS = "items";
    protected static final String KEY_VOLUME_INFO = "volumeInfo";
    protected static final String KEY_TITLE = "title";
    protected static final String KEY_IDENTIFIER = "identifier";
    protected static final String KEY_INDUSTRY_IDENTIFIERS = "industryIdentifiers";
    protected static final String KEY_AUTHORS = "authors";
    protected static final String KEY_THUMBNAIL = "thumbnail";
    protected static final String KEY_IMAGE_LINKS = "imageLinks";
    protected static final String KEY_PUBLISHED_DATE = "publishedDate";
    protected static final String KEY_PAGE_COUNT = "pageCount";
    protected static final String KEY_LANGUAGE = "language";
    protected static final String KEY_DESCRIPTION = "description";



    public static Book readISBN(String isbn) throws IOException, JSONException {
        JSONObject readJsonFromUrl = JsonReader.readJsonFromUrl(GOOGLE_API_URL + isbn);
        return JsonParser.parseJson(readJsonFromUrl);
    }
}

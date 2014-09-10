package edu.kea.pm.bookkeeper.api;

import static edu.kea.pm.bookkeeper.api.BookAPI.KEY_AUTHORS;
import static edu.kea.pm.bookkeeper.api.BookAPI.KEY_DESCRIPTION;
import static edu.kea.pm.bookkeeper.api.BookAPI.KEY_IDENTIFIER;
import static edu.kea.pm.bookkeeper.api.BookAPI.KEY_IMAGE_LINKS;
import static edu.kea.pm.bookkeeper.api.BookAPI.KEY_INDUSTRY_IDENTIFIERS;
import static edu.kea.pm.bookkeeper.api.BookAPI.KEY_ITEMS;
import static edu.kea.pm.bookkeeper.api.BookAPI.KEY_LANGUAGE;
import static edu.kea.pm.bookkeeper.api.BookAPI.KEY_PAGE_COUNT;
import static edu.kea.pm.bookkeeper.api.BookAPI.KEY_PUBLISHED_DATE;
import static edu.kea.pm.bookkeeper.api.BookAPI.KEY_THUMBNAIL;
import static edu.kea.pm.bookkeeper.api.BookAPI.KEY_TITLE;
import static edu.kea.pm.bookkeeper.api.BookAPI.KEY_VOLUME_INFO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.kea.pm.bookkeeper.model.Book;

public class JsonParser {

	public static Book parseJson(JSONObject json) throws IOException,
			JSONException {
		JSONArray items = getJSONArray(json, KEY_ITEMS);

		if (items == null || items.length() == 0) {
			return null;
		}

		JSONObject bookJson = items.getJSONObject(0);
		JSONObject volumeInfo = getJSONObject(bookJson, KEY_VOLUME_INFO);

		if (volumeInfo == null) {
			return null;
		}
		return parseBookInfo(volumeInfo);
	}

	private static Book parseBookInfo(JSONObject volumeInfo)
			throws JSONException {
		Book book = new Book();
		book.setTitle(getString(volumeInfo, KEY_TITLE));
		book.setDescription(getString(volumeInfo, KEY_DESCRIPTION));
		book.setLanguage(getString(volumeInfo, KEY_LANGUAGE));
		book.setPublished(getString(volumeInfo, KEY_PUBLISHED_DATE));
		book.setPageCount(getInt(volumeInfo, KEY_PAGE_COUNT));
		book.setIsbn(getIsbn(volumeInfo));
		book.setAuthors(getAuthors(volumeInfo));
		book.setThumbnailURL(getThumbnailURL(volumeInfo));
		return book;
	}

	public static String getString(JSONObject object, String key)
			throws JSONException {
		return object.has(key) ? object.getString(key) : null;
	}

	public static int getInt(JSONObject object, String key)
			throws JSONException {
		return object.has(key) ? object.getInt(key) : 0;
	}

	public static JSONObject getJSONObject(JSONObject object, String key)
			throws JSONException {
		return object.has(key) ? object.getJSONObject(key) : null;
	}

	public static JSONArray getJSONArray(JSONObject object, String key)
			throws JSONException {
		return object.has(key) ? object.getJSONArray(key) : null;
	}

	public static String getAuthors(JSONObject volumeInfo) throws JSONException {
		JSONArray authors = getJSONArray(volumeInfo, KEY_AUTHORS);
		if (authors == null) {
			return null;
		}
		List<String> names = getAuthorNames(authors);

		return join(names, ", ");
	}

	private static List<String> getAuthorNames(JSONArray authors)
			throws JSONException {
		List<String> names = new ArrayList<String>();
		for (int i = 0; i < authors.length(); i++) {
			names.add(authors.getString(i));
		}
		return names;
	}

	public static String getThumbnailURL(JSONObject volumeInfo)
			throws JSONException {
		JSONObject imageLinks = getJSONObject(volumeInfo, KEY_IMAGE_LINKS);
		return imageLinks == null ? null : getString(imageLinks, KEY_THUMBNAIL);
	}

	public static String getIsbn(JSONObject volumeInfo) throws JSONException {
		JSONArray industryIdentifiers = getJSONArray(volumeInfo,
				KEY_INDUSTRY_IDENTIFIERS);
		if (industryIdentifiers == null) {
			return null;
		}
		JSONObject id = industryIdentifiers.getJSONObject(0);
		return getString(id, KEY_IDENTIFIER);
	}

	private static String join(List<String> list, String separator) {
		if (list == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		int endIndex = list.size();

		for (int i = 0; i < endIndex; i++) {
			if (i > 0) {
				sb.append(separator);
			}
			if (list.get(i) != null) {
				sb.append(list.get(i));
			}
		}
		return sb.toString();
	}

}

package edu.kea.pm.bookkeeper.api;

import static edu.kea.pm.bookkeeper.api.BookAPI.KEY_AUTHORS;
import static edu.kea.pm.bookkeeper.api.BookAPI.KEY_IDENTIFIER;
import static edu.kea.pm.bookkeeper.api.BookAPI.KEY_IMAGE_LINKS;
import static edu.kea.pm.bookkeeper.api.BookAPI.KEY_INDUSTRY_IDENTIFIERS;
import static edu.kea.pm.bookkeeper.api.BookAPI.KEY_THUMBNAIL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.kea.pm.bookkeeper.model.Book;

public class JsonParserTest {

    private static JSONObject jsonResponse;

    @BeforeClass
    public static void init() throws JSONException {
        jsonResponse = new JSONObject(readFile("etc/book_info_json.txt"));
    }

    @Test
    public void should_parse_title() throws IOException, JSONException {
        Book book = JsonParser.parseJson(jsonResponse);
        assertEquals("Software Testing", book.getTitle());
    }

    @Test
    public void should_parse_description() throws IOException, JSONException {
        Book book = JsonParser.parseJson(jsonResponse);
        assertEquals(
                "The bestselling software testing title is the only official textbook of the ISTQB - ISEB Foundation Certificate in Software Testing. This revised 2nd edition covers the 2010 update to the exam syllabus. It is ideal for those with a little experience of software testing who wish to cement their knowledge with industry-recognised techniques and theory. \"Succinctly and clearly written with no non-sense. An unreserved 5 for value for money\" IT Training Magazine (referring to 1st edition)",
                book.getDescription());
    }

    @Test
    public void should_parse_language() throws IOException, JSONException {
        Book book = JsonParser.parseJson(jsonResponse);
        assertEquals("en", book.getLanguage());
    }

    @Test
    public void should_parse_published_date() throws IOException, JSONException {
        Book book = JsonParser.parseJson(jsonResponse);
        assertEquals("2010", book.getPublished());
    }

    @Test
    public void should_parse_page_count() throws IOException, JSONException {
        Book book = JsonParser.parseJson(jsonResponse);
        assertEquals(224, book.getPageCount());
    }

    @Test
    public void should_parse_isbn() throws IOException, JSONException {
        Book book = JsonParser.parseJson(jsonResponse);
        assertEquals("9781906124762", book.getIsbn());
    }

    @Test
    public void should_parse_authors() throws IOException, JSONException {
        Book book = JsonParser.parseJson(jsonResponse);
        assertEquals("Angelina Samaroo, Geoff Thompson, Peter Williams", book.getAuthors());
    }

    @Test
    public void should_parse_thumbnail_url() throws IOException, JSONException {
        Book book = JsonParser.parseJson(jsonResponse);
        assertEquals(
                "http://bks0.books.google.dk/books?id=sFj3SsYYRJEC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                book.getThumbnailURL());
    }

    @Test
    public void should_return_null_if_items_object_missing() throws IOException, JSONException {
        assertNull(JsonParser.parseJson(new JSONObject()));
    }

    @Test
    public void should_return_null_if_items_array_is_emtpy() throws IOException, JSONException {
        JSONObject json = new JSONObject();
        json.put(BookAPI.KEY_ITEMS, new JSONArray());

        assertNull(JsonParser.parseJson(json));
    }

    @Test
    public void should_return_null_if_volume_info_missing() throws IOException, JSONException {
        JSONObject json = new JSONObject();
        JSONArray items = new JSONArray();
        items.put(new JSONObject());

        json.put(BookAPI.KEY_ITEMS, items);

        assertNull(JsonParser.parseJson(json));
    }

    @Test
    public void should_return_parsed_book() throws IOException, JSONException {
        JSONObject json = new JSONObject();
        JSONObject bookJson = new JSONObject();
        bookJson.put(BookAPI.KEY_VOLUME_INFO, new JSONObject());

        JSONArray items = new JSONArray();
        items.put(bookJson);

        json.put(BookAPI.KEY_ITEMS, items);

        assertNotNull(JsonParser.parseJson(json));
    }

    @Test
    public void should_return_string_if_key_exists() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("KEY_FOR_STRING", "Value");

        assertEquals("Value", JsonParser.getString(object, "KEY_FOR_STRING"));
    }

    @Test
    public void should_return_int_if_key_exists() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("KEY_FOR_INT", 1);

        assertEquals(1, JsonParser.getInt(object, "KEY_FOR_INT"));
    }

    @Test
    public void should_return_object_if_key_exists() throws JSONException {
        JSONObject object = new JSONObject();
        JSONObject innerObject = new JSONObject();
        object.put("KEY_FOR_JSON_OBJECT", innerObject);

        assertEquals(innerObject, JsonParser.getJSONObject(object, "KEY_FOR_JSON_OBJECT"));
    }

    @Test
    public void should_return_array_if_key_exists() throws JSONException {
        JSONObject object = new JSONObject();
        JSONArray array = new JSONArray();
        object.put("KEY_FOR_JSON_ARRAY", array);

        assertEquals(array, JsonParser.getJSONArray(object, "KEY_FOR_JSON_ARRAY"));
    }

    @Test
    public void should_return_null_if_key_dosent_exist() throws JSONException {
        JSONObject object = new JSONObject();

        assertNull(JsonParser.getString(object, "KEY_FOR_STRING"));
        assertEquals(0, JsonParser.getInt(object, "KEY_FOR_INT"));
        assertNull(JsonParser.getJSONObject(object, "KEY_FOR_JSON_OBJECT"));
        assertNull(JsonParser.getJSONArray(object, "KEY_FOR_JSON_ARRAY"));
    }

    @Test
    public void should_return_null_if_authors_missing() throws JSONException {
        JSONObject object = new JSONObject();

        assertNull(JsonParser.getAuthors(object));
    }

    @Test
    public void should_return_one_author() throws JSONException {
        JSONArray array = new JSONArray();
        array.put("John");
        JSONObject object = new JSONObject();
        object.put(KEY_AUTHORS, array);

        assertEquals("John", JsonParser.getAuthors(object));
    }

    @Test
    public void should_return_multiple_authors() throws JSONException {
        JSONArray array = new JSONArray();
        array.put("John");
        array.put("Mark");
        array.put("Peter");

        JSONObject object = new JSONObject();
        object.put(KEY_AUTHORS, array);

        assertEquals("John, Mark, Peter", JsonParser.getAuthors(object));
    }

    @Test
    public void should_return_null_if_image_links_missing() throws JSONException {
        JSONObject object = new JSONObject();

        assertNull(JsonParser.getThumbnailURL(object));
    }

    @Test
    public void should_return_null_if_thumbnail_missing() throws JSONException {
        JSONObject object = new JSONObject();
        object.put(KEY_IMAGE_LINKS, new JSONObject());

        assertNull(JsonParser.getThumbnailURL(object));
    }

    @Test
    public void should_return_thumbnail() throws JSONException {
        JSONObject imgLinks = new JSONObject();
        imgLinks.put(KEY_THUMBNAIL, "thumbnail");
        JSONObject object = new JSONObject();
        object.put(KEY_IMAGE_LINKS, imgLinks);

        assertEquals("thumbnail", JsonParser.getThumbnailURL(object));
    }

    @Test
    public void should_return_null_if_industry_identifiers_missing() throws JSONException {
        JSONObject volumeInfo = new JSONObject();
        assertNull(JsonParser.getIsbn(volumeInfo));
    }

    @Test
    public void should_return_null_if_identifier_is_missing() throws JSONException {
        JSONArray industryIdentifiers = new JSONArray();
        industryIdentifiers.put(0, new JSONObject());
        industryIdentifiers.put(1, new JSONObject());
        JSONObject volumeInfo = new JSONObject();
        volumeInfo.put(KEY_INDUSTRY_IDENTIFIERS, industryIdentifiers);

        assertNull(JsonParser.getIsbn(volumeInfo));
    }

    @Test
    public void should_return_isbn() throws JSONException {
        JSONObject identifier = new JSONObject();
        identifier.put(KEY_IDENTIFIER, "123456789");
        JSONArray industryIdentifiers = new JSONArray();
        industryIdentifiers.put(0, identifier);

        JSONObject volumeInfo = identifier;
        volumeInfo.put(KEY_INDUSTRY_IDENTIFIERS, industryIdentifiers);

        assertEquals("123456789", JsonParser.getIsbn(volumeInfo));
    }

    public static String readFile(String filename) {
        String content = null;
        File file = new File(filename);
        try {
            FileReader reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

}

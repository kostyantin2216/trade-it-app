package com.tradeitsignals.datamodel.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.tradeitsignals.datamodel.BaseDAO;
import com.tradeitsignals.datamodel.data.ContentPage;
import com.tradeitsignals.datamodel.enums.ContentPageType;

import java.util.Date;

/**
 * Created by Kostyantin on 8/18/2015.
 */
public class ContentPageDAO extends BaseDAO<ContentPage> {

    public final static String TABLE_NAME = "content_page";

    public final static String KEY_ID = "id";
    public final static String KEY_TYPE = "type";
    public final static String KEY_TITLE = "title";
    public final static String KEY_DESCRIPTION = "description";
    public final static String KEY_TEMPLATE_FILE_NAME = "template_file_name";
    public final static String KEY_HTML_CONTENT = "html_content";
    public final static String KEY_ICON_RES_ID = "icon_res_id";
    public final static String KEY_CREATED_AT = "created_at";
    public final static String KEY_UPDATED_AT = "updated_at";

    public final static String CMD_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_TYPE + " INTEGER, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_TITLE + " TEXT, "
            + KEY_TEMPLATE_FILE_NAME + " TEXT, "
            + KEY_HTML_CONTENT + " TEXT, "
            + KEY_ICON_RES_ID + " TEXT, "
            + KEY_CREATED_AT + " INTEGER, "
            + KEY_UPDATED_AT + " INTEGER);";

    private ContentPageDAO() { }

    private static ContentPageDAO instance;

    public static ContentPageDAO getInstance() {
        if (instance == null) {
            instance = new ContentPageDAO();
        }

        return instance;
    }
    @Override
    public ContentValues extractContentValues(ContentPage page) {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, page.getId());
        values.put(KEY_TYPE, page.getType() != null ? page.getType().id() : -1);
        values.put(KEY_TITLE, page.getTitle());
        values.put(KEY_DESCRIPTION, page.getDescription());
        values.put(KEY_TEMPLATE_FILE_NAME, page.getTemplateFileName());
        values.put(KEY_HTML_CONTENT, page.getHtmlContent());
        values.put(KEY_ICON_RES_ID, page.getAndroidIconResId());
        values.put(KEY_CREATED_AT, page.getUpdatedAt().getTime());
        values.put(KEY_UPDATED_AT, page.getUpdatedAt().getTime());

        return values;
    }

    @Override
    public ContentPage getObject(Cursor c) {
        ContentPage page = new ContentPage();
        page.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        page.setType(ContentPageType.findById(c.getInt(c.getColumnIndex(KEY_HTML_CONTENT))));
        page.setTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
        page.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
        page.setTemplateFileName(c.getString(c.getColumnIndex(KEY_TEMPLATE_FILE_NAME)));
        page.setHtmlContent(c.getString(c.getColumnIndex(KEY_HTML_CONTENT)));
        page.setAndroidIconResId(c.getString(c.getColumnIndex(KEY_ICON_RES_ID)));
        page.setCreatedAt(new Date(c.getLong(c.getColumnIndex(KEY_CREATED_AT))));
        page.setUpdatedAt(new Date(c.getLong(c.getColumnIndex(KEY_UPDATED_AT))));

        return page;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getIdKey() {
        return KEY_ID;
    }
}

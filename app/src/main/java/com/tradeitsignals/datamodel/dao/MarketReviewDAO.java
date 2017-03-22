package com.tradeitsignals.datamodel.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.tradeitsignals.datamodel.BaseDAO;
import com.tradeitsignals.datamodel.data.MarketReview;

import java.util.Date;

/**
 * Created by Kostyantin on 6/14/2015.
 */
public class MarketReviewDAO extends BaseDAO<MarketReview> {

    public static final String TABLE_NAME = "market_reviews";

    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_IMAGE_URL = "image_url";
    public static final String KEY_CREATED_AT = "created_at";

    public static final String CMD_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_TITLE + " TEXT NOT NULL, "
            + KEY_CONTENT + " TEXT NOT NULL, "
            + KEY_IMAGE_URL + " TEXT, "
            + KEY_CREATED_AT + " INTEGER NOT NULL);";

    private MarketReviewDAO() { }

    private static class holder {
        private static MarketReviewDAO instance;
    }

    public static MarketReviewDAO getInstance() {
        if(holder.instance == null) {
            holder.instance = new MarketReviewDAO();
        }
        return holder.instance;
    }

    @Override
    public ContentValues extractContentValues(MarketReview review) {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, review.getId());
        values.put(KEY_TITLE, review.getTitle());
        values.put(KEY_CONTENT, review.getContent());
        values.put(KEY_IMAGE_URL, review.getImageUrl());
        values.put(KEY_CREATED_AT, review.getCreatedAt().getTime());

        return values;
    }

    @Override
    public MarketReview getObject(Cursor c) {
        MarketReview review = new MarketReview();
        review.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        review.setTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
        review.setContent(c.getString(c.getColumnIndex(KEY_CONTENT)));
        review.setImageUrl(c.getString(c.getColumnIndex(KEY_IMAGE_URL)));
        review.setCreatedAt(new Date(c.getLong(c.getColumnIndex(KEY_CREATED_AT))));

        return review;
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

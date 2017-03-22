package com.tradeitsignals.datamodel.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.tradeitsignals.datamodel.BaseDAO;
import com.tradeitsignals.datamodel.data.User;
import com.tradeitsignals.utils.PrefUtils;

import java.util.Date;
import java.util.List;


/**
 * Created by Kostyantin on 6/6/2015.
 */
public class UserDAO extends BaseDAO<User> {

    public final static String TABLE_NAME = "user";

    public static final String KEY_ID = "id";
    public static final String KEY_FIRST_NAME = "first_name";
    public static final String KEY_LAST_NAME = "last_name";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_TELE = "telephone";
    public static final String KEY_COUNTRY_ID = "country_id";
    public static final String KEY_CHANNELS = "channels";
    public static final String KEY_IS_REGISTERED = "is_registered";
    public static final String KEY_IS_ADMIN = "is_admin";
    public static final String KEY_IS_ON_TRIAL = "is_on_trial";
    public static final String KEY_LAST_PAYMENT = "last_payment";
    public static final String KEY_UPDATED_AT = "updated_at";
    public static final String KEY_CREATED_AT = "created_at";

    public static final String CMD_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_FIRST_NAME + " TEXT, "
            + KEY_LAST_NAME + " TEXT, "
            + KEY_PASSWORD + " TEXT, "
            + KEY_EMAIL + " TEXT, "
            + KEY_TELE + " TEXT, "
            + KEY_COUNTRY_ID + " INTEGER, "
            + KEY_CHANNELS + " TEXT, "
            + KEY_IS_REGISTERED + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_IS_ADMIN + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_IS_ON_TRIAL + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_LAST_PAYMENT + " INTEGER, "
            + KEY_UPDATED_AT + " INTEGER, "
            + KEY_CREATED_AT + " INTEGER);";

    private UserDAO() { }

    private static class holder {
        private static UserDAO instance;
    }

    public static UserDAO getInstance() {
        if(holder.instance == null) {
            holder.instance = new UserDAO();
        }
        return holder.instance;
    }

    public User getUser() {
        User user = findById(PrefUtils.getUserId());
        if(user == null) {
            List<User> users = findAll();
            if(!users.isEmpty()) {
                user = users.get(0);
            }
        }
        return user;
    }

    @Override
    public ContentValues extractContentValues(User user) {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, user.getId());
        values.put(KEY_FIRST_NAME, user.getFirstName());
        values.put(KEY_LAST_NAME, user.getLastName());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_TELE, user.getTelephone());
        values.put(KEY_COUNTRY_ID, user.getCountryId());
        values.put(KEY_CHANNELS, user.getChannels());
        values.put(KEY_IS_REGISTERED, user.isRegistered() ? 1 : 0);
        values.put(KEY_IS_ADMIN, user.isAdmin() ? 1 : 0);
        values.put(KEY_IS_ON_TRIAL, user.isOnTrial() ? 1 : 0);
        values.put(KEY_LAST_PAYMENT, user.getLastPayment() != null ? user.getLastPayment().getTime() : null);
        values.put(KEY_UPDATED_AT, user.getUpdatedAt() != null ? user.getUpdatedAt().getTime() : null);
        values.put(KEY_CREATED_AT, user.getUpdatedAt() != null ? user.getUpdatedAt().getTime() : null);

        return values;
    }

    @Override
    public User getObject(Cursor c) {
        User user = new User();
        user.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        user.setFirstName(c.getString(c.getColumnIndex(KEY_FIRST_NAME)));
        user.setLastName(c.getString(c.getColumnIndex(KEY_LAST_NAME)));
        user.setPassword(c.getString(c.getColumnIndex(KEY_PASSWORD)));
        user.setEmail(c.getString(c.getColumnIndex(KEY_EMAIL)));
        user.setTelephone(c.getString(c.getColumnIndex(KEY_TELE)));
        user.setCountryId(c.getInt(c.getColumnIndex(KEY_COUNTRY_ID)));
        user.setChannels(c.getString(c.getColumnIndex(KEY_CHANNELS)));
        user.setIsRegistered(c.getInt(c.getColumnIndex(KEY_IS_REGISTERED)) == 1);
        user.setIsAdmin(c.getInt(c.getColumnIndex(KEY_IS_ADMIN)) == 1);
        user.setIsOnTrial(c.getInt(c.getColumnIndex(KEY_IS_ON_TRIAL)) == 1);
        user.setLastPayment(new Date(c.getLong(c.getColumnIndex(KEY_LAST_PAYMENT))));
        user.setUpdatedAt(new Date(c.getLong(c.getColumnIndex(KEY_UPDATED_AT))));
        user.setCreatedAt(new Date(c.getLong(c.getColumnIndex(KEY_CREATED_AT))));

        return user;
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

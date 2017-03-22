package com.tradeitsignals.datamodel.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.tradeitsignals.datamodel.BaseDAO;
import com.tradeitsignals.datamodel.data.Currency;

import java.util.List;

/**
 * Created by Kostyantin on 7/15/2015.
 */
public class CurrencyDAO extends BaseDAO<Currency> {

    public final static String TABLE_NAME = "currencies";

    public final static String KEY_ID = "id";
    public final static String KEY_COUNTRY = "country";
    public final static String KEY_ISO_CODE = "iso_code";
    public final static String KEY_DESCRIPTION = "description";
    public final static String KEY_RATE = "rate";
    public final static String KEY_FLAG_RES_ID = "flag_res_id";
    public final static String KEY_LAST_UPDATED = "last_updated";

    public final static String CMD_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_COUNTRY + " TEXT, "
            + KEY_ISO_CODE + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_RATE + " REAL, "
            + KEY_FLAG_RES_ID + " INTEGER, "
            + KEY_LAST_UPDATED + " INTEGER);";

    private CurrencyDAO() { }

    private static class holder {
        private static CurrencyDAO instance;
    }

    public static CurrencyDAO getInstance() {
        if(holder.instance == null) {
            holder.instance = new CurrencyDAO();
        }
        return holder.instance;
    }

    public Currency findByIsoCode(String isoCode) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ISO_CODE + " = ?;";
        List<Currency> l =  findByQuery(query, new String[] {isoCode});
        if(l != null && !l.isEmpty()) {
            return l.get(0);
        }
        return null;
    }

    @Override
    public ContentValues extractContentValues(Currency currency) {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, currency.getId());
        values.put(KEY_COUNTRY, currency.getCountry());
        values.put(KEY_ISO_CODE, currency.getIsoCode());
        values.put(KEY_DESCRIPTION, currency.getDescription());
        values.put(KEY_RATE, currency.getRate());
        values.put(KEY_FLAG_RES_ID, currency.getFlagResId());
        values.put(KEY_LAST_UPDATED, currency.getLastUpdated());
        return values;
    }

    @Override
    public Currency getObject(Cursor c) {
        Currency currency = new Currency();
        currency.setId(c.getString(c.getColumnIndex(KEY_ID)));
        currency.setCountry(c.getString(c.getColumnIndex(KEY_COUNTRY)));
        currency.setIsoCode(c.getString(c.getColumnIndex(KEY_ISO_CODE)));
        currency.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
        currency.setRate(c.getDouble(c.getColumnIndex(KEY_RATE)));
        currency.setFlagResId(c.getInt(c.getColumnIndex(KEY_FLAG_RES_ID)));
        currency.setLastUpdated(c.getLong(c.getColumnIndex(KEY_LAST_UPDATED)));
        return currency;
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

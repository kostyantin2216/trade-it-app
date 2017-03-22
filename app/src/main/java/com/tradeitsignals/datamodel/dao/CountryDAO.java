package com.tradeitsignals.datamodel.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.tradeitsignals.datamodel.BaseDAO;
import com.tradeitsignals.datamodel.data.Country;
import com.tradeitsignals.utils.CommonUtils;

import java.util.List;

/**
 * Created by Kostyantin on 10/19/2015.
 */
public class CountryDAO extends BaseDAO<Country> {

    public final static String TABLE_NAME = "countries";

    public final static String KEY_ID = "id";
    public final static String KEY_NAME = "name";
    public final static String KEY_ISO_3 = "iso_3";
    public final static String KEY_ISO_2 = "iso_2";
    public final static String KEY_DIAL_CODE = "dial_code";




    public static final String CMD_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_ISO_3 + " TEXT NOT NULL, "
            + KEY_ISO_2 + " TEXT NOT NULL, "
            + KEY_DIAL_CODE + " TEXT NOT NULL);";





    private static CountryDAO instance = new CountryDAO();

    public static CountryDAO getInstance() {
        return instance;
    }

    private CountryDAO() { }

    public Country findByName(String name) {
        if(name != null) {
            List<Country> countries = findByProperty(KEY_NAME, CommonUtils.capitalize(name.toLowerCase()));

            if (countries.isEmpty()) {
                return null;
            }
            return countries.get(0);
        }
        return null;
    }

    public Country findByIso3(String iso3) {
        List<Country> countries = findByProperty(KEY_ISO_3, iso3.toUpperCase());

        if(countries.isEmpty()) {
            return null;
        }

        return countries.get(0);
    }

    public Country findByIso2(String iso2) {
        List<Country> countries = findByProperty(KEY_ISO_2, iso2.toUpperCase());

        if(countries.isEmpty()) {
            return null;
        }

        return countries.get(0);
    }

    @Override
    public ContentValues extractContentValues(Country country) {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, country.getId());
        values.put(KEY_NAME, country.getName());
        values.put(KEY_ISO_3, country.getIso3());
        values.put(KEY_ISO_2, country.getIso2());
        values.put(KEY_DIAL_CODE, country.getDialCode());
        return values;
    }

    @Override
    public Country getObject(Cursor c) {
        return new Country(
                c.getInt(c.getColumnIndex(KEY_ID)),
                c.getString(c.getColumnIndex(KEY_NAME)),
                c.getString(c.getColumnIndex(KEY_ISO_3)),
                c.getString(c.getColumnIndex(KEY_ISO_2)),
                c.getString(c.getColumnIndex(KEY_DIAL_CODE))
        );
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

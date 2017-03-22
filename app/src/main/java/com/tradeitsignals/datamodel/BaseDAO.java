package com.tradeitsignals.datamodel;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tradeitsignals.helpers.DatabaseHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Kostyantin on 6/6/2015.
 */
public abstract class BaseDAO<T extends DataModelObject> implements CommonDAO<T> {

    public static final String LOG_TAG = "#DAO";

    private final DatabaseHelper dbHelper;

    public BaseDAO() {
        this.dbHelper = DatabaseHelper.getInstance();
    }

    public boolean insert(T obj) {
        SQLiteDatabase db = dbHelper.openDatabase();

        ContentValues values = extractContentValues(obj);

        boolean createSuccessful = db.insert(getTableName(), null, values) != -1;

        dbHelper.closeDatabase();

        return createSuccessful;
    }


    public int insert(List<T> objs) {
        SQLiteDatabase db = dbHelper.openDatabase();

        int errors = 0;
        for(T obj : objs) {
            ContentValues values = extractContentValues(obj);

            if(db.insert(getTableName(), null, values) == -1) {
                errors++;
            }
        }

        dbHelper.closeDatabase();

        return errors;
    }

    public T findById(Integer id) {
        SQLiteDatabase db = dbHelper.openDatabase();

        String where = getIdKey() + " = '" + id + "'";
        Cursor c = db.query(getTableName(), null, where, null, null, null, null);

        T obj = null;

        if(c.moveToFirst()) {
            obj = getObject(c);
        }

        c.close();
        dbHelper.closeDatabase();

        return obj;
    }

    public T findByName(String name){
        SQLiteDatabase db = dbHelper.openDatabase();

        String where = getIdKey() + " = '" + name + "'";
        Cursor c = db.query(getTableName(), null, where, null, null, null, null);

        T obj = null;

        if(c.moveToFirst()) {
            obj = getObject(c);
        }

        c.close();
        dbHelper.closeDatabase();

        return obj;
    }

    public List<T> findByQuery(String query, String[] values) {
        SQLiteDatabase db = dbHelper.openDatabase();

        Cursor c = db.rawQuery(query, values);

        List<T> objs = null;
        if(c.moveToFirst()) {
            objs = new ArrayList<>();
            do {
                objs.add(getObject(c));
            } while(c.moveToNext());
        }

        c.close();
        dbHelper.closeDatabase();

        return objs;
    }

    public List<T> findByProperty(String property, String value) {
        SQLiteDatabase db = dbHelper.openDatabase();

        String selection = property + " = ?";
        String[] selectionArgs = new String[] {value};
        Cursor c = db.query(getTableName(), null, selection, selectionArgs, null, null, null);

        List<T> objs = null;
        if(c.moveToFirst()) {
            objs = new ArrayList<>();
            do {
                objs.add(getObject(c));
            } while(c.moveToNext());
        }

        c.close();
        dbHelper.closeDatabase();

        if(objs == null)  {
            objs = Collections.emptyList();
        }

        return objs;
    }

   /* public List<T> findByMap(Map<String, Object> propertiesMap) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();



        return null;
    }

    private String getSelectionString(Map<String, Object> propertiesMap) {
        String selection = "";
        for(Map.Entry<String, Object> entry : propertiesMap.entrySet()) {

        }
        return null;
    }*/

    public List<T> findAll() {
        return findAll(null);
    }

    public List<T> findAll(String orderBy) {
        SQLiteDatabase db = dbHelper.openDatabase();

        Cursor c = db.query(getTableName(), null, null, null, null, null, orderBy);

        List<T> objs = null;
        if(c.moveToFirst()) {
            objs = new ArrayList<>();
            do {
                objs.add(getObject(c));
            } while(c.moveToNext());
        }

        c.close();
        dbHelper.closeDatabase();

        if(objs == null)  {
            objs = Collections.emptyList();
        }

        return objs;
    }

    public boolean update(T obj) {
        SQLiteDatabase db = dbHelper.openDatabase();

        ContentValues values = extractContentValues(obj);

        boolean updateSuccesfull = db.update(getTableName(), values, getIdKey() + " = ?", new String[]{values.getAsString(getIdKey())}) > 0;

        dbHelper.closeDatabase();

        return updateSuccesfull;
    }

    public int updateAll(List<T> objs) {
        SQLiteDatabase db = dbHelper.openDatabase();

        int failures = 0;
        for(T obj : objs) {
            ContentValues values = extractContentValues(obj);

            if(db.update(getTableName(), values, getIdKey() + " = ?", new String[]{values.getAsString(getIdKey())}) == 0) {
                failures++;
            }
        }

        dbHelper.closeDatabase();

        return failures;
    }

    public boolean delete(String id) {
        SQLiteDatabase db = dbHelper.openDatabase();

        boolean deleteSuccesfull = db.delete(getTableName(), getIdKey() + " = ?", new String[]{id}) != 0;

        dbHelper.closeDatabase();

        return deleteSuccesfull;
    }

    public boolean contains(Serializable id) {
        SQLiteDatabase db = dbHelper.openDatabase();

        String query = "SELECT count(*) FROM " + getTableName() + " WHERE " + getIdKey() + " = '" + id + "'";
        Cursor c = db.rawQuery(query, null);

        boolean contains = false;
        if(c.moveToFirst()) {
            contains = c.getInt(0) > 0;
        }

        c.close();
        dbHelper.closeDatabase();

        return contains;
    }

    public int count() {
        SQLiteDatabase db = dbHelper.openDatabase();

        String query = "SELECT count(*) FROM " + getTableName();
        Cursor c = db.rawQuery(query, null);

        int count = 0;
        if(c.moveToFirst()) {
            count = c.getInt(0);
        }

        c.close();
        dbHelper.closeDatabase();

        return count;
    }

    public int countQuery(String query, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.openDatabase();

        Cursor c = db.rawQuery(query, selectionArgs);

        int count = 0;
        if(c.moveToFirst()) {
            count = c.getInt(0);
        }

        c.close();
        dbHelper.closeDatabase();

        return count;
    }

    protected abstract ContentValues extractContentValues(T obj);
    protected abstract T getObject(Cursor c);
    protected abstract String getIdKey();

    protected String getLogTag() {
        return "#" + getTableName() + "DAO";
    }
}

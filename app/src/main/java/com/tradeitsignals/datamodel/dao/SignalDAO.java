package com.tradeitsignals.datamodel.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.tradeitsignals.datamodel.BaseDAO;
import com.tradeitsignals.datamodel.data.Signal;
import com.tradeitsignals.datamodel.enums.SignalStatus;

import java.util.Date;
import java.util.List;

/**
 * Created by Kostyantin on 6/6/2015.
 */
public class SignalDAO extends BaseDAO<Signal> {

    public static final String TABLE_NAME = "signals";

    public static final String KEY_ID = "id";
    public static final String KEY_CREATED_AT = "created_at";
    public static final String KEY_EXPIRY_TIME = "expiry_time";
    public static final String KEY_ASSET = "asset";
    public static final String KEY_ENTRY_RATE = "entry_rate";
    public static final String KEY_EXPIRY_RATE = "expiry_rate";
    public static final String KEY_IS_CALL = "is_call";
    public static final String KEY_STATUS = "status";

    public static final String CMD_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_CREATED_AT + " INTEGER NOT NULL, "
            + KEY_EXPIRY_TIME + " INTEGER NOT NULL, "
            + KEY_ASSET + " TEXT NOT NULL, "
            + KEY_ENTRY_RATE + " REAL NOT NULL, "
            + KEY_EXPIRY_RATE + " REAL NOT NULL, "
            + KEY_IS_CALL + " INTEGER NOT NULL, "
            + KEY_STATUS + " INTEGER NOT NULL DEFAULT 0);";


    private SignalDAO() { }

    private static class holder {
        private static SignalDAO instance;
    }

    public static SignalDAO getInstance() {
        if(holder.instance == null) {
            holder.instance = new SignalDAO();
        }
        return holder.instance;
    }

    public List<Signal> getActiveSignals() {
        String query = "SELECT * FROM " + TABLE_NAME
                + " WHERE " + KEY_EXPIRY_TIME + " > ?"
                + " ORDER BY " +  KEY_EXPIRY_TIME + " DESC";
        String[] values = new String[] {String.valueOf(System.currentTimeMillis())};

        return findByQuery(query, values);
    }

    public List<Signal> getExpiredSignals() {
        String query = "SELECT * FROM " + TABLE_NAME
                + " WHERE " + KEY_EXPIRY_TIME + " < ?"
                + " ORDER BY " +  KEY_EXPIRY_TIME + " DESC";
        String[] values = new String[] {String.valueOf(System.currentTimeMillis())};

        return findByQuery(query, values);
    }

    public int getActiveSignalsCount() {
        String query = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE " + KEY_EXPIRY_TIME + " > ?";
        String[] values = new String[] {String.valueOf(System.currentTimeMillis())};

        return countQuery(query, values);
    }

    public int getExpiredSignalsCount() {
        String query = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE " + KEY_EXPIRY_TIME + " < ?";
        String[] values = new String[] {String.valueOf(System.currentTimeMillis())};

        return countQuery(query, values);
    }

    public boolean contains(Signal signal) {
        return contains(signal.getId());
    }

    @Override
    public ContentValues extractContentValues(Signal signal) {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, signal.getId());
        values.put(KEY_CREATED_AT, signal.getCreatedAt().getTime());
        values.put(KEY_ASSET, signal.getAsset());
        values.put(KEY_ENTRY_RATE, signal.getEntryRate());
        values.put(KEY_EXPIRY_RATE, signal.getExpiryRate());
        values.put(KEY_IS_CALL, signal.isCall() ? 1 : 0);

        SignalStatus status = signal.getStatus();
        if(status != null) {
            values.put(KEY_STATUS, status.ordinal());
        }

        Date timeCreated = signal.getCreatedAt();
        if(timeCreated != null) {
            values.put(KEY_CREATED_AT, timeCreated.getTime());
        }

        Date expiryTime = signal.getExpiryTime();
        if(expiryTime != null) {
            values.put(KEY_EXPIRY_TIME, expiryTime.getTime());
        }

        return values;
    }

    @Override
    public Signal getObject(Cursor c) {
        Signal signal = new Signal();
        signal.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        signal.setCreatedAt(new Date(c.getLong(c.getColumnIndex(KEY_CREATED_AT))));
        signal.setExpiryTime(new Date(c.getLong(c.getColumnIndex(KEY_EXPIRY_TIME))));
        signal.setAsset(c.getString(c.getColumnIndex(KEY_ASSET)));
        signal.setEntryRate(c.getDouble(c.getColumnIndex(KEY_ENTRY_RATE)));
        signal.setExpiryRate(c.getDouble(c.getColumnIndex(KEY_EXPIRY_RATE)));
        signal.setIsCall(c.getInt(c.getColumnIndex(KEY_IS_CALL)) == 1 ? true : false);
        signal.setStatus(SignalStatus.values[c.getInt(c.getColumnIndex(KEY_STATUS))]);

        return signal;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getIdKey() {
        return null;
    }
}

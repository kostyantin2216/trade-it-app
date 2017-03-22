package com.tradeitsignals.datamodel.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.tradeitsignals.datamodel.BaseDAO;
import com.tradeitsignals.datamodel.data.BrokerRegistration;

import java.util.List;

/**
 * Created by Kostyantin on 10/21/2015.
 *
 * DO NOT USE, save registration with brokers on server only using BrokerRegistrationAPI.
 */
@Deprecated
public class BrokerRegistrationDAO extends BaseDAO<BrokerRegistration> {

    public final static String TABLE_NAME = "broker_registration";

    public final static String KEY_USER_ID = "user_id";
    public final static String KEY_BROKER_NAME = "broker_name";
    public final static String KEY_BROKER_USER_ID = "broker_user_id";
    public final static String KEY_MESSAGE = "message";
    public final static String KEY_IS_SUCCESS = "is_success";
    public final static String KEY_IP_ADDRESS = "ip_address";

    public final static String CMD_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
            + KEY_USER_ID + " TEXT, "
            + KEY_BROKER_NAME + " TEXT PRIMARY KEY, "
            + KEY_BROKER_USER_ID + " TEXT, "
            + KEY_MESSAGE + " TEXT, "
            + KEY_IS_SUCCESS + " INTEGER, "
            + KEY_IP_ADDRESS + " TEXT);";

    private final static BrokerRegistrationDAO instance = new BrokerRegistrationDAO();

    public static BrokerRegistrationDAO getInstance() {
        return instance;
    }

    private BrokerRegistrationDAO() { }

    public BrokerRegistration findByBrokerName(String brokerName) {
        List<BrokerRegistration> registrations = findByProperty(KEY_BROKER_NAME, brokerName);

        if(registrations.isEmpty()) {
            return null;
        }

        return registrations.get(0);
    }

    @Override
    public ContentValues extractContentValues(BrokerRegistration br) {
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, br.getUserBrokerId());
       // values.put(KEY_BROKER_NAME, br.getBrokerId());
        values.put(KEY_BROKER_USER_ID, br.getUserBrokerId());
        values.put(KEY_MESSAGE, br.getMessage());
        values.put(KEY_IS_SUCCESS, br.isSuccess() ? 1 : 0);
        values.put(KEY_IP_ADDRESS, br.getIpAddress());
        return values;
    }

    @Override
    public BrokerRegistration getObject(Cursor c) {
        return null;/* new BrokerRegistration(
                c.getString(c.getColumnIndex(KEY_USER_ID)),
                c.getString(c.getColumnIndex(KEY_BROKER_NAME)),
                c.getString(c.getColumnIndex(KEY_BROKER_USER_ID)),
                c.getString(c.getColumnIndex(KEY_MESSAGE)),
                c.getInt(c.getColumnIndex(KEY_IS_SUCCESS)) == 1,
                c.getString(c.getColumnIndex(KEY_IP_ADDRESS))
        ); */
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

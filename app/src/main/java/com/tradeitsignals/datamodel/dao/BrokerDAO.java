package com.tradeitsignals.datamodel.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.tradeitsignals.datamodel.BaseDAO;
import com.tradeitsignals.logging.TILogger;
import com.tradeitsignals.datamodel.data.Broker;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Kostyantin on 6/6/2015.
 */
public class BrokerDAO extends BaseDAO<Broker> {

    public static final String TABLE_NAME = "brokers";

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_DESC = "description";
    public static final String KEY_LOGO_URL = "logo_url";
    public static final String KEY_MIN_DEPOSIT = "min_deposit";
    public static final String KEY_MIN_WITHDRAWAL = "min_withdrawal";
    public static final String KEY_PROMO = "promotion";
    public static final String KEY_RATING = "rating";
    public static final String KEY_URL = "url";
    public static final String KEY_UPDATED_AT = "updated_at";
    public static final String KEY_IS_RECOMMENDED = "is_recommended";
    public static final String KEY_IS_ACTIVE = "is_active";

    public static final String CMD_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NAME + " TEXT, "
            + KEY_DESC + " TEXT, "
            + KEY_LOGO_URL + " TEXT, "
            + KEY_MIN_DEPOSIT + " INTEGER, "
            + KEY_MIN_WITHDRAWAL + " INTEGER, "
            + KEY_PROMO + " TEXT, "
            + KEY_RATING + " REAL, "
            + KEY_UPDATED_AT + " INTEGER, "
            + KEY_URL + " TEXT,"
            + KEY_IS_RECOMMENDED + " INTEGER,"
            + KEY_IS_ACTIVE + " INTEGER);";

    private BrokerDAO() { }

    private static class holder {
        private static BrokerDAO instance;
    }

    public static BrokerDAO getInstance() {
        if(holder.instance == null) {
            holder.instance = new BrokerDAO();
        }
        return holder.instance;
    }

    public Broker findRandomBroker() {
        List<Broker> brokers = findAll();

        Random rand = new Random(System.currentTimeMillis());

        return brokers.get(rand.nextInt(brokers.size()));
    }

    public List<Broker> getRecommendedBrokers() {
        List<Broker> brokers = findByProperty(KEY_IS_RECOMMENDED, String.valueOf(1));
        return brokers;
    }

    public Broker getRandomRecommendedBroker() {
        List<Broker> brokers = getRecommendedBrokers();

        Random rand = new Random(System.currentTimeMillis());
        if(brokers.isEmpty()) {
            TILogger.getLog().d(getLogTag(), "No recommended brokers... retrieving all brokers..");
            brokers = findAll();
        }
        if(brokers.isEmpty()) {
            return null;
        }
        return brokers.get(rand.nextInt(brokers.size()));
    }

    public List<Broker> getActiveBrokers() {
        return findByProperty(KEY_IS_ACTIVE, String.valueOf(1));
    }

    @Override
    public ContentValues extractContentValues(Broker broker) {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, broker.getId());
        values.put(KEY_NAME, broker.getName());
        values.put(KEY_DESC, broker.getDescription());
        values.put(KEY_LOGO_URL, broker.getLogoUrl());
        values.put(KEY_MIN_DEPOSIT, broker.getMinDeposit());
        values.put(KEY_MIN_WITHDRAWAL, broker.getMinWithdrawal());
        values.put(KEY_PROMO, broker.getPromotion());
        values.put(KEY_RATING, broker.getRating());
        values.put(KEY_UPDATED_AT, broker.getUpdatedAt().getTime());
        values.put(KEY_URL, broker.getUrl());
        values.put(KEY_IS_RECOMMENDED, broker.isRecommended() ? 1 : 0);
        values.put(KEY_IS_ACTIVE, broker.isActive() ? 1 : 0);

        return values;
    }

    @Override
    public Broker getObject(Cursor c) {
        Broker broker = new Broker();
        broker.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        broker.setName(c.getString(c.getColumnIndex(KEY_NAME)));
        broker.setDescription(c.getString(c.getColumnIndex(KEY_DESC)));
        broker.setLogoUrl(c.getString(c.getColumnIndex(KEY_LOGO_URL)));
        broker.setMinDeposit(c.getInt(c.getColumnIndex(KEY_MIN_DEPOSIT)));
        broker.setMinWithdrawal(c.getInt(c.getColumnIndex(KEY_MIN_WITHDRAWAL)));
        broker.setPromotion(c.getString(c.getColumnIndex(KEY_PROMO)));
        broker.setRating(c.getFloat(c.getColumnIndex(KEY_RATING)));
        broker.setUpdatedAt(new Date(c.getLong(c.getColumnIndex(KEY_UPDATED_AT))));
        broker.setUrl(c.getString(c.getColumnIndex(KEY_URL)));
        broker.setRecommended(c.getInt(c.getColumnIndex(KEY_IS_RECOMMENDED)) == 1 ? true : false);
        broker.setActive(c.getInt(c.getColumnIndex(KEY_IS_ACTIVE)) == 1);

        return broker;
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

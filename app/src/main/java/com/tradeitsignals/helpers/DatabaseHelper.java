package com.tradeitsignals.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tradeitsignals.SignalsApplication;
import com.tradeitsignals.datamodel.dao.BrokerDAO;
import com.tradeitsignals.datamodel.dao.BrokerRegistrationDAO;
import com.tradeitsignals.datamodel.dao.ChartDataDAO;
import com.tradeitsignals.datamodel.dao.ContentPageDAO;
import com.tradeitsignals.datamodel.dao.CountryDAO;
import com.tradeitsignals.datamodel.dao.CurrencyDAO;
import com.tradeitsignals.datamodel.dao.MarketReviewDAO;
import com.tradeitsignals.datamodel.dao.SignalDAO;
import com.tradeitsignals.datamodel.dao.UserDAO;
import com.tradeitsignals.datamodel.data.User;
import com.tradeitsignals.rest.api.APIFactory;
import com.tradeitsignals.rest.api.UserAPI;
import com.tradeitsignals.synchronization.SynchronizationTimestamps;
import com.tradeitsignals.utils.PrefUtils;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DatabaseHelper extends SQLiteOpenHelper {

    public final static String DB_NAME = "signals";

    private final static String DROP_TABLE = "DROP TABLE IF EXISTS ";

    private SQLiteDatabase mDatabase;

    private int dbOpenCounter = 0;

    private static class holder {
        static DatabaseHelper instance;
    }

    public static synchronized DatabaseHelper getInstance() {
        if (holder.instance == null) {
            holder.instance = new DatabaseHelper(SignalsApplication.getAppContext());
        }

        return holder.instance;
    }

    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, TIConfiguration.getDatabaseVersion());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SignalDAO.CMD_CREATE_TABLE);
        db.execSQL(UserDAO.CMD_CREATE_TABLE);
        db.execSQL(BrokerDAO.CMD_CREATE_TABLE);
        db.execSQL(MarketReviewDAO.CMD_CREATE_TABLE);
        db.execSQL(CurrencyDAO.CMD_CREATE_TABLE);
        db.execSQL(ContentPageDAO.CMD_CREATE_TABLE);
        db.execSQL(CountryDAO.CMD_CREATE_TABLE);
        db.execSQL(BrokerRegistrationDAO.CMD_CREATE_TABLE);
        db.execSQL(ChartDataDAO.CREATE_CHART_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE + SignalDAO.TABLE_NAME);
        db.execSQL(DROP_TABLE + UserDAO.TABLE_NAME);
        db.execSQL(DROP_TABLE + BrokerDAO.TABLE_NAME);
        db.execSQL(DROP_TABLE + MarketReviewDAO.TABLE_NAME);
        db.execSQL(DROP_TABLE + CurrencyDAO.TABLE_NAME);
        db.execSQL(DROP_TABLE + ContentPageDAO.TABLE_NAME);
        db.execSQL(DROP_TABLE + CountryDAO.TABLE_NAME);
        db.execSQL(DROP_TABLE + BrokerRegistrationDAO.TABLE_NAME);
        db.execSQL(DROP_TABLE + ChartDataDAO.TABLE_NAME);
        db.execSQL(DROP_TABLE + ChartDataDAO.TABLE_NAME);
        new SynchronizationTimestamps().reset();

        onCreate(db);
    }

    public synchronized SQLiteDatabase openDatabase() {
        dbOpenCounter++;
        if(dbOpenCounter == 1) {
            mDatabase = getWritableDatabase();
        }

        return mDatabase;
    }

    public synchronized void closeDatabase() {
        dbOpenCounter--;
        if(dbOpenCounter == 0) {
            mDatabase.close();
        }
    }

}

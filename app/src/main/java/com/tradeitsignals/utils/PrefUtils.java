package com.tradeitsignals.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.tradeitsignals.R;
import com.tradeitsignals.SignalsApplication;
import com.tradeitsignals.helpers.TIConfiguration;
import com.tradeitsignals.logging.TILogger;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kostyantin on 6/9/2015.
 */
public class PrefUtils {

    private final static String LOG_TAG = "#PrefUtils";

    private final static SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(SignalsApplication.getAppContext());

    /** Id created upon installation **/
    private final static String PREF_INSTALLATION_ID = "pref_installation_id";

    /** Boolean indicating wether the app has been open before */
    private final static String PREF_IS_FIRST_TIME_APP_OPENED = "pref_is_first_time_app_opened";

    /** Long contained a timestamp of the first time the app was open */
    private final static String PREF_FIRST_APP_OPEN_TIMESTAMP = "pef_first_app_open_timestamp";

    /** String representing users id in SQLite database. */
    private final static String PREF_USER_ID = "pref_user_id";

    /** Boolean indication whether parse has synchronized at least once and there is content in the app */
    private final static String PREF_CONTENT_EXISTS = "pref_content_exists";

    /** Boolean indicating whether user has been asked to register before trial has finished **/
    private final static String PREF_USER_ASKED_TO_REGISTER = "pref_user_asked_to_register";

    /** Boolean indicating if full synchronization is needed */
    private final static String PREF_SYNCHRONIZATION_NEEDED = "pref_synchronization_needed";

    /** String containing synchronization timestamps as key value pairs seperated by & **/
    private final static String PREF_SYNCHRONIZATION_TIMESTAMPS = "pref_synchronization_timestamps";

    /** String url for Trade It Server REST API **/
    private final static String PREF_BASE_API_URL = "pref_base_api_url";

    public static void init(Context context) {
        if(CommonUtils.isEmpty(mSharedPreferences.getString(PREF_INSTALLATION_ID, null))) {
            SharedPreferences.Editor edit = mSharedPreferences.edit();
            TILogger.getLog().i(LOG_TAG, "----APP HAS BEEN OPENED FOR THE FIRST TIME!!");
            edit.putString(PREF_INSTALLATION_ID, CommonUtils.createInstallationId(context));
            edit.putLong(PREF_FIRST_APP_OPEN_TIMESTAMP, System.currentTimeMillis());
            edit.apply();
        }
    }

    public static String getInstallationId() {
        return mSharedPreferences.getString(PREF_INSTALLATION_ID, "");
    }

    public static void setFirstTimeAppOpened() {
        mSharedPreferences.edit().putBoolean(PREF_IS_FIRST_TIME_APP_OPENED, false).commit();
    }

    public static boolean isFirstTimeAppOpened() {
        return mSharedPreferences.getBoolean(PREF_IS_FIRST_TIME_APP_OPENED, true);
    }

    public static long getFirstAppOpenTimestamp() {
        long firstAppOpenMillis =  mSharedPreferences.getLong(PREF_FIRST_APP_OPEN_TIMESTAMP, System.currentTimeMillis());
        TILogger.getLog().d(LOG_TAG, "Getting preference: " + PREF_FIRST_APP_OPEN_TIMESTAMP + " = " + firstAppOpenMillis);
        return firstAppOpenMillis;
    }

    public static void setUserId(int id) {
        mSharedPreferences.edit().putInt(PREF_USER_ID, id).commit();
    }

    /** Caution! returns -1 if no id has been saved. */
    public static int getUserId() {
        return mSharedPreferences.getInt(PREF_USER_ID, -1);
    }

    public static boolean isUserRegistered() {
        return getUserId() > 0;
    }

    public static void setContentExists() {
        mSharedPreferences.edit().putBoolean(PREF_CONTENT_EXISTS, true).commit();
    }

    public static boolean doesContentExist() {
        return mSharedPreferences.getBoolean(PREF_CONTENT_EXISTS, isUserRegistered());
    }

    public static void setUserAskedToRegister() {
        mSharedPreferences.edit().putBoolean(PREF_USER_ASKED_TO_REGISTER, true).commit();
    }

    public static boolean wasUserAskedToRegister() {
        return mSharedPreferences.getBoolean(PREF_USER_ASKED_TO_REGISTER, false);
    }

    public static boolean isSynchronizationNeeded() {
        boolean isResyncNeeded = mSharedPreferences.getBoolean(PREF_SYNCHRONIZATION_NEEDED, true);
        return isResyncNeeded;
    }

    public static void setSynchronizationNeeded(boolean isNeeded) {
        isSynchronizationNeeded();
        mSharedPreferences.edit().putBoolean(PREF_SYNCHRONIZATION_NEEDED, isNeeded).commit();
    }

    public static void setPrefSynchronizationTimestamps(String synchronizationTimestamps) {
        mSharedPreferences.edit().putString(PREF_SYNCHRONIZATION_TIMESTAMPS, synchronizationTimestamps).commit();
    }

    public static String getPrefSynchronizationTimestamps() {
        return mSharedPreferences.getString(PREF_SYNCHRONIZATION_TIMESTAMPS, "");
    }

    public static Map<String, Long> getPrefSynchronizationTimestampsAsMap() {
        String params = getPrefSynchronizationTimestamps();
        Map<String, Long> paramsMap = new HashMap<>();
        String[] splitParams = params.split("&");
        for(String param : splitParams) {
            String[] splitParam = param.split("=");
            if(splitParam.length == 2) {
                paramsMap.put(splitParam[0], Long.parseLong(splitParam[1]));
            }
        }
        return paramsMap;
    }

    public  static String getBaseApiUrl() {
        return mSharedPreferences.getString(PREF_BASE_API_URL, TIConfiguration.getString("api_base_url", "http://45.79.151.92/rest/"));
    }

    public static void setBaseApiUrl(String url) {
        mSharedPreferences.edit().putString(PREF_BASE_API_URL, url).apply();
    }

    //***************************************************************************************//
    //                                SETTINGS PREFERENCES                                   //
    //***************************************************************************************//

    public final static String PREF_SHOW_NOTIFICATIONS = "pref_show_notifications";

    public final static String PREF_SIGNAL_MORNING_RESTRICTION = "pref_signal_morning_restriction_millis";

    public final static String PREF_SIGNAL_EVENING_RESTRICTION = "pref_signal_evening_restriction_millis";

    public final static String PREF_SIGNAL_CURRENCY_PAIRS_FILTER = "pref_signal_currency_pairs_filter";

    public static boolean showNotifications() {
        return mSharedPreferences.getBoolean(PREF_SHOW_NOTIFICATIONS, true);
    }

    public static Set<String> getSignalCurrencyPairsFilter() {
        String[] defaultValuesArr = SignalsApplication.getAppContext().getResources().getStringArray(R.array.currency_pairs);
        Set<String> defaultValuesSet = new HashSet<>(Arrays.asList(defaultValuesArr));
        return mSharedPreferences.getStringSet(PREF_SIGNAL_CURRENCY_PAIRS_FILTER, defaultValuesSet);
    }

    public static Date getMorningSignalsRestriction() {
        long timeOfDay = mSharedPreferences.getLong(PREF_SIGNAL_MORNING_RESTRICTION, TimeUnit.HOURS.toMillis(6));
        return getTimeOfDay(timeOfDay);
    }

    public static Date getEveningSignalRestriction() {
        long timeOfDay = mSharedPreferences.getLong(PREF_SIGNAL_EVENING_RESTRICTION, TimeUnit.HOURS.toMillis(23));
        return getTimeOfDay(timeOfDay);
    }

    private static Date getTimeOfDay(long timeOfDayMillis) {
        Calendar cal = Calendar.getInstance();
        DateUtils.resetTimeOfDay(cal);
        cal.add(Calendar.MILLISECOND, (int) timeOfDayMillis);

        return cal.getTime();
    }
}

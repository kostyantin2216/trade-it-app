package com.tradeitsignals.helpers;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;

import com.tradeitsignals.SignalsApplication;
import com.tradeitsignals.logging.TILogger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Kostyantin on 4/17/2016.
 */
public class TIConfiguration {

    private final static String LOG_TAG = "#CONFIGURATIONS";

    private final static ConcurrentMap<String, Object> configurations = new ConcurrentHashMap<>();

    public final static String DB_VERSION = "database_version";
    public final static String IS_DEVELOPMENT = "is_development";
    public final static String DEVICE_INFO = "device_info";
    public final static String APP_INFO ="app_info";

    private TIConfiguration() { }

    public static Integer getInt(String code, Integer defaultVal) {
        Object config = configurations.get(code);
        if(config == null) {
            Context context = SignalsApplication.getAppContext();
            Resources resources = context.getResources();
            int resId = resources.getIdentifier(code, "integer", context.getPackageName());
            if(resId > 0) {
                Integer val = resources.getInteger(resId);
                configurations.putIfAbsent(code, val);
                return val;
            }
        } else {
            return (Integer) config;
        }
        return defaultVal;
    }

    public static Boolean getBoolean(String code, Boolean defaultVal) {
        Object config = configurations.get(code);
        if(config == null) {
            Context context = SignalsApplication.getAppContext();
            Resources resources = context.getResources();
            int resId = resources.getIdentifier(code, "bool", context.getPackageName());
            if(resId > 0) {
                Boolean val = resources.getBoolean(resId);
                configurations.putIfAbsent(code, val);
                return val;
            }
        } else {
            return (Boolean) config;
        }
        return defaultVal;
    }

    public static String getString(String code, String defaultVal) {
        Object config = configurations.get(code);
        if(config == null) {
            Context context = SignalsApplication.getAppContext();
            Resources resources = context.getResources();
            int resId = resources.getIdentifier(code, "string", context.getPackageName());
            if(resId > 0) {
                String val = resources.getString(resId);
                configurations.putIfAbsent(code, val);
                return val;
            }
        } else {
            return (String) config;
        }
        return defaultVal;
    }

    public static void setConfiguration(String key, Object value) {
        configurations.putIfAbsent(key, value);
    }

    public static String getApplicationInfo() {
        String applicationInformation = (String) configurations.get(APP_INFO);
        if(applicationInformation == null) {

            PackageInfo pInfo = null;
            Context context = SignalsApplication.getAppContext();
            try {
                pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                TILogger.getLog().e("Could get version name from package manager");
            }

            String versionName;
            int versionCode;
            if(pInfo != null) {
                versionName = pInfo.versionName;
                versionCode = pInfo.versionCode;
            } else {
                versionName = "";
                versionCode = 0;
            }

            applicationInformation = "Version Code: " + versionCode
                                 + "\nVersion Name: " + versionName;

            configurations.putIfAbsent(APP_INFO, applicationInformation);
        }
        return applicationInformation;
    }

    public static String getDeviceInfo() {
        String deviceInformation = (String) configurations.get(DEVICE_INFO);
        if(deviceInformation == null) {
            deviceInformation = "Product: " + Build.PRODUCT
                           + ",\nBrand: " + Build.BRAND
                           + ",\nDevice: " + Build.DEVICE
                           + ",\nManufacturer: " + Build.MANUFACTURER
                           + ",\nModel: " + Build.MODEL
                           + ",\nSDK: " + Build.VERSION.SDK_INT;

            configurations.putIfAbsent(DEVICE_INFO, deviceInformation);
        }
        return deviceInformation;
    }

    public static boolean isDevelopment() {
        return getBoolean(IS_DEVELOPMENT, false);
    }

    public static int getDatabaseVersion() {
        return getInt(DB_VERSION, -1);
    }

}

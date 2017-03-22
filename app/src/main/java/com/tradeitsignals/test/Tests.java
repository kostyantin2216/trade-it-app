package com.tradeitsignals.test;

import android.util.Log;

import com.tradeitsignals.datamodel.data.Signal;
import com.tradeitsignals.logging.TILogger;
import com.tradeitsignals.rest.api.APIFactory;
import com.tradeitsignals.rest.services.SignalService;
import com.tradeitsignals.utils.CommonUtils;
import com.tradeitsignals.utils.CountryUtils;
import com.tradeitsignals.utils.PrefUtils;
import com.tradeitsignals.helpers.TIConfiguration;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Kostyantin on 10/23/2015.
 */
public class Tests {

    public static void testAndroidBuildInformation() {
        TILogger log = TILogger.getLog();
        log.e("BUILD INFO", " ----------- DEVICE INFORMATION ------------ ");
        log.e("BUILD INFO", TIConfiguration.getDeviceInfo());
        log.e("BUILD INFO", " ----------- DEVICE INFORMATION ------------ ");
    }

    public static void testApplicationInformation() {
        TILogger log = TILogger.getLog();
        log.e("BUILD INFO", " ----------- APP INFORMATION ------------ ");
        log.e("BUILD INFO", TIConfiguration.getApplicationInfo());
        log.e("BUILD INFO", " ----------- APP INFORMATION ------------ ");
    }

    public static void testSettingPreferences() {
        TILogger log = TILogger.getLog();
        log.i("PREF INFO", " ---------------- PREFERENCE SETTINGS INFORMATION ---------------- ");
        log.i("PREF INFO", "Show notifications: " + PrefUtils.showNotifications());
        log.i("PREF INFO", "Signal morning restriction: " + PrefUtils.getMorningSignalsRestriction());
        log.i("PREF INFO", "Signal evening restriction: " + PrefUtils.getEveningSignalRestriction());
        log.i("PREF INFO", "Signal currency pairs filter: " + PrefUtils.getSignalCurrencyPairsFilter().size());
        log.i("PREF INFO", " ---------------- PREFERENCE SETTINGS INFORMATION ---------------- ");
    }

    public static void testIpAddress() {
        TILogger log = TILogger.getLog();
        log.i("IP ADDRESS", " -------------------- IP ADDRESS -------------------- ");
        log.i("IP ADDRESS", "Internal: " + CommonUtils.getIPAddress());
        log.i("IP ADDRESS", " -------------------- IP ADDRESS -------------------- ");

    }

    public static void testCountryUtils() {
        TILogger log = TILogger.getLog();
        log.i("IP ADDRESS", " -------------------- IP ADDRESS -------------------- ");
        log.i("IP ADDRESS", CountryUtils.getCountryFromUser().toString());
        log.i("IP ADDRESS", " -------------------- IP ADDRESS -------------------- ");

    }

    public static void testServices() {
        APIFactory serviceFactory = APIFactory.getInstance();
        final TILogger log = TILogger.getLog();
        log.i("SERVICES", " ----------------------- SERVICES ---------------------- ");
        serviceFactory.getSignalService().findAll().enqueue(new Callback<List<Signal>>() {

            @Override
            public void onResponse(Call<List<Signal>> call, Response<List<Signal>> response) {
                log.i("SERVICES", "(explicit)Signals from service: " + response.body());
            }

            @Override
            public void onFailure(Call<List<Signal>> call, Throwable t) {
                log.i("SERVICES", "(explicit)Failed to retrieve signals from service, stack trace: " + Log.getStackTraceString(t));
            }
        });

        SignalService service = serviceFactory.getService(APIFactory.ServiceTypes.SIGNAL_SERVICE);
        service.findAll().enqueue(new Callback<List<Signal>>() {

            @Override
            public void onResponse(Call<List<Signal>> call, Response<List<Signal>> response) {
                log.i("SERVICES", "(implicit)Signals from service: " + response.body());
            }

            @Override
            public void onFailure(Call<List<Signal>> call, Throwable t) {
                log.i("SERVICES", "(implicit)Failed to retrieve signals from service, stack trace: " + Log.getStackTraceString(t));
            }
        });

    }

}

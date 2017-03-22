package com.tradeitsignals.external.brokers;

import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.tradeitsignals.SignalsApplication;
import com.tradeitsignals.datamodel.dao.UserDAO;
import com.tradeitsignals.datamodel.data.User;
import com.tradeitsignals.logging.TILogger;
import com.tradeitsignals.rest.RetrofitConfig;
import com.tradeitsignals.rest.requests.UBinaryLoginRequest;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Kostyantin on 4/13/2016.
 */
public class UBinary implements ExternalBroker {

    private final static String PREF_REGISTRATION_ID = "pref_registration_id";

    public final static String BASE_URL = "api.ubinary.com";

    @Override
    public void saveRegistrationId(String id) {
        PreferenceManager.getDefaultSharedPreferences(SignalsApplication.getAppContext())
                         .edit()
                         .putString(PREF_REGISTRATION_ID, PREF_REGISTRATION_ID)
                         .commit();
    }

    @Override
    public String getRegistrationId() {
        return PreferenceManager.getDefaultSharedPreferences(SignalsApplication.getAppContext())
                                .getString(PREF_REGISTRATION_ID, "");
    }

    @Override
    public void login() {
        OkHttpClient httpClient = RetrofitConfig.getInstance().getHttpClient();

        User user = UserDAO.getInstance().getUser();

        UBinaryLoginRequest loginRequest = new UBinaryLoginRequest(user);
        loginRequest.setRedirectTo("http://www.ubinary.com");

        String jsonRequest = new Gson().toJson(loginRequest);

        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host(BASE_URL)
                .addPathSegment("trading")
                .addPathSegment("user")
                .addPathSegment("login")
                .addQueryParameter("data", jsonRequest)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                TILogger.getLog().e("UBinary login failed", e, false, true);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                TILogger.getLog().i("UBinary login response: code=" + response.code() + ", msg=" + response.message(), false, true);
            }
        });
    }

}

package com.tradeitsignals.rest.interceptors;

import android.content.Context;

import com.tradeitsignals.R;
import com.tradeitsignals.SignalsApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Kostyantin on 1/8/2016.
 */
public class AuthorizationInterceptor implements Interceptor {

    private static final String authorizationKey = getAuthorizationKey();

    private static String getAuthorizationKey() {
        Context context = SignalsApplication.getAppContext();
        String formattedKey = context.getString(R.string.random_code);
        int[] formatValues = context.getResources().getIntArray(R.array.code_numbers);
        int val1 = formatValues[0];
        int val2 = formatValues[1];
        int val3 = formatValues[2];
        int val4 = formatValues[3];
        int val5 = formatValues[4];
        int val6 = formatValues[5];
        int val7 = formatValues[6];
        int val8 = formatValues[7];
        return String.format(
                formattedKey, val1, val2, val3, val4, val5, val6, val7, val8).trim();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder()
                .header("Authorization", authorizationKey)
                .header("Accept", "application/json")
                .method(original.method(), original.body());

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}

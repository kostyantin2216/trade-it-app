package com.tradeitsignals.helpers;

import android.content.Context;
import android.content.res.Resources;

import com.tradeitsignals.SignalsApplication;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Kostyantin on 5/31/2016.
 */
public class TIDictionary {

    private final static ConcurrentMap<String, String> dictionary = new ConcurrentHashMap<>();

    public static String translate(String code) {
        String translation = dictionary.get(code);
        if(translation == null) {
            Context context = SignalsApplication.getAppContext();
            Resources resources = context.getResources();
            int resId = resources.getIdentifier(code, "string", context.getPackageName());
            if(resId > 0) {
                translation = resources.getString(resId);
                dictionary.putIfAbsent(code, translation);
            }
        }
        return translation;
    }
}

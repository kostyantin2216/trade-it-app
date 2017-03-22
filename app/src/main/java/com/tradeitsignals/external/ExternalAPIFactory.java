package com.tradeitsignals.external;

import com.tradeitsignals.logging.TILogger;

/**
 * Created by Kostyantin on 10/10/2015.
 */
public class ExternalAPIFactory {

    public final static int AD_MOB = 1;

    private final static String LOG_TAG = "#APIFactory";

    public static API get(int apiCode) {
        switch (apiCode) {
            case AD_MOB:
                return AdMobAPI.getInstance();
            default:
                return null;
        }
    }

}

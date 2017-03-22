package com.tradeitsignals.external;

import android.app.Application;
import android.content.Context;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.tradeitsignals.R;
import com.tradeitsignals.logging.TILogger;
import com.tradeitsignals.utils.Constants;

import java.util.Map;

/**
 * Created by Kostyantin on 9/5/2016.
 */
public class AppsFlyerManager {

    public static void init(Application application) {
        AppsFlyerLib afl = AppsFlyerLib.getInstance();
        afl.startTracking(application, application.getString(R.string.apps_flyer_key));
    }

    public static void setUserId(String userId) {
        AppsFlyerLib afl = AppsFlyerLib.getInstance();
        afl.setCustomerUserId(userId);
    }

    public static void loadClickId(Context context, final ClickIdCallback callback) {
        AppsFlyerLib.getInstance().registerConversionListener(context, new AppsFlyerConversionListener() {

            private boolean isLoaded = false;

            @Override
            public void onInstallConversionDataLoaded(Map<String, String> conversionData) {
                TILogger.getLog().i(AppsFlyerLib.LOG_TAG, "onInstallConversionDataLoaded", true);
                for (String attrName : conversionData.keySet()) {
                    TILogger.getLog().i(AppsFlyerLib.LOG_TAG, "attribute: " + attrName + " = " +
                            conversionData.get(attrName), true);
                }

                if(!isLoaded) {
                    String clickId = conversionData.get(Constants.ARG_CLICK_ID);
                    callback.onClickIdLoaded(clickId);
                    isLoaded = true;
                }
            }

            @Override
            public void onInstallConversionFailure(String errorMessage) {
                TILogger.getLog().e(AppsFlyerLib.LOG_TAG, "error getting conversion data: " +
                        errorMessage, true);

                callback.onError(errorMessage);
            }

            @Override
            public void onAppOpenAttribution(Map<String, String> conversionData) {
                TILogger.getLog().i(AppsFlyerLib.LOG_TAG, "onInstallConversionDataLoaded", true);
                for (String attrName : conversionData.keySet()) {
                    TILogger.getLog().i(AppsFlyerLib.LOG_TAG, "attribute: " + attrName + " = " +
                            conversionData.get(attrName), true);
                }

                if(!isLoaded) {
                    String clickId = conversionData.get(Constants.ARG_CLICK_ID);
                    callback.onClickIdLoaded(clickId);
                    isLoaded = true;
                }
            }

            @Override
            public void onAttributionFailure(String errorMessage) {
                TILogger.getLog().e(AppsFlyerLib.LOG_TAG, "error onAttributionFailure : " + errorMessage, true);

                callback.onError(errorMessage);
            }
        });
    }

    public interface ClickIdCallback {
        void onClickIdLoaded(String clickId);
        void onError(String error);
    }
}

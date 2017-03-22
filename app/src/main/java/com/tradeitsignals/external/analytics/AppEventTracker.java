package com.tradeitsignals.external.analytics;

import android.content.Context;
import android.os.Bundle;

import com.appsflyer.AppsFlyerLib;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.facebook.appevents.AppEventsLogger;
import com.tradeitsignals.SignalsApplication;
import com.tradeitsignals.datamodel.ImmutableKeyValue;
import com.tradeitsignals.datamodel.dao.UserDAO;
import com.tradeitsignals.datamodel.data.User;
import com.tradeitsignals.helpers.TIConfiguration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kostyantin on 9/10/2016.
 */
public class AppEventTracker {

    public final static String EVENT_VERIFY_NUMBER = "verify_number";
    public final static String EVENT_VERIFY_NUMBER_SUCCESS = "verify_number_success";
    public final static String EVENT_VERIFY_NUMBER_ERROR = "verify_number_error";
    public final static String EVENT_REGISTRATION_BEGIN = "registration_begin";
    public final static String EVENT_REGISTRATION_COMPLETE = "registration_complete";
    public final static String EVENT_PUSH_RECEIVE = "push_receive";
    public final static String EVENT_NOTIFICATION_SHOWN = "notification_shown";
    public final static String EVENT_NOTIFICATION_OPENED = "notification_open";
    public final static String EVENT_TERMS_COND_SHOW = "terms_cond_show";
    public final static String EVENT_TERMS_COND_ACCEPT = "terms_cond_accept";
    public final static String EVENT_TERMS_COND_DECLINE = "terms_cond_decline";
    public final static String EVENT_EXTERNAL_LOGIN = "external_login";
    public final static String EVENT_PERMISSION_REQUEST = "permission_request";
    public final static String EVENT_PERMISSION_GRANTED = "permission_granted";
    public final static String EVENT_PERMISSION_DENIED = "permission_denied";


    public final static String LABEL_SOURCE = "source";
    public final static String LABEL_REASON = "reason";
    public final static String LABEL_NUMBER = "number";
    public final static String LABEL_ASSET = "asset";
    public final static String LABEL_SECONDS = "seconds";
    public final static String LABEL_PERMISSION = "permissions";

    private final AppEventsLogger facebookLogger;

    public AppEventTracker(Context context) {
        facebookLogger = AppEventsLogger.newLogger(context);
    }

    public void trackEvent(final AppEvent appEvent) {
        if(shouldTrack()) {
            if (appEvent.sendToAnswers) {
                CustomEvent event = new CustomEvent(appEvent.event);
                if (appEvent.labels != null) {
                    for(ImmutableKeyValue<String, String> label : appEvent.labels) {
                        event.putCustomAttribute(label.key, label.value);
                    }
                }
                Answers.getInstance().logCustom(event);
            }
            if (appEvent.sendToAppsFlyer) {
                Map<String, Object> labelMap = new HashMap<>();
                if(appEvent.labels != null) {
                    for(ImmutableKeyValue<String, String> label : appEvent.labels) {
                        labelMap.put(label.key, label.value);
                    }
                }
                AppsFlyerLib.getInstance().trackEvent(SignalsApplication.getAppContext(), appEvent.event, labelMap);
            }
            if (appEvent.sendToFacebook) {
                Bundle eventArgs = new Bundle();
                if(appEvent.labels != null) {
                    for(ImmutableKeyValue<String, String> label : appEvent.labels) {
                        eventArgs.putString(label.key, label.value);
                    }
                }
                facebookLogger.logEvent(appEvent.event, eventArgs);
            }
        }
    }

    private boolean shouldTrack() {
        boolean isAdmin = false;
        User user = UserDAO.getInstance().getUser();
        if(user != null) {
            isAdmin = user.isAdmin();
        }
        return !TIConfiguration.isDevelopment() && !isAdmin;
    }

    public void trackRegistrationBegin() {
        trackEvent(new AppEvent.Builder(EVENT_REGISTRATION_BEGIN)
                               .sendToAll()
                               .build()
        );
    }

    public void trackRegistrationComplete(String registrationSource) {
        trackEvent(new AppEvent.Builder(EVENT_REGISTRATION_COMPLETE)
                               .sendToAll()
                               .label(LABEL_SOURCE, registrationSource)
                               .build()
        );
    }

    public void trackPushReceive(String asset) {
        trackEvent(new AppEvent.Builder(EVENT_PUSH_RECEIVE)
                               .sendToAll()
                               .label(LABEL_ASSET, asset)
                               .build()
        );
    }

    public void trackNotificationShown(String asset) {
        trackEvent(new AppEvent.Builder(EVENT_NOTIFICATION_OPENED)
                               .sendToAll()
                               .label(LABEL_ASSET, asset)
                               .build()
        );
    }

    public void trackNotificationOpen(String asset) {
        trackEvent(new AppEvent.Builder(EVENT_NOTIFICATION_SHOWN)
                               .sendToAll()
                               .label(LABEL_ASSET, asset)
                               .build()
        );
    }

    public void trackExternalLogin(String source) {
        trackEvent(new AppEvent.Builder(EVENT_EXTERNAL_LOGIN)
                               .sendToAll()
                               .label(LABEL_SOURCE, source)
                               .build()
        );
    }

    public void trackTermsConditionsShow() {
        trackEvent(new AppEvent.Builder(EVENT_TERMS_COND_SHOW)
                               .build()
        );
    }

    public void trackPermissionRequest(String... permissions) {
        trackEvent(new AppEvent.Builder(EVENT_PERMISSION_REQUEST)
                               .label(LABEL_PERMISSION, Arrays.toString(permissions))
                               .build()
        );
    }

    public void trackPermissionGranted(String... permissions) {
        trackEvent(new AppEvent.Builder(EVENT_PERMISSION_GRANTED)
                .label(LABEL_PERMISSION, Arrays.toString(permissions))
                .build()
        );
    }

    public void trackPermissionDenied(String... permissions) {
        trackEvent(new AppEvent.Builder(EVENT_PERMISSION_DENIED)
                .label(LABEL_PERMISSION, Arrays.toString(permissions))
                .build()
        );
    }


}

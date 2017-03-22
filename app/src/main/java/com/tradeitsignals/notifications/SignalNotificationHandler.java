package com.tradeitsignals.notifications;

import android.content.Context;
import android.content.Intent;

import com.tradeitsignals.ui.activities.LauncherActivity;
import com.tradeitsignals.ui.activities.SplashScreen;

/**
 * Created by Kostyantin on 9/11/2016.
 */
public class SignalNotificationHandler {

    public Intent createIntent(Context context, String asset) {
        return new Intent(context, LauncherActivity.class);
    }
}

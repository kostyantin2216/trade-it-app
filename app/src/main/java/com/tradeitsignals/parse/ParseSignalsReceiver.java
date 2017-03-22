package com.tradeitsignals.parse;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.parse.ParsePushBroadcastReceiver;
import com.tradeitsignals.R;
import com.tradeitsignals.external.analytics.AppEventTracker;
import com.tradeitsignals.logging.LogAccess;
import com.tradeitsignals.logging.TILogger;
import com.tradeitsignals.ui.activities.LauncherActivity;
import com.tradeitsignals.ui.activities.SplashScreen;
import com.tradeitsignals.utils.PrefUtils;
import com.tradeitsignals.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Kostyantin on 8/21/2015.
 */
public class ParseSignalsReceiver extends ParsePushBroadcastReceiver {

    public final static String LOG_TAG = "#ParseSignalsReceiver";

    public final static int PARSE_INIT_RETRIES = 5;
    public final static int PARSE_INIT_WAIT_TIME_MILLIS = 500;


    @Override
    protected void onPushReceive(Context context, Intent intent) {
        String channel = intent.getExtras().getString("com.parse.Channel");

        try {
            JSONObject pushData = new JSONObject(intent.getStringExtra("com.parse.Data"));
            String signalId = pushData.getString("signalId");
            String currencyPair = pushData.getString("currencyPair");
            String text = pushData.getString("alert");

            AppEventTracker tracker = new AppEventTracker(context);
            tracker.trackPushReceive(currencyPair);

            if(NotificationManagerCompat.from(context).areNotificationsEnabled()
                    && PrefUtils.showNotifications()
                    && PrefUtils.getSignalCurrencyPairsFilter().contains(currencyPair)) {
                Date now = new Date();
                Date morningRestriction = PrefUtils.getMorningSignalsRestriction();
                Date eveningRestriction = PrefUtils.getEveningSignalRestriction();
                if (now.after(morningRestriction) && now.before(eveningRestriction)) {
                    if(!StringUtils.isEmpty(signalId)) {
                        TILogger.getLog().i(LOG_TAG, "got push on channel " + channel
                                + " with data:" + pushData.toString());

                        String title = "Trade It";
                        if(!PrefUtils.isUserRegistered()) {
                            text = context.getString(R.string.signal_for_non_registered_user);
                        }
                        tracker.trackNotificationShown(currencyPair);
                        generateNotification(context, title, text, signalId);
                    } else {
                        super.onPushReceive(context, intent);
                    }
                }
            }
        } catch (JSONException e) {
            TILogger.getLog().e("com.parse.ParsePushReceiver", "Unexpected JSONException when receiving push data: ", e, false, true);
        }
    }

    private void generateNotification(Context context, String title, String text, String signalId) {
        Intent intent = new Intent(context, LauncherActivity.class);
        intent.putExtra("signalId", signalId);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager mNotifM = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(text);

        mBuilder.setContentIntent(contentIntent);
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setSound(uri);

        Notification notification = mBuilder.build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        mNotifM.notify(0, notification);

    }

    @Override
    protected void onPushOpen(Context context, Intent intent) {
        super.onPushOpen(context, intent);

        AppEventTracker tracker = new AppEventTracker(context);
        try {
            JSONObject pushData = new JSONObject(intent.getStringExtra("com.parse.Data"));
            String currencyPair = pushData.getString("currencyPair");

            tracker.trackNotificationOpen(currencyPair);
        } catch (JSONException e) {
            tracker.trackNotificationOpen("???");
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ParseHelper pHelper = ParseHelper.getInstance();
        if(pHelper.isParseInitialized()) {
            super.onReceive(context, intent);
        } else {
            pHelper.init();
            int retries = 0;
            while(!pHelper.isParseInitialized() && !pHelper.isInitializationError() && retries < PARSE_INIT_RETRIES) {
                try {
                    wait(PARSE_INIT_WAIT_TIME_MILLIS);
                } catch (InterruptedException e) {
                    TILogger.getLog().e("Failed to wait for parse to initialization in ParseSignalsReceiver.onReceive() after " + retries + " retries", e, true, LogAccess.SIGNALS_LOG_FILE_NAME);
                }
                retries++;
            }

            if(pHelper.isParseInitialized()) {
                super.onReceive(context, intent);
            } else if(pHelper.isInitializationError()) {
                TILogger.getLog().e("Error while trying to initialize Parse in ParseSignalsReceiver.onReceive()", true, LogAccess.SIGNALS_LOG_FILE_NAME);
            }
        }
    }
}

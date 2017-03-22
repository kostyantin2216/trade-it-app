package com.tradeitsignals.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.tradeitsignals.R;
import com.tradeitsignals.external.ExternalAPIFactory;
import com.tradeitsignals.external.AdMobAPI;
import com.tradeitsignals.logging.TILogger;
import com.tradeitsignals.synchronization.SynchronizableTables;
import com.tradeitsignals.ui.components.AnimatedTradeItLogo;
import com.tradeitsignals.utils.CommonUtils;
import com.tradeitsignals.utils.PrefUtils;
import com.tradeitsignals.utils.UIUtils;

public class SplashScreen extends SynchronizationActivity {

    private final static String LOG_TAG = "#SplashScreen";
    public final static int APP_CLOSE_WAIT_TIME_MILLIS = 1000 * 10;

    private AnimatedTradeItLogo animatedLogo;

    private Handler mHandler = new Handler();

    private boolean synchronizationFinished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        initViews();
    }

    @Override
    public SynchronizableTables[] getTablesToSynchronize() {
        if (!PrefUtils.isFirstTimeAppOpened()) {
            return new SynchronizableTables[]{SynchronizableTables.COUNTRY, SynchronizableTables.USER};
        } // else sync countries in SignalsActivity during registration.
        return new SynchronizableTables[]{SynchronizableTables.COUNTRY};
    }

    @Override
    public void onSynchronizationStart() {
        // Here to stop loader fragment from showing.
    }

    @Override
    public void onSynchronizationComplete() {
        synchronizationFinished = true;
    }

    private void initViews() {
        TextView tvVersionName = (TextView) findViewById(R.id.tv_version_name);

        tvVersionName.setText(CommonUtils.getVersionName());

        this.animatedLogo = (AnimatedTradeItLogo) findViewById(R.id.animated_trade_it_logo);
        this.animatedLogo.setAnimationCallback(new AnimatedTradeItLogo.TradeItLogoAnimationCallback() {
            @Override
            public void onWelcomeAnimationFinished() { }

            @Override
            public void onFinishAnimationFinished() {
                runApplication();
            }
        });
    }

    public void startApplication() {
        final boolean hasInternetConn = CommonUtils.isNetworkConnected();
        final boolean doesContentExist = PrefUtils.doesContentExist();
        TILogger.getLog().d(LOG_TAG, "-- STARTING APPLICATION! network is " + (hasInternetConn ? "" : "not") + " connected");
        if (hasInternetConn || doesContentExist) {
            animatedLogo.startWelcomeAnimation();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    long millisToWait = 200;

                    if (animatedLogo.isWelcomeAnimationFinished() &&
                            (hasInternetConn ? synchronizationFinished : doesContentExist)
                            ) {
                        animatedLogo.startFinishAnimation();
                    } else {
                        mHandler.postDelayed(this, millisToWait);
                    }
                }
            });
        } else {
            CommonUtils.getConnectToNetworkDialog(this, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    UIUtils.showError(SplashScreen.this, getString(R.string.cannot_start_without_internet));
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            SplashScreen.this.finish();
                        }
                    }, APP_CLOSE_WAIT_TIME_MILLIS);
                }
            }).show();
        }
    }

    private void runApplication() {
        Intent intent = new Intent(SplashScreen.this, SignalsActivity.class);
        startActivity(intent);
        finish();
    }

}

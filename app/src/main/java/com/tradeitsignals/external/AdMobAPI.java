package com.tradeitsignals.external;

import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.tradeitsignals.R;
import com.tradeitsignals.SignalsApplication;
import com.tradeitsignals.logging.TILogger;
import com.tradeitsignals.helpers.TIConfiguration;

/**
 * Created by Kostyantin on 10/11/2015.
 *
 *  Singleton
 *
 */
public class AdMobAPI implements API {

    private static AdMobAPI instance;

    protected static AdMobAPI getInstance() {
        if(instance == null) {
            instance = new AdMobAPI(SignalsApplication.getAppContext());
        }

        return instance;
    }

    private String testBanner;
    private String testIntersitial;
    private String interstitialId;
    private String banner1Id;
    private String banner2Id;
    private String banner3Id;
    private String banner4Id;

    private boolean isProjectDevelopment;

    private AdMobAPI(Context context) {
        this.isProjectDevelopment = !TIConfiguration.isDevelopment();
        if(isProjectDevelopment) {
            this.interstitialId = context.getString(R.string.intersitial);
            this.banner1Id = context.getString(R.string.banner1);
            this.banner2Id = context.getString(R.string.banner2);
            this.banner3Id = context.getString(R.string.banner3);
            this.banner4Id = context.getString(R.string.banner4);
        } else {
            this.testBanner = context.getString(R.string.test_banner);
            this.testIntersitial = context.getString(R.string.test_intersitial);
        }
    }

    public void loadAd(AdView adView, int which) {
        if(isProjectDevelopment) {
            switch (which) {
                case 0:
                    adView.setAdUnitId(banner1Id);
                    break;
                case 1:
                    adView.setAdUnitId(banner2Id);
                    break;
                case 2:
                    adView.setAdUnitId(banner3Id);
                    break;
                case 3:
                    adView.setAdUnitId(banner4Id);
                    break;
                default:
                    TILogger.getLog().e("#AdMob", "Wrong add number requested no add exists at position: " + which);
                    adView.setAdUnitId(banner1Id);
                    break;
            }
        } else {
            adView.setAdUnitId(testBanner);
        }
        adView.setAdSize(AdSize.MEDIUM_RECTANGLE);

        adView.loadAd(getAdRequest());
    }

    public void loadAd(InterstitialAd interstitialAd) {
        if(isProjectDevelopment) {
            interstitialAd.setAdUnitId(interstitialId);
        } else {
            interstitialAd.setAdUnitId(testIntersitial);
        }

        interstitialAd.loadAd(getAdRequest());
    }

    private AdRequest getAdRequest() {
        AdRequest.Builder builder = new AdRequest.Builder();
        if(!isProjectDevelopment) {
            builder.addTestDevice("3FFD6D48096698E24FCC387F91483CAB");
        }

        return builder.build();
    }
}

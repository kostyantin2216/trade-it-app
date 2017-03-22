package com.tradeitsignals;

import android.app.Application;
import android.content.Context;

import com.appsflyer.AppsFlyerLib;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.tradeitsignals.datamodel.dao.UserDAO;
import com.tradeitsignals.datamodel.data.User;
import com.tradeitsignals.external.AppsFlyerManager;
import com.tradeitsignals.external.brokers.UBinary;
import com.tradeitsignals.logging.TILogger;
import com.tradeitsignals.parse.ParseHelper;
import com.tradeitsignals.rest.api.APIFactory;
import com.tradeitsignals.utils.CommonUtils;
import com.tradeitsignals.utils.PrefUtils;
import com.tradeitsignals.helpers.TIConfiguration;

import java.util.UUID;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignalsApplication extends Application {

	private static SignalsApplication instance;

	@Override
	public void onCreate() {
		super.onCreate();
		Fabric.with(this, new Crashlytics(), new Answers());

		instance = this;

		ParseHelper.getInstance().init();
		AppsFlyerManager.init(this);

		/// Facebook
		FacebookSdk.sdkInitialize(getApplicationContext());
		AppEventsLogger.activateApp(this);
	}

	public static Context getAppContext() {
		return instance.getBaseContext();
	}

}

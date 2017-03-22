package com.tradeitsignals.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.crashlytics.android.Crashlytics;
import com.tradeitsignals.R;
import com.tradeitsignals.backoffice.ui.activities.BackOfficeActivity;
import com.tradeitsignals.datamodel.dao.UserDAO;
import com.tradeitsignals.datamodel.data.User;
import com.tradeitsignals.external.brokers.UBinary;
import com.tradeitsignals.helpers.TIConfiguration;
import com.tradeitsignals.rest.api.APIFactory;
import com.tradeitsignals.utils.Constants;
import com.tradeitsignals.utils.PrefUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Kostyantin on 5/25/2016.
 */
public class LauncherActivity extends SplashScreen {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);

        init();
        if(PrefUtils.isUserRegistered()) {
            startApplication();
        } else {
            if(TIConfiguration.isDevelopment()) {
                new MaterialDialog.Builder(this)
                        .content(R.string.developer_registration_dialog_content)
                        .positiveText(R.string.register)
                        .neutralText(R.string.action_settings)
                        .negativeText(R.string.test_user)
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                switch (which) {
                                    case POSITIVE:
                                        startRegistration();
                                        break;
                                    case NEUTRAL:
                                        Intent intent = new Intent(LauncherActivity.this, BackOfficeActivity.class);
                                        startActivity(intent);
                                        break;
                                    case NEGATIVE:
                                        APIFactory.getInstance().getUserAPI().find(Constants.TEST_USER_ID).enqueue(new Callback<User>() {
                                            @Override
                                            public void onResponse(Call<User> call, Response<User> response) {
                                                User user = response.body();
                                                if(user != null) {
                                                    Integer userId = user.getId();
                                                    if(userId != null) {
                                                        PrefUtils.setUserId(userId);
                                                        UserDAO.getInstance().insert(user);
                                                        initCrashlyticsUser(user);
                                                        startApplication();
                                                    } else {
                                                        startRegistration();
                                                    }
                                                } else {
                                                    startRegistration();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<User> call, Throwable t) {
                                                startRegistration();
                                            }
                                        });
                                        break;
                                }
                            }
                        })
                        .cancelable(false)
                        .build()
                        .show();
            } else {
                startRegistration();
            }
        }
    }

    private void init() {
        PrefUtils.init(getApplicationContext());

        int userId = PrefUtils.getUserId();
        if(userId > 0) {
            User user = UserDAO.getInstance().findById(userId);
            if(user != null) {
                initUser(user);
            } else {
                APIFactory.getInstance().getUserAPI().find(userId).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User user = response.body();
                        if(user != null) {
                            UserDAO.getInstance().insert(user);
                            initUser(user);
                        } else {
                            PrefUtils.setUserId(-1);
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        PrefUtils.setUserId(-1);
                    }
                });
            }
        }
    }

    private void initUser(User user) {
        initCrashlyticsUser(user);
        loginWithBrokers();
    }

    private void initCrashlyticsUser(User user) {
        Crashlytics.setUserIdentifier(String.valueOf(user.getId()));
        Crashlytics.setUserEmail(user.getEmail());
        Crashlytics.setUserName(user.getFirstName() + " " + user.getLastName());
    }

    private void loginWithBrokers() {
        UBinary uBinary = new UBinary();
        uBinary.login();
    }

    private void startRegistration() {
        Intent intent = new Intent(this, RegistrationFlowActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void finish() {
        overridePendingTransition(0, 0);
        super.finish();
    }

}

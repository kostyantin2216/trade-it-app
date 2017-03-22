package com.tradeitsignals.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.tradeitsignals.external.analytics.AppEventTracker;
import com.tradeitsignals.helpers.TIDictionary;
import com.tradeitsignals.ui.fragments.BaseFragment;

/**
 * Created by Kostyantin on 5/1/2016.
 */
public class BaseActivity extends AppCompatActivity
        implements BaseFragment.BaseFragmentCallbacks {

    private AppEventTracker mEventTracker;
    private MaterialDialog loaderDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mEventTracker = new AppEventTracker(this);
    }

    @Override
    public AppEventTracker getEventTracker() {
        return mEventTracker;
    }

    public void showLoaderDialog(String title) {
        showLoaderDialog(title, false);
    }

    public void showLoaderDialog(String title, boolean canceledOnTouchOutside) {
        loaderDialog = new MaterialDialog.Builder(this)
                .title(title)
                .canceledOnTouchOutside(canceledOnTouchOutside)
                .content(TIDictionary.translate("please_wait"))
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .show();
    }

    public void hideLoaderDialog() {
        if(loaderDialog != null) {
            if(loaderDialog.isShowing()) {
                loaderDialog.cancel();
            }
            loaderDialog = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideLoaderDialog();
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideLoaderDialog();
    }
}

package com.tradeitsignals.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

import com.tradeitsignals.R;
import com.tradeitsignals.external.analytics.AppContentViewTracker;
import com.tradeitsignals.utils.Constants;

/**
 * Created by Kostyantin on 10/24/2015.
 */
public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        AppContentViewTracker.track("Preferences View", Constants.CONTENT_GENERAL, "");
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().setBackgroundColor(Color.WHITE);
        getView().setClickable(true);
    }
}

package com.tradeitsignals.backoffice.ui.fragments;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.preference.AndroidResources;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.tradeitsignals.R;
import com.tradeitsignals.utils.CommonUtils;
import com.tradeitsignals.utils.PrefUtils;

/**
 * Created by Kostyantin on 10/10/2016.
 */
public class AdminSettingsFragment extends PreferenceFragmentCompat implements
        SharedPreferences.OnSharedPreferenceChangeListener,
        PreferenceManager.OnPreferenceTreeClickListener {

    public static AdminSettingsFragment newInstance() {
        return new AdminSettingsFragment();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.admin_preferences);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) super.onCreateView(inflater, container, savedInstanceState);

        view.setBackgroundColor(Color.WHITE);
        view.setClickable(false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        if(preference.getKey().equals("reset_base_api_url")) {
            Toast.makeText(getContext(), "WORKS", Toast.LENGTH_LONG).show();
        }
        return super.onPreferenceTreeClick(preference);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(CommonUtils.notEmpty(key)) {
            PrefUtils.setBaseApiUrl(sharedPreferences.getString(key, PrefUtils.getBaseApiUrl()));
        }
    }
}



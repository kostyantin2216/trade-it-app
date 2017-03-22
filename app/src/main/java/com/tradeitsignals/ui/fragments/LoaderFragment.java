package com.tradeitsignals.ui.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.tradeitsignals.R;

public class LoaderFragment extends Fragment {

    private ProgressBar mProgress;

    public static LoaderFragment newInstance() {
        LoaderFragment fragment = new LoaderFragment();
        return fragment;
    }

    public LoaderFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_loader, container, false);
        mProgress = (ProgressBar) v.findViewById(R.id.pb_splash_progress);
        return v;
    }

    public void onProgressUpdate(int incrementBy) {
        mProgress.incrementProgressBy(incrementBy);
    }

}

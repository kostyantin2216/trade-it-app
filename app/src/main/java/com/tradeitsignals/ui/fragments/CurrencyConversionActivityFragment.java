package com.tradeitsignals.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tradeitsignals.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class CurrencyConversionActivityFragment extends Fragment {

    public CurrencyConversionActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_currency_conversion, container, false);
    }
}

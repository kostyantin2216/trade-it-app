package com.tradeitsignals.ui.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tradeitsignals.external.analytics.AppContentViewTracker;
import com.tradeitsignals.external.analytics.AppEvent;
import com.tradeitsignals.external.analytics.AppEventTracker;
import com.tradeitsignals.ui.components.AcceptOrDeclineButtons;
import com.tradeitsignals.utils.Constants;

/**
 * Created by Kostyantin on 5/27/2016.
 */
public class TermsAndConditionsFragment extends WebViewFragment
        implements AcceptOrDeclineButtons.AcceptOrDeclineCallback {

    private TermsAndConditionsListener mListener;

    public TermsAndConditionsFragment() { }

    public static TermsAndConditionsFragment newInstance() {
        return new TermsAndConditionsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        AcceptOrDeclineButtons buttons = new AcceptOrDeclineButtons(getContext(), this);
        buttons.setBackgroundColor(Color.WHITE);

        if(v instanceof ViewGroup) {
            ((ViewGroup) v).addView(buttons);
        }

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadAssetHtmlFile(Constants.FILE_NAME_TERMS_CONDITIONS);

        AppContentViewTracker.track("Terms And Conditions", Constants.CONTENT_REGISTRATION, "");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (TermsAndConditionsListener) context;
        } catch (ClassCastException ex) {
            throw new IllegalArgumentException("Context does not implement TermsAndConditionsListener", ex);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onAccept() {
        if(mListener != null) {
            mListener.termsAndConditionsAccepted();

            trackEvent(new AppEvent.Builder(AppEventTracker.EVENT_TERMS_COND_ACCEPT).build());
        }
    }

    @Override
    public void onDecline() {
        if(mListener != null) {
            mListener.termsAndConditionsDeclined();

            trackEvent(new AppEvent.Builder(AppEventTracker.EVENT_TERMS_COND_DECLINE).build());
        }
    }

    public interface TermsAndConditionsListener {
        void termsAndConditionsAccepted();
        void termsAndConditionsDeclined();
    }
}

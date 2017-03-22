package com.tradeitsignals.ui.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.tradeitsignals.external.analytics.AppEvent;
import com.tradeitsignals.external.analytics.AppEventTracker;
import com.tradeitsignals.logging.TILogger;
import com.tradeitsignals.ui.activities.BaseActivity;

/**
 * Created by Kostyantin on 9/14/2016.
 *
 *  Acts as an abstract class.
 *  Extends some functionality from {@link BaseActivity} to it's children's fragments.
 *
 */
public class BaseFragment extends Fragment {
    private final static String LOG_TAG = "#" + BaseFragment.class.getSimpleName();

    private BaseFragmentCallbacks mBaseCallbacks;

    protected void trackEvent(AppEvent event) {
        if(mBaseCallbacks != null) {
            AppEventTracker tracker = mBaseCallbacks.getEventTracker();
            if (tracker != null) {
                tracker.trackEvent(event);
            } else {
                TILogger.getLog().e(LOG_TAG, "Could not get tracker to track event: " + event);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof BaseFragmentCallbacks) {
            mBaseCallbacks = (BaseFragmentCallbacks) context;
        } else {
            throw new IllegalArgumentException("Calling activity " + context.getClass().getName()
                    + " needs to extend " + BaseActivity.class.getName());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mBaseCallbacks = null;
    }

    public interface BaseFragmentCallbacks {
        AppEventTracker getEventTracker();
    }
}

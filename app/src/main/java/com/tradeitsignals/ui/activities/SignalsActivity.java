package com.tradeitsignals.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.tradeitsignals.R;
import com.tradeitsignals.datamodel.dao.SignalDAO;
import com.tradeitsignals.external.analytics.AppContentViewTracker;
import com.tradeitsignals.synchronization.SynchronizableTables;
import com.tradeitsignals.ui.components.SlidingTabLayout;
import com.tradeitsignals.ui.fragments.SignalsListFragment;
import com.tradeitsignals.utils.Constants;
import com.tradeitsignals.utils.PrefUtils;

public class SignalsActivity extends BaseNavigationActivity {

    public final static int NAV_DRAWER_POS = 0;
    public final static int VIEW_PAGER_TAB_COUNT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppContentViewTracker.track("Signal List", Constants.CONTENT_SIGNALS, "");
    }

    @Override
    public SynchronizableTables[] getTablesToSynchronize() {
        if (PrefUtils.isFirstTimeAppOpened()) {
            PrefUtils.setFirstTimeAppOpened();
            return new SynchronizableTables[]{SynchronizableTables.SIGNAL, SynchronizableTables.COUNTRY};
        }
        return new SynchronizableTables[]{SynchronizableTables.SIGNAL};
    }

    @Override
    public void onSynchronizationComplete() {
        super.onSynchronizationComplete();
        if(!isDestroyed()) {
            setUpViewPager();
        }
    }

    // ------------------------------------------------------------------------------------------ //
    //                                 # NAVIGATION                                               //
    // ------------------------------------------------------------------------------------------ //

    @Override
    public int getCurrentNavDrawerPosition() {
        return NAV_DRAWER_POS;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_view_pager;
    }

    // ------------------------------------------------------------------------------------------ //
    //                                  # VIEW PAGER                                              //
    // ------------------------------------------------------------------------------------------ //

    public void setUpViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), getResources().getStringArray(R.array.signal_tabs));

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        SlidingTabLayout tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.white);
            }
        });

        tabs.setViewPager(pager);

        if (SignalDAO.getInstance().getActiveSignalsCount() < 1) {
            pager.setCurrentItem(SignalsListFragment.LIST_TYPE_EXPIRED);
        }
    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private CharSequence[] titles;

        public ViewPagerAdapter(FragmentManager fm, CharSequence[] titles) {
            super(fm);
            this.titles = titles;
            fm.executePendingTransactions();
        }

        @Override
        public Fragment getItem(int position) {
            return SignalsListFragment.newInstance(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return VIEW_PAGER_TAB_COUNT;
        }
    }


}

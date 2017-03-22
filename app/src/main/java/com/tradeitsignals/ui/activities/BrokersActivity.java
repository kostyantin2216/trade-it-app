package com.tradeitsignals.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.tradeitsignals.R;
import com.tradeitsignals.datamodel.dao.BrokerDAO;
import com.tradeitsignals.datamodel.data.Broker;
import com.tradeitsignals.external.analytics.AppContentViewTracker;
import com.tradeitsignals.logging.TILogger;
import com.tradeitsignals.synchronization.SynchronizableTables;
import com.tradeitsignals.ui.fragments.BrokerPreviewFragment;
import com.tradeitsignals.ui.fragments.BrokersListFragment;
import com.tradeitsignals.ui.components.SlidingTabLayout;
import com.tradeitsignals.utils.Constants;

public class BrokersActivity extends BaseNavigationActivity implements BrokersListFragment.BrokerSelectionListener {

    public final static int NAV_DRAWER_POS = 1;
    public final static int VIEW_PAGER_TAB_COUNT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppContentViewTracker.track("Brokers Pager", Constants.CONTENT_BROKERS, "");
    }

    @Override
    public SynchronizableTables[] getTablesToSynchronize() {
        return new SynchronizableTables[] {SynchronizableTables.BROKER};
    }

    @Override
    public void onSynchronizationComplete() {
        super.onSynchronizationComplete();
        initViewPager();
    }

    public void initViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), getResources().getStringArray(R.array.broker_tabs));

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        SlidingTabLayout tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);
        tabs.setBackgroundResource(R.drawable.ripple_effect_primary_color);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.white);
            }
        });

        tabs.setViewPager(pager);
    }

    @Override
    public int getCurrentNavDrawerPosition() {
        return NAV_DRAWER_POS;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_view_pager;
    }

    @Override
    public void onBrokerSelected(Broker broker) {
        Intent intent = new Intent(this, BrokerPreviewActivity.class);
        intent.putExtra("broker", broker);
        startActivity(intent);
        /*FragmentManager fm = getSupportFragmentManager();
        BrokerDialogFragment dialog = BrokerDialogFragment.newInstance(broker);
        dialog.show(fm, "broker dialog");*/
    }

    // ---------------------------------------------------------------------------------------------
    // ||                               View Pager Adapter                                        ||
    // ---------------------------------------------------------------------------------------------

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private CharSequence[] titles;

        public ViewPagerAdapter(FragmentManager fm, CharSequence[] titles) {
            super(fm);
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    TILogger.getLog().i("Showing broker preview");
                    return BrokerPreviewFragment.newInstance(getReccomendedBroker());
                default:
                    TILogger.getLog().i("Showing broker list");
                    return BrokersListFragment.newInstance();
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return VIEW_PAGER_TAB_COUNT;
        }

        public Broker getReccomendedBroker() {
            return BrokerDAO.getInstance().getRandomRecommendedBroker();
        }
    }

}

package com.tradeitsignals.ui.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.tradeitsignals.R;
import com.tradeitsignals.datamodel.data.AssetDataChart;
import com.tradeitsignals.datamodel.enums.charts.CurrencyChartData;
import com.tradeitsignals.datamodel.enums.charts.StockChartData;
import com.tradeitsignals.external.analytics.AppContentViewTracker;
import com.tradeitsignals.synchronization.SynchronizableTables;
import com.tradeitsignals.ui.activities.web.ChartWebActivity;
import com.tradeitsignals.ui.fragments.ChartsSelectionListFragment;
import com.tradeitsignals.ui.fragments.ChartsSelectionListFragment.OnChartListInteractionListener;
import com.tradeitsignals.ui.navdrawer.NavigationDrawerCallbacks;
import com.tradeitsignals.ui.components.SlidingTabLayout;
import com.tradeitsignals.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class ChartsListActivity extends BaseNavigationActivity
        implements NavigationDrawerCallbacks,
                   SearchView.OnQueryTextListener,
                   OnChartListInteractionListener {

    public final static int NAV_DRAW_POS = 4;

    private final static int VIEW_PAGER_TAB_COUNT = 2;
    private final static int PAGE_CURRENCY_CHARTS = 0;
    private final static int PAGE_STOCK_CHARTS = 1;

    private ChartsSelectionListFragment currencyListFragment;
    private ChartsSelectionListFragment stocksListFragment;

    private List<AssetDataChart> currencyAssets;
    private List<AssetDataChart> stockAssets;

    private ViewPager pager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpViewPager();

        currencyAssets = CurrencyChartData.makeAssetData();
        stockAssets = StockChartData.makeAssetData();

        if(currencyListFragment == null) {
            currencyListFragment = ChartsSelectionListFragment
                    .newInstance(new ArrayList<>(CurrencyChartData.makeAssetData()));
        }
        if(stocksListFragment == null) {
            stocksListFragment = ChartsSelectionListFragment
                    .newInstance(new ArrayList<>(StockChartData.makeAssetData()));
        }

        AppContentViewTracker.track("Charts List", Constants.CONTENT_CHARTS, "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!isDrawerOpen()) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_chartlist_activity, menu);
            final MenuItem item = menu.findItem(R.id.action_search);
            final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
            searchView.setOnQueryTextListener(this);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public SynchronizableTables[] getTablesToSynchronize() {
        return null;
    }

    @Override
    public int getCurrentNavDrawerPosition() {
        return NAV_DRAW_POS;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_view_pager;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        switch (pager.getCurrentItem()) {
            case PAGE_CURRENCY_CHARTS:
                currencyListFragment.updateAssetsList(filter(currencyAssets, newText));
                return true;
            case PAGE_STOCK_CHARTS:
                stocksListFragment.updateAssetsList(filter(stockAssets, newText));
                return true;
            default:
                return false;
        }
    }

    public List<AssetDataChart> filter(List<AssetDataChart> models, String query) {
        query = query.toLowerCase();

        final List<AssetDataChart> filteredModelList = new ArrayList<>();
        for (AssetDataChart model : models) {
            final String text = model.getAssetName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    public void onAssetSelected(AssetDataChart asset) {
        Intent intent = new Intent(this, ChartWebActivity.class);
        intent.putExtra(Constants.ASSET_NAME, asset.getAssetName());
        intent.putExtra(Constants.ASSET_CODE, asset.getAssetCode());
        startActivity(intent);
    }

    // VIEW PAGER
    // ---------------------------------------------


    private void setUpViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), getResources().getStringArray(R.array.chart_tabs));

        pager = (ViewPager) findViewById(R.id.pager);
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
    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private CharSequence[] titles;

        public ViewPagerAdapter(FragmentManager fm, CharSequence[] titles) {
            super(fm);
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case PAGE_CURRENCY_CHARTS:
                    return currencyListFragment;
                case PAGE_STOCK_CHARTS:
                    return stocksListFragment;
                default:
                    return null;
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
    }

}

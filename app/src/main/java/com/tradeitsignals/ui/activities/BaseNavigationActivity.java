package com.tradeitsignals.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.tradeitsignals.R;
import com.tradeitsignals.backoffice.ui.activities.BackOfficeActivity;
import com.tradeitsignals.datamodel.dao.UserDAO;
import com.tradeitsignals.datamodel.data.User;
import com.tradeitsignals.ui.fragments.SettingsFragment;
import com.tradeitsignals.ui.navdrawer.NavigationDrawerCallbacks;
import com.tradeitsignals.ui.navdrawer.NavigationDrawerFragment;
import com.tradeitsignals.helpers.TIConfiguration;

/**
 * Created by Kostyantin on 10/24/2015.
 */
public abstract class BaseNavigationActivity extends SynchronizationActivity implements NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);

        setUpNavDrawer(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mNavigationDrawerFragment != null)
            mNavigationDrawerFragment.selectItem(getCurrentNavDrawerPosition());
    }

    public void setUpNavDrawer(Toolbar toolbar) {
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.menu_base, menu);

            User user = UserDAO.getInstance().getUser();
            if (TIConfiguration.isDevelopment() || (user != null && user.isAdmin())) {
                MenuItem devOptions = menu.findItem(R.id.dev_options);
                devOptions.setVisible(true);
            }

            MenuItem searchItem = menu.findItem(R.id.search);
            searchItem.setVisible(false);

            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                openSettings();
                break;
            case R.id.dev_options:
                intent = new Intent(this, BackOfficeActivity.class);
                startActivity(intent);
                return true;
            case R.id.exit_app:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openSettings() {
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen()) {
            mNavigationDrawerFragment.closeDrawer();
        } else if (!getFragmentManager().popBackStackImmediate()) {
            super.onBackPressed();
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        if(position != getCurrentNavDrawerPosition()) {
            Intent intent = null;
            switch (position) {
                case SignalsActivity.NAV_DRAWER_POS:
                    intent = new Intent(this, SignalsActivity.class);
                    break;
                case BrokersActivity.NAV_DRAWER_POS:
                    intent = new Intent(this, BrokersActivity.class);
                    break;
                case RssNewsFeedActivity.NAV_DRAW_POS:
                    intent = new Intent(this, RssNewsFeedActivity.class);
                    break;
                case ContentPageListActivity.NAV_DRAWER_POS:
                    intent = new Intent(this, ContentPageListActivity.class);
                    break;
                case ChartsListActivity.NAV_DRAW_POS:
                    intent = new Intent(this, ChartsListActivity.class);
                    break;
            }
            if(intent != null) {
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public abstract int getCurrentNavDrawerPosition();

    public abstract int getLayoutResId();

    public boolean isDrawerOpen() {
        return mNavigationDrawerFragment.isDrawerOpen();
    }

}

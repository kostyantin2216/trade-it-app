package com.tradeitsignals.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.tradeitsignals.R;
import com.tradeitsignals.datamodel.dao.BrokerDAO;
import com.tradeitsignals.datamodel.data.Broker;
import com.tradeitsignals.external.analytics.AppContentViewTracker;
import com.tradeitsignals.ui.fragments.BrokerPreviewFragment;
import com.tradeitsignals.utils.Constants;

public class BrokerPreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null && findViewById(R.id.fragment_container) != null) {
            Broker broker;
            if(getIntent().hasExtra("broker")) {
                broker = getIntent().getParcelableExtra("broker");
                AppContentViewTracker.track("Broker Preview", Constants.CONTENT_BROKERS, broker.getName());
            } else {
                broker = BrokerDAO.getInstance().getRandomRecommendedBroker();
            }

            BrokerPreviewFragment brokerPreviewFrag = BrokerPreviewFragment.newInstance(broker);

            getSupportFragmentManager().beginTransaction()
                                       .add(R.id.fragment_container, brokerPreviewFrag)
                                       .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();

        }

        return super.onOptionsItemSelected(item);
    }


}

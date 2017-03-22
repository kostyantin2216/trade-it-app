package com.tradeitsignals.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tradeitsignals.R;
import com.tradeitsignals.datamodel.dao.SignalDAO;
import com.tradeitsignals.datamodel.data.Signal;
import com.tradeitsignals.datamodel.enums.charts.CurrencyChartData;
import com.tradeitsignals.logging.TILogger;
import com.tradeitsignals.ui.activities.web.ChartWebActivity;
import com.tradeitsignals.ui.adapters.SignalsRecyclerAdapter;
import com.tradeitsignals.utils.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SignalsListFragment extends Fragment
        implements SignalsRecyclerAdapter.OnSignalInteractionListener {

    private final static long SIGNAL_UPDATE_INTERVAL_MILLIS = 10 * 1000;

    private SignalDAO signalDao;
    private Handler signalUpdateHandler;
    private Runnable signalUpdateRunnable;
    private SignalsRecyclerAdapter mAdapter;
    private TextView tvNoSignals;

    private int listType;

	public final static int LIST_TYPE_ACTIVE = 0;
	public final static int LIST_TYPE_EXPIRED = 1;

    private static final String KEY_LIST_TYPE = "list_type";

    public static SignalsListFragment newInstance(int listType) {
        SignalsListFragment fragment = new SignalsListFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_LIST_TYPE, listType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signalDao = SignalDAO.getInstance();
        listType = getArguments().getInt(KEY_LIST_TYPE);
        this.signalUpdateRunnable = new Runnable() {
            @Override
            public void run() {
                TILogger.getLog().d(" -- refreshing signals...");
                List<Signal> signals = getSignals();
                if(signals != null) {
                    mAdapter.swapItems(getSignals());
                    toggleNoSignalsViewVisibility();
                }
                signalUpdateHandler.postDelayed(this, SIGNAL_UPDATE_INTERVAL_MILLIS);
            }
        };
        this.signalUpdateHandler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rv, container, false);


        RecyclerView mList = (RecyclerView) v.findViewById(R.id.container_drawer_content);
        mList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mList.setItemAnimator(new DefaultItemAnimator());
        mList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerView.canScrollVertically(1)) {
                    onScrolledToBottom();
                }
            }
        });

        List<Signal> signals = getSignals();
        if(signals == null) {
            signals = new ArrayList<>();
        }
        mAdapter = new SignalsRecyclerAdapter(signals, this);
        mList.setAdapter(mAdapter);

        tvNoSignals = (TextView) v.findViewById(R.id.tv_empty_rv);
        toggleNoSignalsViewVisibility();


        return v;
    }

    private void onScrolledToBottom() {

    }

    @Override
    public void onResume() {
        super.onResume();
        signalUpdateHandler.post(signalUpdateRunnable);
    }

    @Override
    public void onPause() {
        super.onPause();
        signalUpdateHandler.removeCallbacks(signalUpdateRunnable);
    }

    private List<Signal> getSignals() {
    	if(listType == LIST_TYPE_ACTIVE) {
    		return signalDao.getActiveSignals();
    	} else {
    		return signalDao.getExpiredSignals();
    	}
    }

    private boolean currentSignalsAvailable() {
        if(signalDao.getActiveSignalsCount() > 0) {
            return true;
        }
        return false;
    }

    private void toggleNoSignalsViewVisibility() {
        if(listType == LIST_TYPE_ACTIVE) {
            if (currentSignalsAvailable() && tvNoSignals.getVisibility() == View.VISIBLE) {
                tvNoSignals.setVisibility(View.GONE);
            } else if (!currentSignalsAvailable() && tvNoSignals.getVisibility() == View.GONE) {
                tvNoSignals.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onSignalSelected(Signal signal) {
        CurrencyChartData asset = CurrencyChartData.findByName(signal.getAsset());
        if(asset != null) {
            Intent intent = new Intent(getContext(), ChartWebActivity.class);
            intent.putExtra(Constants.ASSET_NAME, asset.getCurrencyName());
            intent.putExtra(Constants.ASSET_CODE, asset.getCode());
            startActivity(intent);
        }
    }

}
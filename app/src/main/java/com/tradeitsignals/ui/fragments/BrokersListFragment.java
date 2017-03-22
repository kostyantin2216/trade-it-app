package com.tradeitsignals.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tradeitsignals.R;
import com.tradeitsignals.datamodel.dao.BrokerDAO;
import com.tradeitsignals.datamodel.data.Broker;
import com.tradeitsignals.logging.TILogger;
import com.tradeitsignals.ui.adapters.CommonRecyclerAdapter;
import com.tradeitsignals.ui.listeners.RecyclerItemClickListener;

import java.util.List;

public class BrokersListFragment extends Fragment {

    private BrokerSelectionListener mCallbacks;
    private List<Broker> brokers;

    public static BrokersListFragment newInstance() {
        BrokersListFragment fragment = new BrokersListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public BrokersListFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.brokers = getBrokers();
        TILogger.getLog().d("#Broker List", brokers.toString());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallbacks = (BrokerSelectionListener) context;
        } catch (ClassCastException e) {
            TILogger.getLog().e("Activity must implement BrokerSelectionListener.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rv, container, false);

        RecyclerView rvReviewsList = (RecyclerView) v.findViewById(R.id.container_drawer_content);
        rvReviewsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvReviewsList.setItemAnimator(new DefaultItemAnimator());

        CommonRecyclerAdapter<Broker> adapter = new CommonRecyclerAdapter<>(R.layout.list_item_broker, brokers,
                CommonRecyclerAdapter.TYPE_BROKERS);
        
        rvReviewsList.setAdapter(adapter);

        rvReviewsList.addOnItemTouchListener(
            new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    mCallbacks.onBrokerSelected(brokers.get(position));
                }
            })
        );

        return v;
    }

    private List<Broker> getBrokers() {
        return BrokerDAO.getInstance().getActiveBrokers();
    }

    public interface BrokerSelectionListener {
        void onBrokerSelected(Broker broker);
    }
}

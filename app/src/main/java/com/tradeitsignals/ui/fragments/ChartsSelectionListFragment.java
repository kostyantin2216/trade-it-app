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
import com.tradeitsignals.datamodel.data.AssetDataChart;
import com.tradeitsignals.ui.adapters.CommonRecyclerAdapter;
import com.tradeitsignals.ui.listeners.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class ChartsSelectionListFragment extends Fragment {

    public final static String ASSET_LIST = "assetList";

    private RecyclerView mRecycler;
    private CommonRecyclerAdapter<AssetDataChart> mAdapter;

    private OnChartListInteractionListener mListener;

    private List<AssetDataChart> assets;

    public ChartsSelectionListFragment() { }

    public static ChartsSelectionListFragment newInstance(ArrayList<AssetDataChart> assets) {
        ChartsSelectionListFragment fragment = new ChartsSelectionListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ASSET_LIST, assets);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null) {
            assets = args.getParcelableArrayList(ASSET_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rv, container, false);

        mAdapter = new CommonRecyclerAdapter<>(R.layout.list_item_asset,
                assets, CommonRecyclerAdapter.TYPE_CHART_LIST);

        mRecycler = (RecyclerView) v.findViewById(R.id.container_drawer_content);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        mRecycler.setAdapter(mAdapter);
        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        mRecycler.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        AssetDataChart asset = assets.get(position);
                        mListener.onAssetSelected(asset);
                    }
                }
        ));

        return v;
    }

    public void updateAssetsList(List<AssetDataChart> assets) {
        mRecycler.scrollToPosition(0);
        mAdapter.animateTo(assets);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnChartListInteractionListener) context;
        } catch(ClassCastException e) {
            throw new ClassCastException("Activity must implement OnChartListInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnChartListInteractionListener {
        void onAssetSelected(AssetDataChart asset);
    }

}

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
import com.tradeitsignals.datamodel.data.ArticleData;
import com.tradeitsignals.ui.adapters.CommonRecyclerAdapter;
import com.tradeitsignals.ui.listeners.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThorneBird on 3/30/2016.
 */
public class RssNewsFragmentList extends Fragment {

    private OnNewsInteractionListener mListener;

    private RecyclerView mRecyclerView;
    private CommonRecyclerAdapter<ArticleData> mAdapter;

    private List<ArticleData> articleDataList;

    public RssNewsFragmentList() { }

    public static RssNewsFragmentList newInstance() {
        RssNewsFragmentList fragment = new RssNewsFragmentList();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        articleDataList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rv, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.container_drawer_content);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        mListener.onArticleSelected(articleDataList.get(position));
                    }
                }));

        mAdapter = new CommonRecyclerAdapter<>(R.layout.list_item_article, articleDataList,
                CommonRecyclerAdapter.TYPE_ARTICLE_LIST);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    public void onNewsLoaded(List<ArticleData> news) {
        articleDataList = news;
        mAdapter.animateTo(articleDataList);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnNewsInteractionListener) context;
        } catch(ClassCastException e) {
            throw new ClassCastException("Context must implement OnNewsInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnNewsInteractionListener {
        void onArticleSelected(ArticleData article);
    }

}

package com.tradeitsignals.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tradeitsignals.R;
import com.tradeitsignals.datamodel.dao.ContentPageDAO;
import com.tradeitsignals.datamodel.data.ContentPage;
import com.tradeitsignals.external.analytics.AppContentViewTracker;
import com.tradeitsignals.synchronization.SynchronizableTables;
import com.tradeitsignals.ui.activities.web.ContentPagePreviewActivity;
import com.tradeitsignals.ui.adapters.CommonRecyclerAdapter;
import com.tradeitsignals.ui.listeners.RecyclerItemClickListener;
import com.tradeitsignals.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class ContentPageListActivity extends BaseNavigationActivity {

    public final static int NAV_DRAWER_POS = 3;

    private List<ContentPage> pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppContentViewTracker.track("Strategies List", Constants.CONTENT_GENERAL, "");
    }

    @Override
    public SynchronizableTables[] getTablesToSynchronize() {
        return new SynchronizableTables[] {SynchronizableTables.CONTENT_PAGE};
    }

    @Override
    public void onSynchronizationComplete() {
        super.onSynchronizationComplete();
        if(!isDestroyed()) {
            pages = ContentPageDAO.getInstance().findAll();

            initList();
        }
    }

    public void initList() {
        CommonRecyclerAdapter<ContentPage> adapter = new CommonRecyclerAdapter<>(
                R.layout.list_item_content_page,
                pages,
                CommonRecyclerAdapter.TYPE_CONTENT_PAGE
        );

        RecyclerView rvContentPagesList = (RecyclerView) findViewById(R.id.container_drawer_content);
        rvContentPagesList.setLayoutManager(new LinearLayoutManager(this));
        rvContentPagesList.setItemAnimator(new DefaultItemAnimator());
        rvContentPagesList.setAdapter(adapter);
        rvContentPagesList.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(ContentPageListActivity.this, ContentPagePreviewActivity.class);
                        intent.putExtra(ContentPagePreviewActivity.CONTENT_PAGE_POSITION, position);

                        startActivity(intent);
                    }
                })
        );
    }

    public List<String> getPageNames() {
        List<String> pageNames = new ArrayList<>();
        for(ContentPage page : pages) {
            pageNames.add(page.getTemplateFileName());
        }
        return pageNames;
    }

    @Override
    public int getCurrentNavDrawerPosition() {
        return NAV_DRAWER_POS;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_with_recycler_view;
    }

}

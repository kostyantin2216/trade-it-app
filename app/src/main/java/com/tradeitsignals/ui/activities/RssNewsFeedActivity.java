package com.tradeitsignals.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import com.tradeitsignals.R;
import com.tradeitsignals.datamodel.data.ArticleData;
import com.tradeitsignals.external.NewsRssFetcher;
import com.tradeitsignals.external.analytics.AppContentViewTracker;
import com.tradeitsignals.helpers.TIDictionary;
import com.tradeitsignals.synchronization.SynchronizableTables;
import com.tradeitsignals.ui.activities.web.SinglePageWebView;
import com.tradeitsignals.ui.fragments.RssNewsFragmentList;
import com.tradeitsignals.utils.Constants;

import java.util.List;

public class RssNewsFeedActivity extends BaseNavigationActivity
    implements NewsRssFetcher.NewsFetcherCallback,
               RssNewsFragmentList.OnNewsInteractionListener {

    public final static int NAV_DRAW_POS = 2;
    private static final String URL_DAILY_FX_FEED = "https://www.dailyfx.com/feeds/daily_briefings";

    private RssNewsFragmentList rssNewsFragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null || rssNewsFragmentList == null) {
            rssNewsFragmentList = RssNewsFragmentList.newInstance();
            getSupportFragmentManager().beginTransaction()
                                       .add(R.id.container_drawer_content, rssNewsFragmentList)
                                       .commit();
        }

        AppContentViewTracker.track("News List", Constants.CONTENT_NEWS, URL_DAILY_FX_FEED);

        new NewsRssFetcher(URL_DAILY_FX_FEED, this).execute();
    }

    @Override
    public int getCurrentNavDrawerPosition() {
        return NAV_DRAW_POS;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_fragment_holder;
    }
    @Override
    public SynchronizableTables[] getTablesToSynchronize() {
        return new SynchronizableTables[0];
    }

    @Override
    public void onNewsFetchStarted() {
        showLoaderDialog(TIDictionary.translate("loading_news"));
    }

    @Override
    public void onNewsFetched(List<ArticleData> news) {
        hideLoaderDialog();
        rssNewsFragmentList.onNewsLoaded(news);
    }

    @Override
    public void onArticleSelected(ArticleData article) {
        Intent intent = new Intent(this, SinglePageWebView.class);
        intent.putExtra(Constants.URL, article.getLink());
        startActivity(intent);
    }
}

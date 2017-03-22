package com.tradeitsignals.ui.activities.web;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tradeitsignals.R;
import com.tradeitsignals.external.analytics.AppContentViewTracker;
import com.tradeitsignals.helpers.TIDictionary;
import com.tradeitsignals.utils.Constants;
import com.tradeitsignals.utils.StringUtils;

public class SinglePageWebView extends BaseWebViewActivity {

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        url = getIntent().getStringExtra(Constants.URL);
        if(StringUtils.notEmpty(url)) {
            AppContentViewTracker.track("Single Page Web View", Constants.CONTENT_GENERAL, url);
            loadUrl(url);
            init();
        } else {
            loadErrorPage("Could not load " + (url == null ? "null" : "empty") + " url");
        }
    }

    protected void init() {
        super.init();
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showLoaderDialog(TIDictionary.translate("loading_article"), true);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideLoaderDialog();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.equals(SinglePageWebView.this.url)) {
                    return false;
                }
                return true;
            }
        });
    }

}

package com.tradeitsignals.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tradeitsignals.R;
import com.tradeitsignals.utils.Constants;


/**
 * Created by ThorneBird on 5/14/2016.
 */
public abstract class WebViewFragment extends BaseFragment {

    protected WebView webView;

    public WebViewFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);
        this.webView = (WebView) view.findViewById(R.id.wv_web_view);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                String errorMessage = String.format("Error Code: %d \nFailing URL: %s \nDescription: %s",
                        errorCode, failingUrl, description);
                webView.loadData(errorMessage, "text/html", null);
            }
        });

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLoadsImagesAutomatically(true);

        return view;
    }

    public void loadUrl(String url) {
        if (url != null && url.trim().length() > 0) {
            webView.loadUrl(url);
        } else {
            loadErrorPage();
        }
    }

    public void loadAssetFile(String fileName) {
        loadUrl(Constants.BASE_ASSETS_PATH + fileName);
    }

    public void loadAssetHtmlFile(String fileName) {
        loadUrl(Constants.BASE_ASSETS_PATH + Constants.ASSETS_HTML_PATH + fileName);
    }

    protected void loadErrorPage() {
        loadErrorPage(Constants.DEFAULT_ERROR_WEB_VIEW_MSG);
    }

    protected void loadErrorPage(String error) {
        String errorHtml = String.format(Constants.HTML_ERROR_W_MSG_PLACEHOLDER, error);
        webView.loadData(errorHtml, "text/html", null);
    }

}

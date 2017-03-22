package com.tradeitsignals.ui.activities.web;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.tradeitsignals.R;
import com.tradeitsignals.ui.activities.BaseActivity;
import com.tradeitsignals.utils.Constants;

import java.io.File;

/**
 * Created by Kostyantin on 9/15/2015.
 */
public abstract class BaseWebViewActivity extends BaseActivity {

    protected final static String LOG_TAG = "#WebViewActivity";
    protected final static String HTML_ERROR_W_MSG_PLACEHOLDER = "<html><body><h1>Error</h1> <p><b>%s</b></p></body></html>";
    protected final static String DEFAULT_ERROR_MSG = "No/Wrong url was provided for Browser view.";

    protected WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }

    protected void init() {
        webView = (WebView) findViewById(R.id.wv_web_view);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                setProgress(newProgress * 1000);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                String errorMessage = String.format("Error Code: %d \nFailing URL: %s \nDescription: %s",
                        errorCode, failingUrl, description);
                webView.loadData(errorMessage, "text/html", null);
                Toast.makeText(getBaseContext(), errorMessage, Toast.LENGTH_LONG).show();
            }
        });

        initWebSettings(webView.getSettings());
    }

    protected void initWebSettings(WebSettings settings) {
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLoadsImagesAutomatically(true);
    }

    public void loadUrl(String url) {
        if (url != null && url.trim().length() > 0) {
            webView.loadUrl(url);
        } else {
            loadErrorPage();
        }
    }

    /**
     * Currently only loads files from the assets folder.
     *
     * @param fileName String of the file name to load in the assets folder.
     */
    public void loadFile(String fileName) {
        webView.loadUrl(Constants.BASE_ASSETS_PATH + fileName);
    }

    protected void loadErrorPage() {
        loadErrorPage(DEFAULT_ERROR_MSG);
    }

    protected void loadErrorPage(String error) {
        String errorHtml = String.format(HTML_ERROR_W_MSG_PLACEHOLDER, error);
        webView.loadData(errorHtml, "text/html", null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

}

package com.tradeitsignals.ui.activities.web;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.tradeitsignals.R;

import java.io.File;

/**
 * Created by Kostyantin on 7/17/2015.
 */
public class WebViewActivity extends AppCompatActivity {

    public final static String URL = "url";
    public final static String FILE_NAME = "file_name";
    public final static String PAGE_TYPE = "page_type";
    public final static int PAGE_TYPE_URL = 0;
    public final static int PAGE_TYPE_FILE = 1;

    protected final static String BASE_ASSETS_PATH = "file:///android_asset" + File.separator + "html" + File.separator;
    protected final static String ERROR_HTML = "<html><body><h1>Error</h1> <p><b>No/Wrong url was provided for Browser view.</b></p></body></html>";

    protected WebView webView;

    protected int pageType;

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

    private void init() {
        webView = (WebView) findViewById(R.id.wv_web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                WebViewActivity.this.setProgress(newProgress * 1000);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                String errorMessage = String.format("Error Code: %d \nFailing URL: %s \nDescription: %s",
                        errorCode, failingUrl, description);
                webView.loadData(errorMessage, "text/html", null);
                Toast.makeText(WebViewActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });

        Intent intent = getIntent();
        pageType = intent.getExtras().getInt(PAGE_TYPE);
        switch(pageType) {
            case PAGE_TYPE_URL:
                loadUrl(intent.getExtras().getString(URL));
                break;
            case PAGE_TYPE_FILE:
                loadFile(intent.getExtras().getString(FILE_NAME));
                break;
        }
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
        webView.loadUrl(BASE_ASSETS_PATH + fileName);
    }

    protected void loadErrorPage() {
        loadErrorPage(ERROR_HTML);
    }

    protected void loadErrorPage(String error) {
        webView.loadData(ERROR_HTML, "text/html", null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}

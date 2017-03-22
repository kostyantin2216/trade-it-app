package com.tradeitsignals.ui.activities.web;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.tradeitsignals.R;
import com.tradeitsignals.datamodel.dao.ChartDataDAO;
import com.tradeitsignals.datamodel.data.ChartData;
import com.tradeitsignals.datamodel.enums.charts.ChartIndicator;
import com.tradeitsignals.datamodel.enums.charts.ChartInterval;
import com.tradeitsignals.datamodel.enums.charts.ChartType;
import com.tradeitsignals.external.analytics.AppContentViewTracker;
import com.tradeitsignals.helpers.TIDictionary;
import com.tradeitsignals.logging.TILogger;
import com.tradeitsignals.ui.components.ChartToolbar;
import com.tradeitsignals.utils.Constants;

import org.apache.commons.io.IOUtils;

import java.io.IOException;


public class ChartWebActivity extends BaseWebViewActivity
        implements ChartToolbar.ChartToolbarSelectionCallback {

    private final static String PLACEHOLDER_HEIGHT = "%HEIGHT%";
    private final static String PALCEHOLDER_WIDTH = "%WIDTH%";
    private final static String PLACEHOLDER_CURRENCY = "%CURRENCY%";
    private final static String PLACEHOLDER_INTERVAL = "%INTERVAL%";
    private final static String PLACEHOLDER_CHART_TYPE = "%TYPE%";
    private final static String PLACEHOLDER_INDICATOR = "%INDICATOR%";

    private int screenHeight, screenWidth;
    private String assetName;
    private String assetCode;
    private ChartData chartData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getScreenSize(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        getSupportActionBar().hide();

        Intent intent = getIntent();
        assetName = intent.getStringExtra(Constants.ASSET_NAME);
        assetCode = intent.getStringExtra(Constants.ASSET_CODE);

        AppContentViewTracker.track("Chart View", Constants.CONTENT_CHARTS, assetName);

        init();
        loadChartData();
        createToolbar();
        loadChart();
    }

    private void getScreenSize(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        screenHeight = (int) dpHeight;
        screenWidth = (int) dpWidth;
    }

    @Override
    protected void init() {
        final String title = String.format(TIDictionary.translate("loading_chart"), assetName);

        webView = (WebView) findViewById(R.id.wv_web_view);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showLoaderDialog(title, true);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideLoaderDialog();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return !url.contains(Constants.BASE_ASSETS_PATH);
            }
        });

        initWebSettings(webView.getSettings());

        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
    }

    @Override
    protected void initWebSettings(WebSettings settings) {
        super.initWebSettings(settings);

        settings.setAllowFileAccess(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDomStorageEnabled(true);
    }

    private void createToolbar() {
        ChartToolbar chartToolbar = new ChartToolbar(this, this);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        RelativeLayout webViewContainer = (RelativeLayout) findViewById(R.id.container_web_view);
        webViewContainer.addView(chartToolbar, lp);
    }

    private void loadChart() {
        String content = null;
        try {
            content = IOUtils.toString(getAssets().open("html/" + Constants.FILE_NAME_CHART_WIDGET))
                    .replaceAll(PLACEHOLDER_CURRENCY, assetCode)
                    .replaceAll(PLACEHOLDER_HEIGHT, String.valueOf(screenHeight))
                    .replaceAll(PALCEHOLDER_WIDTH, String.valueOf(screenWidth))
                    .replaceAll(PLACEHOLDER_INTERVAL, chartData.getIntervalCode())
                    .replaceAll(PLACEHOLDER_CHART_TYPE, chartData.getChartTypeCode())
                    .replaceAll(PLACEHOLDER_INDICATOR, chartData.getIndicatorCode());

            webView.loadDataWithBaseURL(
                    Constants.BASE_ASSETS_PATH + Constants.ASSETS_HTML_PATH + Constants.FILE_NAME_CHART_WIDGET,
                    content, "text/html", "UTF-8", null);
        } catch (IOException e) {
            TILogger.getLog().e(LOG_TAG, "Error while loading chart with content: \n" + content, e);
        }
    }

    private void loadChartData() {
        chartData = ChartDataDAO.getInstance().findByName(assetName);
        if(chartData == null) {
            chartData = new ChartData(
                    assetName,
                    Constants.DEFAULT_CHART_INTERVAL,
                    Constants.DEFAULT_CHART_TYPE,
                    Constants.DEFAULT_CHART_INDICATOR
            );
        }
    }

    @Override
    public void onChartToolbarSelection(ChartToolbar.Section section, String value) {
        int what = Constants.VAR_DISPLAY;
        switch (section) {
            case INDICATOR:
                chartData.setIndicator(ChartIndicator.findBy(what, value));
                break;
            case INTERVAL:
                chartData.setInterval(ChartInterval.findBy(what, value));
                break;
            case TYPE:
                chartData.setChartType(ChartType.findBy(what, value));
                break;
        }

        ChartDataDAO.getInstance().update(chartData);
        loadChart();
    }

    @Override
    public String getSelectedToolbarValue(ChartToolbar.Section section) {
        switch (section) {
            case INTERVAL:
                return chartData.getIntervalDisplay();
            case INDICATOR:
                return chartData.getIndicatorDisplay();
            case TYPE:
                return chartData.getChartTypeDisplay();
            default:
                return null;
        }
    }
}

package com.tradeitsignals.utils;

import com.tradeitsignals.datamodel.enums.charts.ChartIndicator;
import com.tradeitsignals.datamodel.enums.charts.ChartInterval;
import com.tradeitsignals.datamodel.enums.charts.ChartType;

import java.io.File;

/**
 * Created by Kostyantin on 4/29/2016.
 */
public class Constants {

    public final static String URL = "url";

    public final static long TIMESTAMP_1_DAY = 86400000;

    public final static String BASE_ASSETS_PATH = "file:///android_asset" + File.separator;
    public final static String ASSETS_HTML_PATH = "html" + File.separator;
    public final static String FILE_NAME_TERMS_CONDITIONS = "terms_conditions.html";
    public final static String FILE_NAME_CHART_WIDGET = "chartwidget.html";

    public final static String HTML_ERROR_W_MSG_PLACEHOLDER = "<html><body><h1>Error</h1> <p><b>%s</b></p></body></html>";
    public final static String DEFAULT_ERROR_WEB_VIEW_MSG = "No/Wrong url was provided for Browser view.";

    public final static String PATH = "path";
    public final static String IS_ASSET = "isAsset";
    public final static String ASSET_NAME = "assetName";
    public final static String ASSET_CODE = "assetCode";

    public final static String CONTENT_SIGNALS = "signals";
    public final static String CONTENT_BROKERS = "brokers";
    public final static String CONTENT_NEWS = "news";
    public final static String CONTENT_CHARTS = "charts";
    public final static String CONTENT_GENERAL = "general";
    public static final String CONTENT_REGISTRATION = "registration";


    public final static int UBINARY_CODE = 1;

    public final static int TEST_USER_ID = 1260;

    public final static ChartIndicator DEFAULT_CHART_INDICATOR = ChartIndicator.BOLLINGERBANDS;
    public final static ChartInterval DEFAULT_CHART_INTERVAL = ChartInterval.ONE_HOUR;
    public final static ChartType DEFAULT_CHART_TYPE = ChartType.CANDLE;

    public final static int VAR_CODE = 0;
    public final static int VAR_DISPLAY = 1;

    public final static String ARG_CLICK_ID = "clickId";
    public static final String ERROR_CODE = "error_code";
    public static final String LISTENER = "listener";
}

package com.tradeitsignals.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tradeitsignals.R;
import com.tradeitsignals.datamodel.enums.charts.StockChartData;
import com.tradeitsignals.logging.TILogger;

import java.util.HashMap;
import java.util.Map;

public class UIUtils {

    public final static Map<String, Integer> CURRENCY_PAIR_FLAGS_MAP = getFlags();
    public final static Map<String, Integer> STOCK_FLAGS_MAP = getStockImages();

    private static HashMap<String, Integer> getFlags() {
        HashMap<String, Integer> flags = new HashMap<String, Integer>();
        flags.put("AUD/CAD", R.drawable.audcad);
        flags.put("CHF/JPY", R.drawable.chfjpy);
        flags.put("EUR/NZD", R.drawable.eurnzd);
        flags.put("GBP/JPY", R.drawable.gbpjpy);
        flags.put("TRY/JPY", R.drawable.tryjpy);
        flags.put("USD/NOK", R.drawable.usdnok);
        flags.put("AUD/CHF", R.drawable.audchf);
        flags.put("EUR/AUD", R.drawable.euraud);
        flags.put("EUR/SEK", R.drawable.eursek);
        flags.put("GBP/NZD", R.drawable.gbpnzd);
        flags.put("USD/CAD", R.drawable.usdcad);
        flags.put("USD/SEK", R.drawable.usdsek);
        flags.put("AUD/JPY", R.drawable.audjpy);
        flags.put("EUR/CAD", R.drawable.eurcad);
        flags.put("EUR/TRY", R.drawable.eurtry);
        flags.put("GBP/USD", R.drawable.gbpusd);
        flags.put("USD/CHF", R.drawable.usdchf);
        flags.put("USD/TRY", R.drawable.usdtry);
        flags.put("AUD/NZD", R.drawable.audnzd);
        flags.put("EUR/CHF", R.drawable.eurchf);
        flags.put("EUR/USD", R.drawable.eurusd);
        flags.put("NZD/CAD", R.drawable.nzdcad);
        flags.put("USD/CNH", R.drawable.usdcnh);
        flags.put("USD/ZAR", R.drawable.usdzar);
        flags.put("AUD/USD", R.drawable.audusd);
        flags.put("EUR/GBP", R.drawable.eurgbp);
        flags.put("GBP/AUD", R.drawable.gbpaud);
        flags.put("AUD/GBP", R.drawable.gbpaud);
        flags.put("NZD/CHF", R.drawable.nzdchf);
        flags.put("USD/HKD", R.drawable.usdhkd);
        flags.put("ZAR/JPY", R.drawable.zarjpy);
        flags.put("CAD/CHF", R.drawable.cadchf);
        flags.put("EUR/JPY", R.drawable.eurjpy);
        flags.put("GBP/CAD", R.drawable.gbpcad);
        flags.put("NZD/JPY", R.drawable.nzdjpy);
        flags.put("USD/JPY", R.drawable.usdjpy);
        flags.put("CAD/JPY", R.drawable.cadjpy);
        flags.put("EUR/NOK", R.drawable.eurnok);
        flags.put("GBP/CHF", R.drawable.gbpchf);
        flags.put("NZD/USD", R.drawable.nzdusd);
        flags.put("USD/MXN", R.drawable.usdmxn);
        flags.put("CAD/NZD", R.drawable.cadnzd);
        return flags;
    }


    private static Map<String, Integer> getStockImages() {
        HashMap<String, Integer> stockImage = new HashMap<>();
        stockImage.put(StockChartData.GOOGLE.getStockName(), R.drawable.ic_google);
        stockImage.put(StockChartData.TWITTER.getStockName(), R.drawable.ic_twitter);
        stockImage.put(StockChartData.APPLE.getStockName(), R.drawable.ic_apple);
        stockImage.put(StockChartData.BAIDU.getStockName(), R.drawable.ic_baidu);
        stockImage.put(StockChartData.ALIBABA.getStockName(), R.drawable.ic_alibaba);
        stockImage.put(StockChartData.AMAZON.getStockName(), R.drawable.ic_amazon);
        stockImage.put(StockChartData.BLACKBERY.getStockName(), R.drawable.ic_blackberry);
        stockImage.put(StockChartData.BOEING.getStockName(), R.drawable.ic_boeing);
        stockImage.put(StockChartData.CATERPILLER.getStockName(), R.drawable.ic_caterpiller);
        stockImage.put(StockChartData.CITIGROUP.getStockName(), R.drawable.ic_citigroup);
        stockImage.put(StockChartData.COCACOLA.getStockName(), R.drawable.ic_coke);
        stockImage.put(StockChartData.DISNEY.getStockName(), R.drawable.ic_diseny);
        stockImage.put(StockChartData.EBAY.getStockName(), R.drawable.ic_ebay);
        stockImage.put(StockChartData.FACEBOOK.getStockName(), R.drawable.ic_facebook);
        stockImage.put(StockChartData.FERARRI.getStockName(), R.drawable.ic_ferrari);
        stockImage.put(StockChartData.GENERALMOTORS.getStockName(), R.drawable.ic_generalmotors);
        stockImage.put(StockChartData.GROUPON.getStockName(), R.drawable.ic_groupon);
        stockImage.put(StockChartData.IBM.getStockName(), R.drawable.ic_ibm);

        return stockImage;
    }

    public static void showError(Context context, String error, Throwable throwable, boolean useLog) {
        if (useLog) {
            TILogger.getLog().e(error, throwable);
        }
        Toast.makeText(context, error, Toast.LENGTH_LONG).show();
    }

    public static void showError(Context context, String error, Throwable throwable) {
        showError(context, error, null, false);
    }

    public static void showError(Context context, String error, boolean useLog) {
        showError(context, error, null, useLog);
    }

    public static void showError(Context context, String error) {
        showError(context, error, null, false);
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float dpToPx(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float pxToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    public static void showSnackbar(Activity activity, @StringRes int resId) {
        showSnackbar(activity, activity.getString(resId));
    }

    public static void showSnackbar(Activity activity, String message) {
        View view = ((ViewGroup) activity
                .findViewById(android.R.id.content)).getChildAt(0);
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }
}

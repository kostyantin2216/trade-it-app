package com.tradeitsignals.datamodel.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.tradeitsignals.datamodel.BaseDAO;
import com.tradeitsignals.datamodel.data.ChartData;
import com.tradeitsignals.datamodel.enums.charts.ChartIndicator;
import com.tradeitsignals.datamodel.enums.charts.ChartInterval;
import com.tradeitsignals.datamodel.enums.charts.ChartType;
import com.tradeitsignals.utils.Constants;

/**
 * Created by ThorneBird on 2/24/2016.
 */
public class ChartDataDAO extends BaseDAO<ChartData>{

    public final static String TABLE_NAME="chart_dashboard";

    public final static String KEY_ID = "_id";
    public final static String KEY_ASSET_NAME="assetName";
    public final static String KEY_INTERVAL="interval";
    public final static String KEY_INDICATOR= "indicator";
    public final static String KEY_CHART_TYPE="chartType";


    public final static String CREATE_CHART_TABLE="CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_ASSET_NAME + " TEXT , "
            + KEY_INTERVAL + " TEXT, "
            + KEY_INDICATOR + " TEXT, "
            + KEY_CHART_TYPE + " TEXT) ;";

    private static ChartDataDAO instance;

    public static ChartDataDAO getInstance(){
        if(instance==null){
            instance=new ChartDataDAO();
        }
        return instance;
    }

    @Override
    protected ContentValues extractContentValues(ChartData chartData) {
        ContentValues values=new ContentValues();
        values.put(KEY_ASSET_NAME, chartData.getAssetName());
        values.put(KEY_INTERVAL, chartData.getIntervalCode());
        values.put(KEY_INDICATOR, chartData.getIndicatorCode());
        values.put(KEY_CHART_TYPE, chartData.getChartTypeCode());
        return values;
    }

    @Override
    protected ChartData getObject(Cursor c) {
        ChartData chartData =new ChartData();
        chartData.setAssetName(c.getString(c.getColumnIndex(KEY_ASSET_NAME)));
        int what = Constants.VAR_CODE;
        chartData.setChartType(ChartType.findBy(what, c.getString(c.getColumnIndex(KEY_CHART_TYPE))));
        chartData.setInterval(ChartInterval.findBy(what, c.getString(c.getColumnIndex(KEY_INTERVAL))));
        chartData.setIndicator(ChartIndicator.findBy(what, c.getString(c.getColumnIndex(KEY_INDICATOR))));
        return chartData;
    }

    @Override
    protected String getIdKey() {
        return KEY_ASSET_NAME;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

}



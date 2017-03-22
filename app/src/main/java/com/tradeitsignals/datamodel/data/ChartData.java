package com.tradeitsignals.datamodel.data;

import com.tradeitsignals.datamodel.DataModelObject;
import com.tradeitsignals.datamodel.enums.charts.ChartIndicator;
import com.tradeitsignals.datamodel.enums.charts.ChartInterval;
import com.tradeitsignals.datamodel.enums.charts.ChartType;

/**
 * Created by ThorneBird on 2/27/2016.
 */
public class ChartData implements DataModelObject {

    private String assetName;
    private ChartInterval interval;
    private ChartType chartType;
    private ChartIndicator indicator;

    public ChartData(){}

    public ChartData(String assetName, ChartInterval interval, ChartType chartType, ChartIndicator indicator) {
        this.assetName = assetName;
        this.interval = interval;
        this.chartType = chartType;
        this.indicator = indicator;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getIntervalCode() {
        return interval.getIntervalCode();
    }

    public String getIntervalDisplay() {
        return interval.getDisplay();
    }

    public void setInterval(ChartInterval interval) {
        this.interval=interval;
    }

    public String getChartTypeCode() {
        return chartType.getChartTypeCode();
    }

    public String getChartTypeDisplay() {
        return chartType.getDisplay();
    }

    public void setChartType(ChartType chartType) {
        this.chartType = chartType;
    }

    public String getIndicatorCode() {
        return indicator.getIndicatorCode();
    }

    public String getIndicatorDisplay() {
        return indicator.getDisplay();
    }

    public void setIndicator(ChartIndicator indicator) {
        this.indicator = indicator;
    }

    @Override
    public String getId() {
        return assetName;
    }

    @Override
    public String toString() {
        return "ChartTypeData{" +
                "assetName='" + assetName + '\'' +
                ", interval='" + interval + '\'' +
                ", chartType='" + chartType + '\'' +
                ", indicator='" + indicator + '\'' +
                '}';
    }
}

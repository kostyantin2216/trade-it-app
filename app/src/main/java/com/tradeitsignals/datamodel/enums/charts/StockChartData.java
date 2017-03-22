package com.tradeitsignals.datamodel.enums.charts;

import com.tradeitsignals.datamodel.data.AssetDataChart;
import com.tradeitsignals.datamodel.enums.AssetType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThorneBird on 2/17/2016.
 */
public enum StockChartData implements ChartDataHolder {

    GOOGLE("Google","NASDAQ:GOOGL"),//
    TWITTER("Twitter","NYSE:TWTR"),//
    APPLE("Apple","NASDAQ:AAPL"),//
    BAIDU("Baidu","NASDAQ:BIDU"),//
    ALIBABA("Alibaba","NYSE:BABA"),//
    AMAZON("Amazon","NASDAQ:AMZN"),//
    BLACKBERY("Blackberry","NASDAQ:BBRY"),//
    BOEING("Boeing","NYSE:BA"),//
    CATERPILLER("Caterpillar","NYSE:CAT"),//
    CITIGROUP("CitiGroup","NYSE:C"),//
    COCACOLA("Coca-Cola","NASDAQ:COKE"),//
    DISNEY("Disney","NYSE:DIS"),//
    EBAY("Ebay","NASDAQ:EBAY"),//
    FACEBOOK("Facebook","NASDAQ:FB"),//
    FERARRI("Ferarri","CHXEUR:RACEM"),
    GENERALMOTORS("G&M","NYSE:GM"),//
    GROUPON("Groupon","NASDAQ:GRPN"),//
    IBM("IBM","NYSE:IBM");//


    private final  String stockName;
    private final  String stockCode;

    StockChartData(String stockName, String stockCode){
        this.stockName=stockName;
        this.stockCode=stockCode;
    }

    public String getStockName(){ return stockName; }

    public String getStockCode(){ return stockCode; }

    @Override
    public AssetDataChart toChartData() {
        return new AssetDataChart(AssetType.STOCK, stockName, stockCode);
    }

    public static List<AssetDataChart> makeAssetData() {
        List<AssetDataChart> assetDataList = new ArrayList<>();
        for (StockChartData stockChartData : values()) {
            assetDataList.add(stockChartData.toChartData());
        }
        return assetDataList;
    }

}

package com.tradeitsignals.datamodel.enums.charts;

import com.tradeitsignals.datamodel.data.AssetDataChart;
import com.tradeitsignals.datamodel.enums.AssetType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThorneBird on 2/15/2016.
 */
public enum CurrencyChartData implements ChartDataHolder {

    USDJPY("USD/JPY","FX:USDJPY"),
    USDCAD("USD/CAD","FX:USDCAD"),
    USDCHF("USD/CHF","FX:USDCHF"),
    USDHKD("USD/HKD","FX:USDHKD"),
    USDSEK("USD/SEK","FX:USDSEK"),
    USDTRY("USD/TRY","FX:USDTRY"),
    USDMXN("USD/MXN","FX:USDMXN"),
    USDZAR("USD/ZAR","FX:USDZAR"),
    USDCNH("USD/CNH","FX:USDCNH"),
    USDNOK("USD/NOK","FX:USDNOK"),
    CADCHF("CAD/CHF","FX:CADCHF"),
    CADNZD("CAD/NZD","FX_IDC:CADNZD"),
    EURCAD("EUR/CAD","FX:EURCAD"),
    EURJPY("EUR/JPY","FX:EURJPY"),
    EURNOK("EUR/NOK","FX:EURNOK"),
    EURUSD("EUR/USD","FX:EURUSD"),
    EURAUD("EUR/AUD","FX:EURAUD"),
    EURTRY("EUR/TRY","FX:EURTRY"),
    EURGBP("EUR/GBP","FX:EURGBP"),
    EURSEK("EUR/SEK","FX:EURSEK"),
    EURNZD("EUR/NZD","FX:EURNZD"),
    EURCHF("EUR/CHF","FX:EURCHF"),
    GBPUSD("GBP/USD","FX:GBPUSD"),
    GBPCHF("GBP/CHF","FX:GBPCHF"),
    GBPAUD("GBP/AUD","FX:GBPAUD"),
    GBPCAD("GBP/CAD","FX:GBPAUD"),
    GBPJPY("GBP/JPY","FX:GBPJPY"),
    GBPNZD("GBP/NZD","FX:GBPNZD"),
    AUDCAD("AUD/CAD","FX:AUDCAD"),
    AUDCHF("AUD/CHF","FX:AUDCHF"),
    AUDGBP("AUD/GBP","FX:AUDGBP"),
    AUDNZD("AUD/NZD","FX:AUDNZD"),
    AUDJPY("AUD/JPY","FX:AUDJPY"),
    AUDUSD("AUD/USD","FX:AUDUSD"),
    NZDCAD("NZD/CAD","FX:NZDCAD"),
    NZDCHF("NZD/CHF","FX:NZDCHF"),
    NZDUSD("NZD/USD","FX:NZDUSD"),
    CHFJPY("CHF/JPY","FX:CHFJPY"),
    CADJPY("CAD/JPY","FX:CADJPY"),
    TRYJPY("TRY/JPY","FX:TRYJPY"),
    NZDJPY("NZD/JPY","FX:NZDJPY"),
    ZARJPY("ZAR/JPY","FX:ZARJPY");



    private final  String type;
    private final String name;

    CurrencyChartData(String name, String code){
        this.type=code;
        this.name=name;
    }

    public String getCode(){
        return  type;
    }
    public String getCurrencyName(){return  name;}

    @Override
    public AssetDataChart toChartData() {
        return new AssetDataChart(AssetType.CURRENCY, name, type);
    }

    public static List<AssetDataChart> makeAssetData() {
        List<AssetDataChart> assetDataList = new ArrayList<>();
        for (CurrencyChartData currencyChartData : values()) {
            assetDataList.add(currencyChartData.toChartData());
        }
        return assetDataList;
    }

    public static CurrencyChartData findByName(String currencyName) {
        for(CurrencyChartData chartEnum : values()) {
            if(chartEnum.name.equals(currencyName)) {
                return chartEnum;
            }
        }
        return null;
    }

}

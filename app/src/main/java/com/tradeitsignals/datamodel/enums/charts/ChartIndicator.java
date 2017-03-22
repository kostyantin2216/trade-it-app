package com.tradeitsignals.datamodel.enums.charts;

import com.tradeitsignals.helpers.TIDictionary;
import com.tradeitsignals.logging.TILogger;
import com.tradeitsignals.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThorneBird on 2/22/2016.
 */
public enum ChartIndicator {

    BOLLINGERBANDS(TIDictionary.translate("bollinger_bands"), "BB@tv-basicstudies"),
    MACD(TIDictionary.translate("macd"), "MACD@tv-basicstudies"),
    ELLIOTTWAVE(TIDictionary.translate("elliott_wave"), "ElliottWave@tv-basicstudies"),
    STOCHASTIC(TIDictionary.translate("stochastic"), "Stochastic@tv-basicstudies"),
    RSI(TIDictionary.translate("rsi"), "RSI@tv-basicstudies"),
    MOVING_AVERAGES(TIDictionary.translate("moving_averages"), "MASimple@tv-basicstudies"),
    PIVOT_POINTS_HIGH_LOW(TIDictionary.translate("pivot_point_h_l"), "PivotPointsHighLow@tv-basicstudies"),
    ACCUMULATION_DISCTIRUBTION(TIDictionary.translate("accumulation_disctribution"), "ACCD@tv-basicstudies"),
    MONEY_FLOW(TIDictionary.translate("money_flow"), "MF@tv-basicstudies"),
    VOLUME(TIDictionary.translate("volume"), "Volume@tv-basicstudies");

    private final String indicatorCode;
    private final String display;

    ChartIndicator(String name, String indicatorCode) {
        this.indicatorCode = indicatorCode;
        this.display = name;
    }

    public String getIndicatorCode() {
        return indicatorCode;
    }

    public String getDisplay() {
        return display;
    }

    /**
     * @param what One of the constants that starts with VAR in Constants class
     */
    public static ChartIndicator findBy(int what, String value) {
        for(ChartIndicator indicator : values()) {
            String trueValue;
            switch (what) {
                case Constants.VAR_CODE:
                    trueValue = indicator.indicatorCode;
                    break;
                case Constants.VAR_DISPLAY:
                    trueValue = indicator.display;
                    break;
                default:
                    TILogger.getLog().w(ChartIndicator.class.getName()
                            + " does not have a variable with Constants.VAR_... value "
                            + what);
                    return null;
            }

            if(trueValue.equals(value)) {
                return indicator;
            }
        }
        return null;
    }

    public static List<String> toDisplayList() {
        List<String> display = new ArrayList<>();
        for(ChartIndicator indicator : values()) {
            display.add(indicator.display);
        }
        return display;
    }
}








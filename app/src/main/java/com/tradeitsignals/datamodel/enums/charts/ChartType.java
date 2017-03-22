package com.tradeitsignals.datamodel.enums.charts;

import com.tradeitsignals.helpers.TIDictionary;
import com.tradeitsignals.logging.TILogger;
import com.tradeitsignals.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThorneBird on 2/29/2016.
 */
public enum ChartType {

    CANDLE(TIDictionary.translate("candles"), "1"),
    BARS(TIDictionary.translate("bars"), "0"),
    AREA(TIDictionary.translate("area"), "3"),
    LINE(TIDictionary.translate("line"), "2"),
    HOLLOW(TIDictionary.translate("hollow"), "9"),
    HEIKIN_ASHI(TIDictionary.translate("heikin_ashi"), "8");

    private final String display;
    private final String chartTypeCode;

    ChartType(String display, String chartTypeCode) {
        this.display = display;
        this.chartTypeCode = chartTypeCode;
    }

    public String getChartTypeCode() {
        return chartTypeCode;
    }

    public String getDisplay() {
        return display;
    }

    public static ChartType findBy(int what, String value) {
        for(ChartType chartType : values()) {
            String trueValue;
            switch (what) {
                case Constants.VAR_CODE:
                    trueValue = chartType.chartTypeCode;
                    break;
                case Constants.VAR_DISPLAY:
                    trueValue = chartType.display;
                    break;
                default:
                    TILogger.getLog().w(ChartInterval.class.getName()
                            + " does not have a variable with Constants.VAR_... value "
                            + what);
                    return null;
            }

            if(trueValue.equals(value)) {
                return chartType;
            }
        }
        return null;
    }

    public static List<String> toDisplayList() {
        List<String> display = new ArrayList<>();
        for(ChartType type : values()) {
            display.add(type.display);
        }
        return display;
    }
}

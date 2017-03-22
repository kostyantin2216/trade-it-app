package com.tradeitsignals.datamodel.enums.charts;

import com.tradeitsignals.helpers.TIDictionary;
import com.tradeitsignals.logging.TILogger;
import com.tradeitsignals.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThorneBird on 2/29/2016.
 */
public enum ChartInterval {

    ONE_MINUTE(TIDictionary.translate("interval_one"),"1"),
    THREE_MINUTES(TIDictionary.translate("interval_three"),"3"),
    FIVE_MINUTES(TIDictionary.translate("interval_five"),"5"),
    FIFTEEN_MINUTES(TIDictionary.translate("interval_fifteen"),"15"),
    THIRTY_MINUTES(TIDictionary.translate("interval_thirty"),"30"),
    ONE_HOUR(TIDictionary.translate("interval_hour"),"60"),
    TWO_HOURS(TIDictionary.translate("interval_two_hour"),"120"),
    THREE_HOURS(TIDictionary.translate("interval_three_hour"),"180"),
    FOUR_HOURS(TIDictionary.translate("interval_four_hour"),"240"),
    DAILY(TIDictionary.translate("interval_daily"),"D"),
    WEEKLY(TIDictionary.translate("interval_weekly"),"W");

    private final String intervalCode;
    private final String display;

    ChartInterval(String display, String intervalCode){
        this.display = display;
        this.intervalCode = intervalCode;
    }

    public String getIntervalCode() {
        return intervalCode;
    }

    public String getDisplay(){
        return display;
    }

    public static ChartInterval findBy(int what, String value) {
        for(ChartInterval interval : values()) {
            String trueValue;
            switch (what) {
                case Constants.VAR_CODE:
                    trueValue = interval.intervalCode;
                    break;
                case Constants.VAR_DISPLAY:
                    trueValue = interval.display;
                    break;
                default:
                    TILogger.getLog().w(ChartInterval.class.getName()
                            + " does not have a variable with Constants.VAR_... value "
                            + what);
                    return null;
            }

            if(trueValue.equals(value)) {
                return interval;
            }
        }
        return null;
    }

    public static List<String> toDisplayList() {
        List<String> display = new ArrayList<>();
        for(ChartInterval interval : values()) {
            display.add(interval.display);
        }
        return display;
    }

}

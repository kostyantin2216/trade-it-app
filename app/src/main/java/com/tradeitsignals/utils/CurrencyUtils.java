package com.tradeitsignals.utils;

import com.tradeitsignals.R;
import com.tradeitsignals.datamodel.data.Currency;

/**
 * Created by Kostyantin on 7/15/2015.
 */
public class CurrencyUtils {

    public final static String DEFAULT_CURRENCY = "USD";

    public final static String[] CURRENCIES_USED = {"AUD", "CAD", "CHF", "CNH", "EUR", "GBP", "HKD",
            "JPY", "MXN","NOK", "NZD", "SEK", "TRY", "USD", "ZAR"};

    public final static String[] CURRENCY_PAIRS = {"AUD/CAD", "CHF/JPY", "EUR/NZD", "GBP/JPY",
            "TRY/JPY", "USD/NOK", "AUD/CHF", "EUR/AUD", "EUR/SEK", "GBP/NZD", "USD/CAD", "USD/SEK",
            "AUD/JPY", "EUR/CAD", "EUR/TRY", "GBP/USD", "USD/CHF", "USD/TRY", "AUD/NZD", "EUR/CHF",
            "EUR/USD", "NZD/CAD", "USD/CNH", "USD/ZAR", "AUD/USD", "EUR/GBP", "GBP/AUD", "NZD/CHF",
            "USD/HKD", "ZAR/JPY", "CAD/CHF", "EUR/JPY", "GBP/CAD", "NZD/JPY", "USD/JPY", "CAD/JPY",
            "EUR/NOK", "GBP/CHF", "NZD/USD", "USD/MXN"};

    public static int getFlagIdForCurrency(String isoCode) {
        if(isoCode.equals(Currency.AUD)) {
            return R.drawable.ic_aud;
        } else if(isoCode.equals(Currency.CAD)) {

        } else if(isoCode.equals(Currency.CHF)) {

        } else if(isoCode.equals(Currency.CNH)) {

        } else if(isoCode.equals(Currency.EUR)) {

        } else if(isoCode.equals(Currency.GBP)) {

        } else if(isoCode.equals(Currency.HKD)) {

        } else if(isoCode.equals(Currency.JPY)) {

        } else if(isoCode.equals(Currency.MXN)) {

        } else if(isoCode.equals(Currency.NOK)) {

        } else if(isoCode.equals(Currency.NZD)) {

        } else if(isoCode.equals(Currency.SEK)) {

        } else if(isoCode.equals(Currency.TRY)) {

        } else if(isoCode.equals(Currency.USD)) {

        } else if(isoCode.equals(Currency.ZAR)) {

        }
        return 0;
    }
    
}

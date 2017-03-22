package com.tradeitsignals.datamodel.data;

import java.util.Arrays;

/**
 * Created by Kostyantin on 9/11/2016.
 */
public class UBinaryOption {

    private String Symbol;
    private UBinaryDeal[] Deals;

    public UBinaryOption() { }

    public UBinaryOption(String symbol, UBinaryDeal[] deals) {
        Symbol = symbol;
        Deals = deals;
    }

    public String getSymbol() {
        return Symbol;
    }

    public void setSymbol(String symbol) {
        Symbol = symbol;
    }

    public UBinaryDeal[] getDeals() {
        return Deals;
    }

    public void setDeals(UBinaryDeal[] deals) {
        Deals = deals;
    }

    @Override
    public String toString() {
        return "UBinaryOption{" +
                "Symbol='" + Symbol + '\'' +
                ", Deals=" + Arrays.toString(Deals) +
                '}';
    }
}

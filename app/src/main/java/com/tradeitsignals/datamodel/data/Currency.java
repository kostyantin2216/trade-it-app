package com.tradeitsignals.datamodel.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.tradeitsignals.datamodel.DataModelObject;

/**
 * Created by Kostyantin on 7/15/2015.
 */
public class Currency implements Parcelable, DataModelObject {

    public final static String AUD = "AUD";
    public final static String CAD = "CAD";
    public final static String CHF = "CHF";
    public final static String CNH = "CNH";
    public final static String EUR = "EUR";
    public final static String GBP = "GBP";
    public final static String HKD = "HKD";
    public final static String JPY = "JPY";
    public final static String MXN = "MXN";
    public final static String NOK = "NOK";
    public final static String NZD = "NZD";
    public final static String SEK = "SEK";
    public final static String TRY = "TRY";
    public final static String USD = "USD";
    public final static String ZAR = "ZAR";

    private String id;
    private String country;
    private String isoCode;
    private String description;
    private double rate;
    private int flagResId;
    private long lastUpdated;

    public Currency(String id, String country, String isoCode, String description, double rate, int flagResId, long lastUpdated) {
        this.id = id;
        this.country = country;
        this.isoCode = isoCode;
        this.description = description;
        this.rate = rate;
        this.flagResId = flagResId;
        this.lastUpdated = lastUpdated;
    }

    public Currency(String country, String isoCode, String description, double rate, int flagResId, long lastUpdated) {
        this.country = country;
        this.isoCode = isoCode;
        this.description = description;
        this.rate = rate;
        this.flagResId = flagResId;
        this.lastUpdated = lastUpdated;
    }

    public Currency() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getFlagResId() {
        return flagResId;
    }

    public void setFlagResId(int flagResId) {
        this.flagResId = flagResId;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return new StringBuilder(15).append("Currency [id = ").append(id).append(", country = ").append(country)
                .append(", isoCode = ").append(isoCode).append(", description = ").append(description)
                .append(", rate = ").append(rate).append(", flagResId = ").append(flagResId)
                .append(", lastUpdated = ").append(lastUpdated).append("]").toString();
    }

    // ===========================================================================
    // ||					  	  Parcelable Methods						    ||
    // ===========================================================================

    public final static Creator<Currency> CREATOR = new Creator<Currency>() {

        @Override
        public Currency createFromParcel(Parcel source) {
            return new Currency(source);
        }

        @Override
        public Currency[] newArray(int size) {
            return new Currency[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(country);
        dest.writeString(isoCode);
        dest.writeString(description);
        dest.writeDouble(rate);
        dest.writeInt(flagResId);
        dest.writeLong(lastUpdated);
    }

    public Currency(Parcel in) {
        this.id = in.readString();
        this.country = in.readString();
        this.isoCode = in.readString();
        this.description = in.readString();
        this.rate = in.readDouble();
        this.flagResId = in.readInt();
        this.lastUpdated = in.readLong();
    }

}

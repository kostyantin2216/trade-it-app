package com.tradeitsignals.datamodel.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.tradeitsignals.ui.adapters.CommonRecyclerItem;
import com.tradeitsignals.datamodel.enums.AssetType;

/**
 * Created by ThorneBird on 2/13/2016.
 */
public class AssetDataChart implements Parcelable, CommonRecyclerItem {

    private AssetType assetType;
    private String assetName;
    private double assetRate;
    private String country;
    private String assetCode;

    public AssetDataChart(){}

    public AssetDataChart(AssetType assetType, String assetName, String assetCode){
        this.assetType=assetType;
        this.assetName=assetName;
        this.assetCode=assetCode;
    }

    public AssetType getAssetType() {
        return assetType;
    }

    public void setAssetType(AssetType assetType) {
        this.assetType = assetType;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public double getAssetRate() {
        return assetRate;
    }

    public void setAssetRate(double assetRate) {
        this.assetRate = assetRate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    // ===========================================================================
    // ||					  	  Parcelable Methods						    ||
    // ===========================================================================

    public final static Creator<AssetDataChart> CREATOR = new Creator<AssetDataChart>() {

        @Override
        public AssetDataChart createFromParcel(Parcel source) {
            return new AssetDataChart(source);
        }

        @Override
        public AssetDataChart[] newArray(int size) {
            return new AssetDataChart[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(assetType.name());
        dest.writeString(assetName);
        dest.writeString(assetCode);
        dest.writeDouble(assetRate);
        dest.writeString(country);
    }

    public AssetDataChart(Parcel in) {
        assetType = AssetType.valueOf(in.readString());
        assetName = in.readString();
        assetCode = in.readString();
        assetRate = in.readDouble();
        country = in.readString();
    }

}

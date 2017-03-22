package com.tradeitsignals.datamodel.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.tradeitsignals.ui.adapters.CommonRecyclerItem;
import com.tradeitsignals.datamodel.DataModelObject;

import java.util.Date;

/**
 * Created by Kostyantin on 6/14/2015.
 */
public class MarketReview implements Parcelable, DataModelObject, CommonRecyclerItem {

    private Integer id;
    private String title;
    private String content;
    private Date createdAt;
    private String imageUrl;

    public MarketReview() { }

    public MarketReview(String title, String content, Date createdAt, String imageUrl) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.imageUrl = imageUrl;
    }

    public MarketReview(Integer id, String title, String content, Date createdAt, String imageUrl) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.imageUrl = imageUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFileNamePrefix() {
        String title = this.title.replace(" ", "");
        if(title.length() > 12) {
            return title.substring(0, 12).toLowerCase();
        } else {
            return title.toLowerCase();
        }
    }

    public String getImageFileName() {
        String[] logoUrlArr = getImageUrl().split("\\.");
        String suffix = "jpg";
        if(logoUrlArr.length > 0) {
            suffix = logoUrlArr[logoUrlArr.length - 1];
        }
        return getFileNamePrefix() + "." + suffix;
    }

    @Override
    public String toString() {
        return new StringBuilder(11).append("MarketReview [id = ").append(id).append(", title = ")
                .append(title).append(", content = ")
                .append(content.length() > 20 ? content.substring(0, 20).concat("...") : content)
                .append(", createdAt = ").append(createdAt).append(", imageUrl = ").append(imageUrl)
                .append("]").toString();
    }

    // ===========================================================================
    // ||					  	  Parcelable Methods						    ||
    // ===========================================================================

    public final static Creator<MarketReview> CREATOR = new Creator<MarketReview>() {

        @Override
        public MarketReview createFromParcel(Parcel source) {
            return new MarketReview(source);
        }

        @Override
        public MarketReview[] newArray(int size) {
            return new MarketReview[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(imageUrl);
        dest.writeLong(createdAt.getTime());
    }

    public MarketReview(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.content = in.readString();
        this.imageUrl = in.readString();
        this.createdAt = new Date(in.readLong());
    }
}
package com.tradeitsignals.datamodel.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.tradeitsignals.ui.adapters.CommonRecyclerItem;

/**
 * Created by ThorneBird on 3/28/2016.
 */
public class ArticleData implements Parcelable, CommonRecyclerItem {

    private String title;
    private String description;
    private String link;
    private String pubDate;

    public ArticleData() {
    }

 /*   public ArticleData(String title, String description, String url) {
        this.title = title;
        this.description = description;
        this.url = url;
    }*/

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }


    // ===========================================================================
    // ||					  	  Parcelable Methods						    ||
    // ===========================================================================

    public final static Creator<ArticleData> CREATOR = new Creator<ArticleData>() {
        @Override
        public ArticleData createFromParcel(Parcel source) {
            return new ArticleData(source);
        }

        @Override
        public ArticleData[] newArray(int size) {
            return new ArticleData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(link);
        dest.writeString(pubDate);
    }


    public ArticleData(Parcel in) {
        title = in.readString();
        description = in.readString();
        link = in.readString();
        pubDate = in.readString();
    }

}

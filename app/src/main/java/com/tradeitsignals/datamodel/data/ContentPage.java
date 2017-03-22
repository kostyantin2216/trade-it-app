package com.tradeitsignals.datamodel.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.tradeitsignals.ui.adapters.CommonRecyclerItem;
import com.tradeitsignals.datamodel.DataModelObject;
import com.tradeitsignals.datamodel.enums.ContentPageType;

import java.util.Date;

/**
 * Created by Kostyantin on 8/18/2015.
 */
public class ContentPage implements Parcelable, DataModelObject, CommonRecyclerItem {

    private Integer id;
    private ContentPageType type;
    private String title;
    private String description;
    private String androidIconResId;
    private String templateFileName;
    private String htmlContent;
    private Date createdAt;
    private Date updatedAt;

    public ContentPage(Integer id, ContentPageType type, String title, String description, String templateFileName, String htmlContent,
                       String androidIconResId, Date createdAt, Date updatedAt) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.description = description;
        this.templateFileName = templateFileName;
        this.htmlContent = htmlContent;
        this.androidIconResId = androidIconResId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public ContentPage(ContentPageType type, String title, String description, String templateFileName, String htmlContent,
                       String androidIconResId, Date createdAt, Date updatedAt) {
        this.type = type;
        this.title = title;
        this.description = description;
        this.templateFileName = templateFileName;
        this.htmlContent = htmlContent;
        this.androidIconResId = androidIconResId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public ContentPage() { }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ContentPageType getType() {
        return type;
    }

    public void setType(ContentPageType type) {
        this.type = type;
    }

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

    public String getAndroidIconResId() {
        return androidIconResId;
    }

    public void setAndroidIconResId(String androidIconResId) {
        this.androidIconResId = androidIconResId;
    }

    public String getTemplateFileName() {
        return templateFileName;
    }

    public void setTemplateFileName(String templateFileName) {
        this.templateFileName = templateFileName;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }
    
    public Date getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return new StringBuilder(15).append("ContentPage [id = ").append(id).append(", type = ").append(type)
                .append(", title = ").append(title).append(", description = ").append(description)
                .append(", androidIconResId = ").append(androidIconResId).append(", assestsFileName = ").append(templateFileName)
                .append(", createdAtTimeStamp = ").append(createdAt)
                .append(", updatedAt = ").append(updatedAt)
                .append(", htmlContent = ").append(htmlContent.length() > 20 ? htmlContent.substring(0, 20).concat("...") : htmlContent).append("]").toString();
    }

    // ===========================================================================
    // ||					  	  Parcelable Methods						    ||
    // ===========================================================================

    public final static Creator<ContentPage> CREATOR = new Creator<ContentPage>() {

        @Override
        public ContentPage createFromParcel(Parcel source) {
            return new ContentPage(source);
        }

        @Override
        public ContentPage[] newArray(int size) {
            return new ContentPage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(type.id());
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(androidIconResId);
        dest.writeString(templateFileName);
        dest.writeString(htmlContent);
        dest.writeLong(createdAt.getTime());
        dest.writeLong(updatedAt.getTime());
    }

    public ContentPage(Parcel in) {
        this.id = in.readInt();
        this.type = ContentPageType.findById(in.readInt());
        this.title = in.readString();
        this.description = in.readString();
        this.androidIconResId = in.readString();
        this.templateFileName = in.readString();
        this.htmlContent = in.readString();
        this.createdAt = new Date(in.readLong());
        this.updatedAt = new Date(in.readLong());
    }
}

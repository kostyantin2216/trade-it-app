package com.tradeitsignals.datamodel.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.tradeitsignals.ui.adapters.CommonRecyclerItem;
import com.tradeitsignals.datamodel.DataModelObject;

import java.util.Date;

public class Broker implements Parcelable, DataModelObject, CommonRecyclerItem {

	private Integer id;
	private String name;
	private String description;
	private String logoUrl;
	private int minDeposit;
	private int minWithdrawal;
	private String promotion;
	private float rating;
	private Date updatedAt;
	private String url;
	private boolean isRecommended;
	private boolean isActive;

	public Broker() { }

	public Broker(String name, String description, String logoUrl, int minDeposit, int minWithdrawal,
				  String promotion, float rating, Date updatedAt, String url, boolean isRecommended, boolean isActive) {
		this.name = name;
		this.description = description;
		this.logoUrl = logoUrl;
		this.minDeposit = minDeposit;
		this.minWithdrawal = minWithdrawal;
		this.promotion = promotion;
		this.rating = rating;
		this.updatedAt = updatedAt;
		this.url = url;
		this.isRecommended = isRecommended;
		this.isActive = isActive;
	}

	public Broker(Integer id, String name, String description, String logoUrl, int minDeposit, int minWithdrawal,
				  String promotion, float rating, Date updatedAt, String url, boolean isRecommended, boolean isActive) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.logoUrl = logoUrl;
		this.minDeposit = minDeposit;
		this.minWithdrawal = minWithdrawal;
		this.promotion = promotion;
		this.rating = rating;
		this.updatedAt = updatedAt;
		this.url = url;
		this.isRecommended = isRecommended;
		this.isActive = isActive;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public int getMinDeposit() {
		return minDeposit;
	}

	public void setMinDeposit(int minDeposit) {
		this.minDeposit = minDeposit;
	}

	public int getMinWithdrawal() {
		return minWithdrawal;
	}

	public void setMinWithdrawal(int minWithdrawal) {
		this.minWithdrawal = minWithdrawal;
	}

	public String getPromotion() {
		return promotion;
	}

	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isRecommended() {
		return isRecommended;
	}

	public void setRecommended(boolean isRecommended) {
		this.isRecommended = isRecommended;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getFileNamePrefix() {
		return name.toLowerCase().replace(" ", "_");
	}

	public String getRegistrationFileName() {
		return "registration_" + getFileNamePrefix() + ".html";
	}

	public String getLogoFileName() {
		String[] logoUrlArr = getLogoUrl().split("\\.");
		String suffix = "jpg";
		if(logoUrlArr.length > 0) {
			suffix = logoUrlArr[logoUrlArr.length - 1];
		}
		return getFileNamePrefix() + "." + suffix;
	}

	@Override
	public String toString() {
		return "Broker{" +
				"id=" + id +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", logoUrl='" + logoUrl + '\'' +
				", minDeposit=" + minDeposit +
				", minWithdrawal=" + minWithdrawal +
				", promotion='" + promotion + '\'' +
				", rating=" + rating +
				", updatedAt=" + updatedAt +
				", url='" + url + '\'' +
				", isRecommended=" + isRecommended +
				'}';
	}

	// ===========================================================================
	// ||					  	  Parcelable Methods						    ||
	// ===========================================================================

	public final static Creator<Broker> CREATOR = new Creator<Broker>() {

		@Override
		public Broker createFromParcel(Parcel source) {
			return new Broker(source);
		}

		@Override
		public Broker[] newArray(int size) {
			return new Broker[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(name);
		dest.writeString(description);
		dest.writeString(logoUrl);
		dest.writeInt(minDeposit);
		dest.writeInt(minWithdrawal);
		dest.writeString(promotion);
		dest.writeFloat(rating);
		dest.writeLong(updatedAt.getTime());
		dest.writeString(url);
	}

	public Broker(Parcel in) {
		id = in.readInt();
		name = in.readString();
		description = in.readString();
		logoUrl = in.readString();
		minDeposit = in.readInt();
		minWithdrawal = in.readInt();
		promotion = in.readString();
		rating = in.readFloat();
		updatedAt = new Date(in.readLong());
		url = in.readString();
	}

}

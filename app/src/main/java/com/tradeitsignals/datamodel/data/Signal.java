package com.tradeitsignals.datamodel.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.tradeitsignals.ui.adapters.CommonRecyclerItem;
import com.tradeitsignals.datamodel.DataModelObject;
import com.tradeitsignals.datamodel.enums.SignalStatus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Signal implements Parcelable, DataModelObject, CommonRecyclerItem {

	private Integer id;
	private Date createdAt;
	private Date expiryTime;
	private String asset;
	private double entryRate;
	private double expiryRate;
	private boolean isCall;
	private SignalStatus status;

	public Signal() { }

	public Signal(Integer id, Date createdAt, Date expiryTime, String asset, double entryRate, double expiryRate, boolean isCall, SignalStatus status) {
		this.id = id;
		this.createdAt = createdAt;
		this.expiryTime = expiryTime;
		this.asset = asset;
		this.entryRate = entryRate;
		this.expiryRate = expiryRate;
		this.isCall = isCall;
		this.status = status;
	}

	public Signal(Date createdAt, Date expiryTime, String asset, double entryRate, double expiryRate, boolean isCall, SignalStatus status) {
		this.createdAt = createdAt;
		this.expiryTime = expiryTime;
		this.asset = asset;
		this.entryRate = entryRate;
		this.expiryRate = expiryRate;
		this.isCall = isCall;
		this.status = status;
	}

	/**
	 * Use for creating new signals
	 */
	public Signal(Date expiryTime, String asset, double entryRate, boolean isCall) {
		this.createdAt = new Date();
		this.expiryTime = expiryTime;
		this.asset = asset;
		this.entryRate = entryRate;
		this.isCall = isCall;
		this.status = SignalStatus.ONGOING;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(Date expiryTime) {
		this.expiryTime = expiryTime;
	}

	public String getAsset() {
		return asset;
	}

	public void setAsset(String asset) {
		this.asset = asset;
	}

	public double getEntryRate() {
		return entryRate;
	}

	public void setEntryRate(double entryRate) {
		this.entryRate = entryRate;
	}

	public double getExpiryRate() {
		return expiryRate;
	}

	public void setExpiryRate(double expiryRate) {
		this.expiryRate = expiryRate;
	}

	public boolean isCall() {
		return isCall;
	}

	public void setIsCall(boolean isCall) {
		this.isCall = isCall;
	}

	public SignalStatus getStatus() {
		return status;
	}

	public void setStatus(SignalStatus status) {
		this.status = status;
	}

	public void setStatus(int status) {
		this.status = SignalStatus.values()[status];
	}

	public boolean isExpired() {
		Date now = new Date();
		if(now.after(expiryTime)) {
			return true;
		} else {
			return false;
		}
	}

	public long getMillisLeft() {
		return expiryTime.getTime() - System.currentTimeMillis();
	}

	public Long getMinutesLeft() {
		return getMillisLeft() / 1000 / 60;
	}

	public JSONObject toJSONParseSignal() throws JSONException {
		JSONObject jsonObject = new JSONObject();

		jsonObject.putOpt("objectId", id);
		jsonObject.putOpt("createdAt", createdAt.getTime());
		jsonObject.putOpt("expiryTime", expiryTime.getTime());
		jsonObject.putOpt("currencyPair", asset);
		jsonObject.putOpt("rate", entryRate);
		jsonObject.putOpt("isCall", isCall);

		return jsonObject;
	}

	@Override
	public String toString() {
		return "Signal{" +
				"id=" + id +
				", createdAt=" + createdAt +
				", expiryTime=" + expiryTime +
				", asset='" + asset + '\'' +
				", entryRate=" + entryRate +
				", expiryRate=" + expiryRate +
				", isCall=" + isCall +
				", status=" + status +
				'}';
	}

	// ===========================================================================
	// ||					  	  Parcelable Methods						    ||
	// ===========================================================================

	public final static Creator<Signal> CREATOR = new Creator<Signal>() {

		@Override
		public Signal createFromParcel(Parcel source) {
			return new Signal(source);
		}

		@Override
		public Signal[] newArray(int size) {
			return new Signal[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(asset);
		dest.writeLong(createdAt.getTime());
		dest.writeLong(expiryTime.getTime());
		dest.writeDouble(entryRate);
		dest.writeInt(isCall ? 1 : 0);
		dest.writeInt(status.ordinal());
	}

	public Signal(Parcel in) {
		this.id = in.readInt();
		this.asset = in.readString();
		this.createdAt = new Date(in.readLong());
		this.expiryTime = new Date(in.readLong());
		this.entryRate = in.readDouble();
		this.isCall = in.readInt() == 1;
		this.status = SignalStatus.values[in.readInt()];
	}

}

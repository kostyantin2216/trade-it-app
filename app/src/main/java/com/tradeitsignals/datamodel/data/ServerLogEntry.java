package com.tradeitsignals.datamodel.data;

import com.tradeitsignals.datamodel.DataModelObject;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kostyantin on 2/13/2016.
 */
public class ServerLogEntry implements DataModelObject {

    private Integer id;
    private Integer userId;
    private String tag;
    private String message;
    private String stackTrace;
    private String deviceInfo;
    private String versionInfo;
    private Date createdAt;

    public ServerLogEntry() { }

    public ServerLogEntry(Integer userId, String tag, String message, String stackTrace,
                          String deviceInfo, String versionInfo, Date createdAt) {
        this.userId = userId;
        this.tag = tag;
        this.message = message;
        this.stackTrace = stackTrace;
        this.deviceInfo = deviceInfo;
        this.versionInfo = versionInfo;
        this.createdAt = createdAt;
    }

    public ServerLogEntry(Integer id, Integer userId, String tag, String message, String stackTrace,
                          String deviceInfo, String versionInfo, Date createdAt) {
        this.id = id;
        this.userId = userId;
        this.tag = tag;
        this.message = message;
        this.stackTrace = stackTrace;
        this.deviceInfo = deviceInfo;
        this.versionInfo = versionInfo;
        this.createdAt = createdAt;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getVersionInfo() {
        return versionInfo;
    }

    public void setVersionInfo(String versionInfo) {
        this.versionInfo = versionInfo;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "ServerLogEntry{" +
                "id=" + id +
                ", userId=" + userId +
                ", tag='" + tag + '\'' +
                ", message='" + message + '\'' +
                ", stackTrace='" + stackTrace + '\'' +
                ", deviceInfo='" + deviceInfo + '\'' +
                ", versionInfo='" + versionInfo + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

}

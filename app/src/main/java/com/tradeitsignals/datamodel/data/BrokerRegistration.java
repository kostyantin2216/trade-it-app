package com.tradeitsignals.datamodel.data;

import com.tradeitsignals.datamodel.DataModelObject;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kostyantin on 10/10/2015.
 */
public class BrokerRegistration implements DataModelObject {

    private Integer id;
    private Integer brokerId;
    private Integer userId;
    private String userBrokerId;
    private String message;
    private Boolean isSuccess;
    private String ipAddress;

    public BrokerRegistration(Integer userId, Integer brokerId, String userBrokerId, String message, boolean isSuccess, String ipAddress) {
        this.userId = userId;
        this.brokerId = brokerId;
        this.userBrokerId = userBrokerId;
        this.message = message;
        this.isSuccess = isSuccess;
        this.ipAddress = ipAddress;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(Integer brokerId) {
        this.brokerId = brokerId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserBrokerId() {
        return userBrokerId;
    }

    public void setUserBrokerId(String userBrokerId) {
        this.userBrokerId = userBrokerId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString() {
        return "BrokerRegistration{" +
                "id=" + id +
                ", brokerId=" + brokerId +
                ", userId=" + userId +
                ", userBrokerId='" + userBrokerId + '\'' +
                ", message='" + message + '\'' +
                ", isSuccess=" + isSuccess +
                ", ipAddress='" + ipAddress + '\'' +
                '}';
    }

}

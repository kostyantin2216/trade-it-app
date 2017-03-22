package com.tradeitsignals.rest.responses;

import com.tradeitsignals.datamodel.data.BrokerRegistration;
import com.tradeitsignals.datamodel.data.User;

import java.util.Arrays;

/**
 * Created by Kostyantin on 10/13/2016.
 */
public class UserRegistrationResponse {

    private User user;
    private String ip;
    private BrokerRegistration[] brokerRegistrationDetails;

    public UserRegistrationResponse(User user, String ip, BrokerRegistration[] brokerRegistrationDetails) {
        this.user = user;
        this.ip = ip;
        this.brokerRegistrationDetails = brokerRegistrationDetails;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public BrokerRegistration[] getBrokerRegistrationDetails() {
        return brokerRegistrationDetails;
    }

    public void setBrokerRegistrationDetails(BrokerRegistration[] brokerRegistrationDetails) {
        this.brokerRegistrationDetails = brokerRegistrationDetails;
    }

    @Override
    public String toString() {
        return "UserRegistrationResponse{" +
                "user=" + user +
                ", ip='" + ip + '\'' +
                ", brokerRegistrationDetails=" + Arrays.toString(brokerRegistrationDetails) +
                '}';
    }
}

package com.tradeitsignals.rest.requests;

import com.tradeitsignals.datamodel.data.User;

import java.util.Arrays;

/**
 * Created by Kostyantin on 4/7/2016.
 *
 *
 *  Use {@link UserRegistrationRequest}
 */
@Deprecated
public class BrokerLeadRegistrationRequest {

    private User user;
    private Integer[] companyIds;

    public BrokerLeadRegistrationRequest() {	}

    public BrokerLeadRegistrationRequest(User user, Integer[] companyIds) {
        super();
        this.user = user;
        this.companyIds = companyIds;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer[] getCompanyIds() {
        return companyIds;
    }

    public void setCompanyIds(Integer[] companyIds) {
        this.companyIds = companyIds;
    }

    @Override
    public String toString() {
        return "LeadRegistrationRequest [user=" + user + ", companyIds=" + Arrays.toString(companyIds) + "]";
    }

}

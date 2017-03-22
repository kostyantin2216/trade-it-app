package com.tradeitsignals.rest.requests;

import com.tradeitsignals.datamodel.data.User;

/**
 * Created by Kostyantin on 10/13/2016.
 */
public class UserRegistrationRequest {

    private User user;
    private String comment;
    private String campaign;
    private Boolean registerWithBrokers;

    public UserRegistrationRequest() { }

    public UserRegistrationRequest(User user, String comment, String campaign, Boolean registerWithBrokers) {
        this.user = user;
        this.comment = comment;
        this.campaign = campaign;
        this.registerWithBrokers = registerWithBrokers;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCampaign() {
        return campaign;
    }

    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }

    public Boolean getRegisterWithBrokers() {
        return registerWithBrokers;
    }

    public void setRegisterWithBrokers(Boolean registerWithBrokers) {
        this.registerWithBrokers = registerWithBrokers;
    }

    @Override
    public String toString() {
        return "LeadRegistrationRequest{" +
                "user=" + user +
                ", comment='" + comment + '\'' +
                ", campaign='" + campaign + '\'' +
                ", registerWithBrokers=" + registerWithBrokers +
                '}';
    }
}

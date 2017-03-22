package com.tradeitsignals.rest.requests;

import com.tradeitsignals.datamodel.data.User;

/**
 * Created by Kostyantin on 4/13/2016.
 */
public class UBinaryLoginRequest {

    private String UserEmail;               // user's email
    private String UserPassword;            // user's password
    private String RedirectTo;              // optional - redirect user to this URL
                                            //      URL should be an abolute URL
                                            //      URL should be within the same domain

    public UBinaryLoginRequest() { }

    public UBinaryLoginRequest(User user) {
        this.UserEmail = user.getEmail();
        this.UserPassword = user.getPassword();
    }

    public UBinaryLoginRequest(String userEmail, String userPassword, String redirectTo) {
        UserEmail = userEmail;
        UserPassword = userPassword;
        RedirectTo = redirectTo;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

    public String getRedirectTo() {
        return RedirectTo;
    }

    public void setRedirectTo(String redirectTo) {
        RedirectTo = redirectTo;
    }

    @Override
    public String toString() {
        return "UBinaryLoginRequest{" +
                "UserEmail='" + UserEmail + '\'' +
                ", UserPassword='" + UserPassword + '\'' +
                ", RedirectTo='" + RedirectTo + '\'' +
                '}';
    }

}

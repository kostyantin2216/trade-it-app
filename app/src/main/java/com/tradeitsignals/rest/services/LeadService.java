package com.tradeitsignals.rest.services;

import com.tradeitsignals.datamodel.data.BrokerRegistration;
import com.tradeitsignals.datamodel.data.User;
import com.tradeitsignals.rest.api.LeadAPI;
import com.tradeitsignals.rest.requests.BrokerLeadRegistrationRequest;
import com.tradeitsignals.rest.requests.UserRegistrationRequest;
import com.tradeitsignals.rest.responses.UserRegistrationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Kostyantin on 4/6/2016.
 */
public interface LeadService {

    @POST(LeadAPI.API_NAME + "/register")
    Call<BrokerRegistration[]> registerWithBrokers(@Body BrokerLeadRegistrationRequest registrationRequest);

    @POST(LeadAPI.API_NAME + "/registerUser")
    Call<UserRegistrationResponse> registerUser(@Body UserRegistrationRequest request);


}

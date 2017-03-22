package com.tradeitsignals.rest.api;

import com.tradeitsignals.datamodel.data.BrokerRegistration;
import com.tradeitsignals.datamodel.data.User;
import com.tradeitsignals.rest.RetrofitAPI;
import com.tradeitsignals.rest.requests.BrokerLeadRegistrationRequest;
import com.tradeitsignals.rest.requests.UserRegistrationRequest;
import com.tradeitsignals.rest.responses.UserRegistrationResponse;
import com.tradeitsignals.rest.services.LeadService;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Kostyantin on 4/7/2016.
 */
public class LeadAPI implements RetrofitAPI {

    public final static String API_NAME = "Leads";

    private LeadService service = APIFactory.getInstance().getLeadService();

    protected LeadAPI() { }

    public void registerWithBrokers(User user, Integer[] brokerIds, Callback<BrokerRegistration[]> callback) {
        registerWithBrokers(user, brokerIds).enqueue(callback);
    }

    public Call<BrokerRegistration[]> registerWithBrokers(User user, Integer[] brokerIds) {
        BrokerLeadRegistrationRequest request = new BrokerLeadRegistrationRequest(user, brokerIds);
        return service.registerWithBrokers(request);
    }

    public void registerUser(User user, Callback<UserRegistrationResponse> callback) {
        service.registerUser(new UserRegistrationRequest(user, "", "", true)).enqueue(callback);
    }

}

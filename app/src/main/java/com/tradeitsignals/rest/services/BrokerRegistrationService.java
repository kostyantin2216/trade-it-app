package com.tradeitsignals.rest.services;

import com.tradeitsignals.datamodel.data.BrokerRegistration;
import com.tradeitsignals.rest.api.BrokerRegistrationAPI;
import com.tradeitsignals.rest.queries.QueryRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Kostyantin on 2/7/2016.
 */
public interface BrokerRegistrationService {

    @GET(BrokerRegistrationAPI.API_NAME + "/{id}")
    Call<BrokerRegistration> find(@Path("id") Integer id);

    @GET(BrokerRegistrationAPI.API_NAME)
    Call<List<BrokerRegistration>> findAll();

    @POST(BrokerRegistrationAPI.API_NAME)
    Call<BrokerRegistration> create(@Body BrokerRegistration brokerRegistration);

    @PUT(BrokerRegistrationAPI.API_NAME)
    Call<BrokerRegistration> update(@Body BrokerRegistration brokerRegistration);

    @DELETE(BrokerRegistrationAPI.API_NAME + "/{id}")
    Call<BrokerRegistration> delete(@Path("id") Integer id);

    @POST(BrokerRegistrationAPI.API_NAME + "/query")
    Call<List<BrokerRegistration>> query(@Body QueryRequest req);

}

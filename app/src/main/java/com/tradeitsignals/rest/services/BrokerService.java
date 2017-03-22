package com.tradeitsignals.rest.services;

import com.tradeitsignals.datamodel.data.Broker;
import com.tradeitsignals.rest.queries.QueryRequest;
import com.tradeitsignals.rest.queries.ServiceQuery;
import com.tradeitsignals.rest.api.BrokerAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Kostyantin on 12/12/2015.
 */
public interface BrokerService {

    @GET(BrokerAPI.API_NAME + "/{id}")
    Call<Broker> find(@Path("id") Integer id);

    @GET(BrokerAPI.API_NAME)
    Call<List<Broker>> findAll();

    @POST(BrokerAPI.API_NAME)
    Call<Broker> create(@Body Broker signal);

    @PUT(BrokerAPI.API_NAME)
    Call<Broker> update(@Body Broker signal);

    @DELETE(BrokerAPI.API_NAME + "/{id}")
    Call<Broker> delete(@Path("id") Integer id);

    @POST(BrokerAPI.API_NAME + "/query")
    Call<List<Broker>> query(@Body QueryRequest req);

}

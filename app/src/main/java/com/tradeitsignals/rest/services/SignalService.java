package com.tradeitsignals.rest.services;

import com.tradeitsignals.datamodel.data.Signal;
import com.tradeitsignals.rest.queries.QueryRequest;
import com.tradeitsignals.rest.queries.ServiceQuery;
import com.tradeitsignals.rest.api.SignalAPI;

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
public interface SignalService {

    @GET(SignalAPI.API_NAME + "/{id}")
    Call<Signal> find(@Path("id") Integer id);

    @GET(SignalAPI.API_NAME)
    Call<List<Signal>> findAll();

    @POST(SignalAPI.API_NAME)
    Call<Signal> create(@Body Signal signal);

    @PUT(SignalAPI.API_NAME)
    Call<Signal> update(@Body Signal signal);

    @DELETE(SignalAPI.API_NAME + "/{id}")
    Call<Signal> delete(@Path("id") Integer id);

    @POST(SignalAPI.API_NAME + "/query")
    Call<List<Signal>> query(@Body QueryRequest req);

}

package com.tradeitsignals.rest.services;

import com.tradeitsignals.datamodel.data.Country;
import com.tradeitsignals.rest.queries.QueryRequest;
import com.tradeitsignals.rest.queries.ServiceQuery;
import com.tradeitsignals.rest.api.CountryAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Kostyantin on 1/7/2016.
 */
public interface CountryService {

    @GET(CountryAPI.API_NAME + "/{id}")
    Call<Country> find(@Path("id") Integer id);

    @GET(CountryAPI.API_NAME)
    Call<List<Country>> findAll();

    @POST(CountryAPI.API_NAME)
    Call<Country> create(@Body Country country);

    @PUT(CountryAPI.API_NAME)
    Call<Country> update(@Body Country country);

    @DELETE(CountryAPI.API_NAME + "/{id}")
    Call<Country> delete(@Path("id") Integer id);

    @POST(CountryAPI.API_NAME + "/query")
    Call<List<Country>> query(@Body QueryRequest req);
}

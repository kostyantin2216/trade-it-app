package com.tradeitsignals.rest.services;

import com.tradeitsignals.datamodel.data.User;
import com.tradeitsignals.rest.queries.QueryRequest;
import com.tradeitsignals.rest.queries.ServiceQuery;
import com.tradeitsignals.rest.api.UserAPI;

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
public interface UserService {

    @GET(UserAPI.API_NAME + "/{id}")
    Call<User> find(@Path("id") Integer id);

    @GET(UserAPI.API_NAME)
    Call<List<User>> findAll();

    @POST(UserAPI.API_NAME)
    Call<User> create(@Body User user);

    @PUT(UserAPI.API_NAME)
    Call<User> update(@Body User user);

    @DELETE(UserAPI.API_NAME + "/{id}")
    Call<User> delete(@Path("id") Integer id);

    @POST(UserAPI.API_NAME + "/query")
    Call<List<User>> query(@Body QueryRequest req);

    @POST(UserAPI.API_NAME + "/register")
    Call<User> register(@Body User user);

}

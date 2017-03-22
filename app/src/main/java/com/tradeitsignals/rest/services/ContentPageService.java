package com.tradeitsignals.rest.services;

import com.tradeitsignals.datamodel.data.ContentPage;
import com.tradeitsignals.rest.queries.QueryRequest;
import com.tradeitsignals.rest.queries.ServiceQuery;
import com.tradeitsignals.rest.api.ContentPageAPI;

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
public interface ContentPageService {

    @GET(ContentPageAPI.API_NAME + "/{id}")
    Call<ContentPage> find(@Path("id") Integer id);

    @GET(ContentPageAPI.API_NAME)
    Call<List<ContentPage>> findAll();

    @POST(ContentPageAPI.API_NAME)
    Call<ContentPage> create(@Body ContentPage signal);

    @PUT(ContentPageAPI.API_NAME)
    Call<ContentPage> update(@Body ContentPage signal);

    @DELETE(ContentPageAPI.API_NAME + "/{id}")
    Call<ContentPage> delete(@Path("id") Integer id);

    @POST(ContentPageAPI.API_NAME + "/query")
    Call<List<ContentPage>> query(@Body QueryRequest req);

}

package com.tradeitsignals.rest.services;

import com.tradeitsignals.datamodel.data.MarketReview;
import com.tradeitsignals.rest.queries.QueryRequest;
import com.tradeitsignals.rest.queries.ServiceQuery;
import com.tradeitsignals.rest.api.MarketReviewAPI;

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
public interface MarketReviewService {

    @GET(MarketReviewAPI.API_NAME + "/{id}")
    Call<MarketReview> find(@Path("id") Integer id);

    @GET(MarketReviewAPI.API_NAME)
    Call<List<MarketReview>> findAll();

    @POST(MarketReviewAPI.API_NAME)
    Call<MarketReview> create(@Body MarketReview signal);

    @PUT(MarketReviewAPI.API_NAME)
    Call<MarketReview> update(@Body MarketReview signal);

    @DELETE(MarketReviewAPI.API_NAME + "/{id}")
    Call<MarketReview> delete(@Path("id") Integer id);

    @POST(MarketReviewAPI.API_NAME + "/query")
    Call<List<MarketReview>> query(@Body QueryRequest req);

}

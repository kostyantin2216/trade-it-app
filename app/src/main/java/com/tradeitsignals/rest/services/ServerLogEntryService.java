package com.tradeitsignals.rest.services;

import com.tradeitsignals.datamodel.data.ServerLogEntry;
import com.tradeitsignals.rest.api.ServerLogEntryAPI;
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
 * Created by Kostyantin on 2/13/2016.
 */
public interface ServerLogEntryService {
    @GET(ServerLogEntryAPI.API_NAME + "/{id}")
    Call<ServerLogEntry> find(@Path("id") Integer id);

    @GET(ServerLogEntryAPI.API_NAME)
    Call<List<ServerLogEntry>> findAll();

    @POST(ServerLogEntryAPI.API_NAME)
    Call<ServerLogEntry> create(@Body ServerLogEntry logEntry);

    @PUT(ServerLogEntryAPI.API_NAME)
    Call<ServerLogEntry> update(@Body ServerLogEntry logEntry);

    @DELETE(ServerLogEntryAPI.API_NAME + "/{id}")
    Call<ServerLogEntry> delete(@Path("id") Integer id);

    @POST(ServerLogEntryAPI.API_NAME + "/query")
    Call<List<ServerLogEntry>> query(@Body QueryRequest req);
}

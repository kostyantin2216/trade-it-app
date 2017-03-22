package com.tradeitsignals.rest.api;

import com.tradeitsignals.datamodel.data.ServerLogEntry;
import com.tradeitsignals.rest.ServerAPI;
import com.tradeitsignals.rest.queries.QueryRequest;
import com.tradeitsignals.rest.queries.ServiceQuery;
import com.tradeitsignals.rest.services.ServerLogEntryService;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;

/**
 * Created by Kostyantin on 2/13/2016.
 */
public class ServerLogEntryAPI implements ServerAPI<ServerLogEntry> {

    public final static String API_NAME = "Logs";

    private ServerLogEntryService service = APIFactory.getInstance().getServerLogEntryService();

    protected ServerLogEntryAPI() { }

    @Override
    public Call<ServerLogEntry> find(Integer id) {
        return service.find(id);
    }

    @Override
    public Call<List<ServerLogEntry>> findAll() {
        return service.findAll();
    }

    @Override
    public Call<ServerLogEntry> post(ServerLogEntry obj) {
        return service.create(obj);
    }

    @Override
    public Call<ServerLogEntry> update(ServerLogEntry obj) {
        return service.update(obj);
    }

    @Override
    public Call<ServerLogEntry> delete(Integer id) {
        return service.delete(id);
    }

    @Override
    public Call<List<ServerLogEntry>> query(ServiceQuery query) {
        QueryRequest request = new QueryRequest(query);
        return service.query(request);
    }

    @Override
    public Call<List<ServerLogEntry>> query(List<ServiceQuery> queries) {
        QueryRequest request = new QueryRequest(queries.toArray(new ServiceQuery[queries.size()]));
        return service.query(request);
    }

    @Override
    public Call<List<ServerLogEntry>> query(QueryRequest request) {
        return service.query(request);
    }

    @Override
    public String getApiName() {
        return API_NAME;
    }
}

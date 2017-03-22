package com.tradeitsignals.rest.api;

import com.tradeitsignals.datamodel.data.Signal;
import com.tradeitsignals.rest.ServerAPI;
import com.tradeitsignals.rest.queries.QueryRequest;
import com.tradeitsignals.rest.queries.ServiceQuery;
import com.tradeitsignals.rest.services.SignalService;

import java.util.List;

import retrofit2.Call;

/**
 * Created by Kostyantin on 1/7/2016.
 */
public class SignalAPI implements ServerAPI<Signal> {

    public final static String API_NAME = "Signals";

    private SignalService service = APIFactory.getInstance().getSignalService();

    protected SignalAPI() { }

    @Override
    public Call<Signal> find(Integer id) {
        return service.find(id);
    }

    @Override
    public Call<List<Signal>> findAll() {
        return service.findAll();
    }

    @Override
    public Call<Signal> post(Signal obj) {
        return service.create(obj);
    }

    @Override
    public Call<Signal> update(Signal obj) {
        return service.update(obj);
    }

    @Override
    public Call<Signal> delete(Integer id) {
        return service.delete(id);
    }

    @Override
    public Call<List<Signal>> query(ServiceQuery query) {
        QueryRequest request = new QueryRequest(query);
        return service.query(request);
    }

    @Override
    public Call<List<Signal>> query(List<ServiceQuery> queries) {
        QueryRequest request = new QueryRequest(queries.toArray(new ServiceQuery[queries.size()]));
        return service.query(request);
    }

    @Override
    public Call<List<Signal>> query(QueryRequest request) {
        return service.query(request);
    }

    @Override
    public String getApiName() {
        return API_NAME;
    }

}

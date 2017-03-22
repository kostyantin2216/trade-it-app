package com.tradeitsignals.rest.api;

import com.tradeitsignals.datamodel.data.Broker;
import com.tradeitsignals.rest.ServerAPI;
import com.tradeitsignals.rest.queries.QueryRequest;
import com.tradeitsignals.rest.queries.ServiceQuery;
import com.tradeitsignals.rest.services.BrokerService;

import java.util.List;

import retrofit2.Call;

/**
 * Created by Kostyantin on 1/7/2016.
 */
public class BrokerAPI implements ServerAPI<Broker> {

    public final static String API_NAME = "Brokers";

    private BrokerService service = APIFactory.getInstance().getBrokerService();

    protected BrokerAPI() { }

    @Override
    public Call<Broker> find(Integer id) {
        return service.find(id);
    }

    @Override
    public Call<List<Broker>> findAll() {
        return service.findAll();
    }

    @Override
    public Call<Broker> post(Broker obj) {
        return service.create(obj);
    }

    @Override
    public Call<Broker> update(Broker obj) {
        return service.update(obj);
    }

    @Override
    public Call<Broker> delete(Integer id) {
        return service.delete(id);
    }

    @Override
    public Call<List<Broker>> query(ServiceQuery query) {
        QueryRequest request = new QueryRequest(query);
        return service.query(request);
    }

    @Override
    public Call<List<Broker>> query(List<ServiceQuery> queries) {
        return service.query(new QueryRequest(queries.toArray(new ServiceQuery[queries.size()])));
    }

    @Override
    public Call<List<Broker>> query(QueryRequest request) {
        return service.query(request);
    }

    @Override
    public String getApiName() {
        return API_NAME;
    }

}

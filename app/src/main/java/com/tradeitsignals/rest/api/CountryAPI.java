package com.tradeitsignals.rest.api;

import com.tradeitsignals.datamodel.data.Country;
import com.tradeitsignals.rest.ServerAPI;
import com.tradeitsignals.rest.queries.QueryRequest;
import com.tradeitsignals.rest.queries.ServiceQuery;
import com.tradeitsignals.rest.services.CountryService;

import java.util.List;

import retrofit2.Call;

/**
 * Created by Kostyantin on 1/7/2016.
 */
public class CountryAPI implements ServerAPI<Country> {

    public final static String API_NAME = "Countries";

    private CountryService service = APIFactory.getInstance().getCountryService();

    protected CountryAPI() { }

    @Override
    public Call<Country> find(Integer id) {
        return service.find(id);
    }

    @Override
    public Call<List<Country>> findAll() {
        return service.findAll();
    }

    @Override
    public Call<Country> post(Country obj) {
        return service.create(obj);
    }

    @Override
    public Call<Country> update(Country obj) {
        return service.update(obj);
    }

    @Override
    public Call<Country> delete(Integer id) {
        return service.delete(id);
    }

    @Override
    public Call<List<Country>> query(ServiceQuery query) {
        QueryRequest request = new QueryRequest(query);
        return service.query(request);
    }

    @Override
    public Call<List<Country>> query(List<ServiceQuery> queries) {
        QueryRequest request = new QueryRequest(queries.toArray(new ServiceQuery[queries.size()]));
        return service.query(request);
    }

    @Override
    public Call<List<Country>> query(QueryRequest request) {
        return service.query(request);
    }

    @Override
    public String getApiName() {
        return API_NAME;
    }

}

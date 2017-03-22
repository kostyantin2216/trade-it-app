package com.tradeitsignals.rest.api;

import com.tradeitsignals.datamodel.data.ContentPage;
import com.tradeitsignals.rest.ServerAPI;
import com.tradeitsignals.rest.queries.QueryRequest;
import com.tradeitsignals.rest.queries.ServiceQuery;
import com.tradeitsignals.rest.services.ContentPageService;

import java.util.List;

import retrofit2.Call;

/**
 * Created by Kostyantin on 1/7/2016.
 */
public class ContentPageAPI implements ServerAPI<ContentPage> {

    public final static String  API_NAME = "ContentPages";

    private ContentPageService service = APIFactory.getInstance().getContentPageService();

    protected ContentPageAPI() { }

    @Override
    public Call<ContentPage> find(Integer id) {
        return service.find(id);
    }

    @Override
    public Call<List<ContentPage>> findAll() {
        return service.findAll();
    }

    @Override
    public Call<ContentPage> post(ContentPage obj) {
        return service.create(obj);
    }

    @Override
    public Call<ContentPage> update(ContentPage obj) {
        return service.update(obj);
    }

    @Override
    public Call<ContentPage> delete(Integer id) {
        return service.delete(id);
    }

    @Override
    public Call<List<ContentPage>> query(ServiceQuery query) {
        QueryRequest request = new QueryRequest(query);
        return service.query(request);
    }

    @Override
    public Call<List<ContentPage>> query(List<ServiceQuery> queries) {
        QueryRequest request = new QueryRequest(queries.toArray(new ServiceQuery[queries.size()]));
        return service.query(request);
    }

    @Override
    public Call<List<ContentPage>> query(QueryRequest request) {
        return service.query(request);
    }

    @Override
    public String getApiName() {
        return API_NAME;
    }

}

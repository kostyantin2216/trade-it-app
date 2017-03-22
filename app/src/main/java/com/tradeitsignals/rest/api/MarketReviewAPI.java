package com.tradeitsignals.rest.api;

import com.tradeitsignals.datamodel.data.MarketReview;
import com.tradeitsignals.rest.ServerAPI;
import com.tradeitsignals.rest.queries.QueryRequest;
import com.tradeitsignals.rest.queries.ServiceQuery;
import com.tradeitsignals.rest.services.MarketReviewService;

import java.util.List;

import retrofit2.Call;

/**
 * Created by Kostyantin on 1/7/2016.
 */
public class MarketReviewAPI implements ServerAPI<MarketReview> {

    public final static String API_NAME = "MarketReviews";

    private MarketReviewService service = APIFactory.getInstance().getMarketReviewService();

    protected MarketReviewAPI() { }

    @Override
    public Call<MarketReview> find(Integer id) {
        return service.find(id);
    }

    @Override
    public Call<List<MarketReview>> findAll() {
        return service.findAll();
    }

    @Override
    public Call<MarketReview> post(MarketReview obj) {
        return service.create(obj);
    }

    @Override
    public Call<MarketReview> update(MarketReview obj) {
        return service.update(obj);
    }

    @Override
    public Call<MarketReview> delete(Integer id) {
        return service.delete(id);
    }

    @Override
    public Call<List<MarketReview>> query(ServiceQuery query) {
        QueryRequest request = new QueryRequest(query);
        return service.query(request);
    }

    @Override
    public Call<List<MarketReview>> query(List<ServiceQuery> queries) {
        QueryRequest request = new QueryRequest(queries.toArray(new ServiceQuery[queries.size()]));
        return service.query(request);
    }

    @Override
    public Call<List<MarketReview>> query(QueryRequest request) {
        return service.query(request);
    }

    @Override
    public String getApiName() {
        return API_NAME;
    }

}

package com.tradeitsignals.rest;

import com.tradeitsignals.datamodel.DataModelObject;
import com.tradeitsignals.rest.queries.QueryRequest;
import com.tradeitsignals.rest.queries.ServiceQuery;

import java.util.List;

import retrofit2.Call;

/**
 * Created by Kostyantin on 1/7/2016.
 */
public interface ServerAPI<E extends DataModelObject> extends RetrofitAPI {
    Call<E> find(Integer id);
    Call<List<E>> findAll();
    Call<E> post(E obj);
    Call<E> update(E obj);
    Call<E> delete(Integer id);
    Call<List<E>> query(ServiceQuery query);
    Call<List<E>> query(List<ServiceQuery> queries);
    Call<List<E>> query(QueryRequest request);
    String getApiName();
}

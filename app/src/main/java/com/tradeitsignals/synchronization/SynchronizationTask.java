package com.tradeitsignals.synchronization;

import com.tradeitsignals.datamodel.CommonDAO;
import com.tradeitsignals.datamodel.DataModelObject;
import com.tradeitsignals.rest.ServerAPI;
import com.tradeitsignals.rest.queries.QueryCriteria;

import java.util.List;

import retrofit2.Call;

/**
 * Created by Kostyantin on 1/8/2016.
 */
public class SynchronizationTask<E extends DataModelObject> {

    protected ServerAPI<E> api;
    protected CommonDAO<E> dao;
    protected SynchronizationOperation operation;
    protected QueryCriteria criteria;

    public SynchronizationTask(ServerAPI api, CommonDAO dao, SynchronizationOperation operation,
                               QueryCriteria criteria) {
        this.api = api;
        this.dao = dao;
        this.operation = operation;
        this.criteria = criteria;
    }

    public SynchronizationTask(ServerAPI api, CommonDAO dao, QueryCriteria criteria) {
        this(api, dao, SynchronizationOperation.INSERT_OR_UPDATE, criteria);
    }

    public SynchronizationTask(ServerAPI api, CommonDAO dao, SynchronizationOperation operation) {
        this(api, dao, operation, null);
    }

    public SynchronizationTask(ServerAPI api, CommonDAO dao) {
        this(api, dao, SynchronizationOperation.INSERT_OR_UPDATE, null);
    }

    public ServerAPI<E> getApi() {
        return api;
    }

    public void setApi(ServerAPI<E> api) {
        this.api = api;
    }

    public CommonDAO<E> getDao() {
        return dao;
    }

    public void setDao(CommonDAO<E> dao) {
        this.dao = dao;
    }

    public QueryCriteria getCriteria() {
        return criteria;
    }

    public void setCriteria(QueryCriteria criteria) {
        this.criteria = criteria;
    }

    public SynchronizationOperation getOperation() {
        return operation;
    }

    public void setOperation(SynchronizationOperation operation) {
        this.operation = operation;
    }

    public Call<List<E>> executeQuery() {
        if(criteria == null) {
            return null;
        }
        return api.query(criteria.build());
    }

    @Override
    public String toString() {
        return "SynchronizationTask{" +
                "api=" + api +
                ", dao=" + dao +
                ", operation=" + operation +
                ", criteria=" + criteria +
                '}';
    }

}

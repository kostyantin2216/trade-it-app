package com.tradeitsignals.rest.queries;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kostyantin on 1/15/2016.
 */
public class QueryRequest {
    private List<ServiceQuery> queries;

    public QueryRequest() { }

    public QueryRequest(ServiceQuery... queries) {
        this.queries = new ArrayList<>();
        for(ServiceQuery query : queries) {
            this.queries.add(query);
        }
    }

    public List<ServiceQuery> getQueries() {
        return queries;
    }

    public void setQueries(List<ServiceQuery> queries) {
        this.queries = queries;
    }
}

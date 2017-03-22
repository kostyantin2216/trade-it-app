package com.tradeitsignals.rest.queries;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Kostyantin on 1/14/2016.
 */
public class QueryCriteria {

    private List<ServiceQuery> queries;

    private QueryCriteria() {
        queries = new ArrayList<>();
    }

    public static QueryCriteria create() {
        return new QueryCriteria();
    }

    public static QueryCriteria create(ServiceQuery query) {
        return new QueryCriteria().add(query);
    }

    public QueryCriteria add(ServiceQuery... queries) {
        for(ServiceQuery query : queries) {
            this.queries.add(query);
        }
        return this;
    }

    public List<ServiceQuery> queries() {
        return queries;
    }

    public QueryRequest build() {
        return new QueryRequest(queries.toArray(new ServiceQuery[queries.size()]));
    }

    public void reset() {
        queries.clear();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(ServiceQuery query : queries) {
            sb.append(query.toString()).append(" AND ");
        }
        return sb.toString();
    }

}

package com.tradeitsignals.rest.api;

import com.tradeitsignals.datamodel.dao.UserDAO;
import com.tradeitsignals.datamodel.data.User;
import com.tradeitsignals.logging.TILogger;
import com.tradeitsignals.rest.ServerAPI;
import com.tradeitsignals.rest.queries.QueryRequest;
import com.tradeitsignals.rest.queries.ServiceQuery;
import com.tradeitsignals.rest.services.UserService;
import com.tradeitsignals.utils.CommonUtils;
import com.tradeitsignals.utils.PrefUtils;
import com.tradeitsignals.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Kostyantin on 1/7/2016.
 */
public class UserAPI implements ServerAPI<User> {

    public final static String API_NAME = "Users";

    private UserService service = APIFactory.getInstance().getUserService();

    protected UserAPI() { }

    @Override
    public Call<User> find(Integer id) {
        return service.find(id);
    }

    @Override
    @Deprecated
    public Call<List<User>> findAll() {
        return null;
    }

    @Override
    public Call<User> post(User obj) {
        return service.create(obj);
    }

    @Override
    public Call<User> update(User user) {
        if(user.getId() == null) {
            user.setId(PrefUtils.getUserId());
        }
        return service.update(user);
    }

    @Override
    public Call<User> delete(Integer id) {
        return service.delete(id);
    }

    @Override
    public Call<List<User>> query(ServiceQuery query) {
        QueryRequest request = new QueryRequest(query);
        return service.query(request);
    }

    @Override
    public Call<List<User>> query(List<ServiceQuery> queries) {
        QueryRequest request = new QueryRequest(queries.toArray(new ServiceQuery[queries.size()]));
        return service.query(request);
    }

    @Override
    public Call<List<User>> query(QueryRequest request) {
        return service.query(request);
    }


    public Call<List<User>> findSimilar(User user) {
        List<ServiceQuery> queries = new ArrayList<>();
        // TODO: create queries with user properties after checking they are not null.

        QueryRequest request = new QueryRequest(queries.toArray(new ServiceQuery[queries.size()]));
        return service.query(request);
    }

    @Override
    public String getApiName() {
        return API_NAME;
    }

}

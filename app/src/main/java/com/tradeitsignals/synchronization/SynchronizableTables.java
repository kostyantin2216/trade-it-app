package com.tradeitsignals.synchronization;

import com.tradeitsignals.datamodel.dao.BrokerDAO;
import com.tradeitsignals.datamodel.dao.ContentPageDAO;
import com.tradeitsignals.datamodel.dao.CountryDAO;
import com.tradeitsignals.datamodel.dao.MarketReviewDAO;
import com.tradeitsignals.datamodel.dao.SignalDAO;
import com.tradeitsignals.datamodel.dao.UserDAO;
import com.tradeitsignals.rest.api.APIFactory;
import com.tradeitsignals.rest.api.BrokerAPI;
import com.tradeitsignals.rest.api.ContentPageAPI;
import com.tradeitsignals.rest.api.CountryAPI;
import com.tradeitsignals.rest.api.MarketReviewAPI;
import com.tradeitsignals.rest.api.SignalAPI;
import com.tradeitsignals.rest.api.UserAPI;
import com.tradeitsignals.rest.queries.QueryCriteria;
import com.tradeitsignals.rest.queries.QueryRestrictions;
import com.tradeitsignals.rest.queries.ServiceQuery;
import com.tradeitsignals.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kostyantin on 2/1/2016.
 */
public enum SynchronizableTables {
    BROKER(BrokerDAO.TABLE_NAME, "updatedAt"),
    CONTENT_PAGE(ContentPageDAO.TABLE_NAME, "updatedAt"),
    COUNTRY(CountryDAO.TABLE_NAME, "updatedAt"),
    MARKET_REVIEW(MarketReviewDAO.TABLE_NAME, "updatedAt"),
    SIGNAL(SignalDAO.TABLE_NAME, "createdAt"),
    USER(UserDAO.TABLE_NAME, "updatedAt");

    public final String tableName;
    public final String timestampProperty;

    SynchronizableTables(String tableName, String timestampProperty) {
        this.tableName = tableName;
        this.timestampProperty = timestampProperty;
    }

    public static SynchronizableTables get(String tableName) {
        switch(tableName) {
            case BrokerDAO.TABLE_NAME:
                return BROKER;
            case ContentPageDAO.TABLE_NAME:
                return CONTENT_PAGE;
            case CountryDAO.TABLE_NAME:
                return COUNTRY;
            case MarketReviewDAO.TABLE_NAME:
                return MARKET_REVIEW;
            case SignalDAO.TABLE_NAME:
                return SIGNAL;
            case UserDAO.TABLE_NAME:
                return USER;
            default:
                return null;
        }
    }

    public static SynchronizationTask createTask(SynchronizableTables table) {
        if(table != null) {
            APIFactory factory = APIFactory.getInstance();
            switch (table) {
                case BROKER:
                    return new SynchronizationTask(factory.getBrokerApi(), BrokerDAO.getInstance());
                case CONTENT_PAGE:
                    return new SynchronizationTask(factory.getContentPageAPI(), ContentPageDAO.getInstance());
                case COUNTRY:
                    return new SynchronizationTask(factory.getCountryAPI(), CountryDAO.getInstance());
                case MARKET_REVIEW:
                    return new SynchronizationTask(factory.getMarketReviewAPI(), MarketReviewDAO.getInstance());
                case SIGNAL:
                    return new SynchronizationTask(factory.getSignalAPI(), SignalDAO.getInstance());
                case USER:
                    // Removing this will pull all users from server onto devices SQLite database.
                    int userId = PrefUtils.getUserId();
                    if(userId > 0) {
                        QueryCriteria criteria = QueryCriteria.create(QueryRestrictions.eq(UserDAO.KEY_ID, userId));
                        return new SynchronizationTask(factory.getUserAPI(), UserDAO.getInstance(), criteria);
                    }
            }
        }

        return null;
    }

    public static List<SynchronizationTask> createTasks(SynchronizableTables... tables) {
        List<SynchronizationTask> tasks = new ArrayList<>();
        for(SynchronizableTables table : tables) {
            SynchronizationTask task = createTask(table);
            if(task != null) {
                tasks.add(task);
            }
        }
        return tasks;
    }

}

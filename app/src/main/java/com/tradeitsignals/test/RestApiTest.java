package com.tradeitsignals.test;

import static com.tradeitsignals.rest.api.APIFactory.APITypes;

import android.os.AsyncTask;

import com.tradeitsignals.datamodel.DataModelObject;
import com.tradeitsignals.datamodel.data.Broker;
import com.tradeitsignals.datamodel.data.ContentPage;
import com.tradeitsignals.datamodel.data.Country;
import com.tradeitsignals.datamodel.data.MarketReview;
import com.tradeitsignals.datamodel.data.Signal;
import com.tradeitsignals.datamodel.data.User;
import com.tradeitsignals.datamodel.enums.SignalStatus;
import com.tradeitsignals.logging.TILogger;
import com.tradeitsignals.rest.api.APIFactory;
import com.tradeitsignals.rest.ServerAPI;
import com.tradeitsignals.rest.queries.QueryCriteria;
import com.tradeitsignals.rest.queries.QueryRestrictions;
import com.tradeitsignals.utils.DateUtils;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Kostyantin on 1/8/2016.
 */
public class RestApiTest {

    private final static TILogger LOG = TILogger.getLog();
    private final static String LOG_TAG = "#RESTSERVICE";

    private final static String LAST_UPDATE = "JAN-01-2016";

    public static void runTest() {
        new TestTask().execute();
    }

    private static class TestTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            APIFactory factory = APIFactory.getInstance();

            for(APITypes type : APITypes.values()) {
                LOG.i(LOG_TAG, " ======= TESTING " + type.name());
                try {
                    testApi(factory, type);
                    testQueries(factory, type);
                } catch(Exception e) {
                    LOG.e(LOG_TAG, "Error occured while testing api: " + type.name(), e);
                }
            }

            return null;
        }
    }

    public static void testQueries(APIFactory factory, APITypes type) {
        ServerAPI<DataModelObject> api = factory.getServerApi(type);
        Date lastUpdate = DateUtils.formatToDate(DateUtils.FORMAT_LOG_FILE_NAME, LAST_UPDATE);

        if(lastUpdate != null) {
            String property = type == APITypes.SIGNAL ? "createdAt" : "updatedAt";
            QueryCriteria crit = QueryCriteria.create()
                    .add(QueryRestrictions.gt(property, lastUpdate.getTime()));
            Call<List<DataModelObject>> call = api.query(crit.build());

            try {
                List<DataModelObject> dmoList = call.execute().body();
                TILogger.getLog().i("Query results for criteria " + crit.toString());
                if(dmoList != null) {
                    for (DataModelObject dmo : dmoList) {
                        LOG.i(dmo.toString());
                    }
                } else {
                    LOG.w("Results are null");
                }
            } catch (IOException e) {
                TILogger.getLog().e(LOG_TAG, "Error while retrieving query results");
            }

        } else {
            LOG.e("Could not parse " + LAST_UPDATE + " with format " + DateUtils.FORMAT_LOG_FILE_NAME);
        }
    }

    public static void testApi(APIFactory factory, APITypes type) {
        ServerAPI<DataModelObject> api = factory.getServerApi(type);
        DataModelObject dmo = getDmoForApi(type);

        Integer id = testInsert(api, dmo);
        testFindAll(api);

        dmo = modifyDmo(dmo, id, type);

        testUpdate(api, dmo);
        testFindAll(api);
        if(dmo.getId() != null) {
            testDelete(api, (Integer) dmo.getId());
            testFindById(api, (Integer) dmo.getId());
        } else {
            TILogger.getLog().e(LOG_TAG, "ERROR!!! DMO DOES NOT HAVE IN ID (" + dmo + ")");
        }
    }

    private static DataModelObject getDmoForApi(APITypes type) {
        switch(type) {
            case BROKER:
                return ObjectGenerator.generateBroker();
            case CONTENT_PAGE:
                return ObjectGenerator.generateContentPage();
            case COUNTRY:
                return ObjectGenerator.generateCountry();
            case MARKET_REVIEW:
                return ObjectGenerator.generateMarketReview();
            case SIGNAL:
                return ObjectGenerator.generateSignal();
            case USER:
                return ObjectGenerator.generateUser();
        }
        return null;
    }

    private static DataModelObject modifyDmo(DataModelObject dmo, Integer id, APITypes type) {
        switch (type) {
            case BROKER:
                Broker broker = (Broker) dmo;
                broker.setId(id);
                broker.setName("updated");
                broker.setDescription("update is working");
                return broker;
            case CONTENT_PAGE:
                ContentPage page = (ContentPage) dmo;
                page.setId(id);
                page.setTitle("updated");
                page.setDescription("update is working");
                return page;
            case COUNTRY:
                Country country = (Country) dmo;
                country.setId(id);
                country.setName("updated");
                country.setIso3("UPD");
                return country;
            case MARKET_REVIEW:
                MarketReview review = (MarketReview) dmo;
                review.setId(id);
                review.setTitle("updated");
                review.setContent("update is working");
                return review;
            case SIGNAL:
                Signal signal = (Signal) dmo;
                signal.setId(id);
                signal.setStatus(SignalStatus.WON);
                signal.setExpiryRate(100.12345);
                return signal;
            case USER:
                User user = (User) dmo;
                user.setId(id);
                user.setFirstName("updated");
                user.setLastName("update is working");
                return user;
        }
        return dmo;
    }

    public static void testFindById(ServerAPI<DataModelObject> api, final Integer id) {
        Call<DataModelObject> call = api.find(id);
        LOG.i(LOG_TAG, "finding from " + api.getApiName() + " item with id " + id);
        try {
            Response<DataModelObject> response = call.execute();
            if(response != null) {
                LOG.i(LOG_TAG, "found: " + response.body());
            } else {
                LOG.e(LOG_TAG, "Response is null...");
            }
        } catch (IOException e) {
            LOG.e(LOG_TAG, "failure while looking for item with id " + id, e);
        }
    }

    public static void testFindAll(final ServerAPI<DataModelObject> api) {
        Call<List<DataModelObject>> call = api.findAll();
        LOG.i(LOG_TAG, "finding all items from " + api.getApiName());
        try {
            Response<List<DataModelObject>> response = call.execute();
            if(response != null) {
                List<DataModelObject> items = response.body();
                if(items != null) {
                    LOG.i(LOG_TAG, "found " + items.size() + " items:");
                    for (DataModelObject item : response.body()) {
                        LOG.i(LOG_TAG, item.toString());
                    }
                } else {
                    LOG.e(LOG_TAG, "Response body is null...");
                }
            } else {
                LOG.e(LOG_TAG, "Response is null...");
            }
        } catch (IOException e) {
            LOG.e(LOG_TAG, "failure while looking for all items from " + api.getApiName(), e);
        }
    }

    public static Integer testInsert(ServerAPI<DataModelObject> api, DataModelObject item) {
        Call<DataModelObject> call = api.post(item);
        LOG.i(LOG_TAG, "inserting item into " + api.getApiName());
        try {
            Response<DataModelObject> response = call.execute();
            if(response != null) {
                LOG.i(LOG_TAG, "insert message: " + response.message());
                LOG.i(LOG_TAG, "insert body: " + response.body());
                if(response.body() != null) {
                    return (Integer) response.body().getId();
                }
            } else {
                LOG.e(LOG_TAG, "Response is null...");
            }
        } catch (IOException e) {
            LOG.e(LOG_TAG, "failure trying to insert item " + item, e);
        }

        return null;
    }

    public static void testUpdate(ServerAPI<DataModelObject> api, final DataModelObject item) {
        Call<DataModelObject> call = api.update(item);
        LOG.i(LOG_TAG, "updating item into " + api.getApiName());
        try {
            Response<DataModelObject> response = call.execute();
            if(response != null) {
                LOG.i(LOG_TAG, "update message: " + response.message());
                LOG.i(LOG_TAG, "update body: " + response.body());
            } else {
                LOG.e(LOG_TAG, "Response is null...");
            }
        } catch (IOException e) {
            LOG.e(LOG_TAG, "failure trying to insert item " + item, e);
        }
    }

    public static void testDelete(ServerAPI<DataModelObject> api, Integer id) {
        Call<DataModelObject> call = api.delete(id);
        LOG.i(LOG_TAG, "deleting item with id " + id + " from " + api.getApiName());
        try {
            Response<DataModelObject> response = call.execute();
            if(response != null) {
                LOG.i(LOG_TAG, "delete message: " + response.message());
                LOG.i(LOG_TAG, "delete body: " + response.body());
            } else {
                LOG.e(LOG_TAG, "Response is null...");
            }
        } catch (IOException e) {
            LOG.e(LOG_TAG, "failure trying to delete item with id " + id, e);
        }
    }
}

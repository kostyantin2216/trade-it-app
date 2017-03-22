package com.tradeitsignals.rest.api;

import android.content.Context;

import com.tradeitsignals.R;
import com.tradeitsignals.SignalsApplication;
import com.tradeitsignals.datamodel.data.Country;
import com.tradeitsignals.datamodel.data.MarketReview;
import com.tradeitsignals.datamodel.data.ServerLogEntry;
import com.tradeitsignals.logging.TILogger;
import com.tradeitsignals.rest.RetrofitAPI;
import com.tradeitsignals.rest.RetrofitConfig;
import com.tradeitsignals.rest.ServerAPI;
import com.tradeitsignals.rest.services.BrokerRegistrationService;
import com.tradeitsignals.rest.services.BrokerService;
import com.tradeitsignals.rest.services.ContentPageService;
import com.tradeitsignals.rest.services.CountryService;
import com.tradeitsignals.rest.services.LeadService;
import com.tradeitsignals.rest.services.MarketReviewService;
import com.tradeitsignals.rest.services.ServerLogEntryService;
import com.tradeitsignals.rest.services.SignalService;
import com.tradeitsignals.rest.services.UserService;
import com.tradeitsignals.utils.PrefUtils;

import retrofit2.Retrofit;

/**
 * Created by Kostyantin on 12/11/2015.
 */
public class APIFactory {

    private final static String LOG_TAG = "#" + APIFactory.class.getSimpleName();

    private final Retrofit retrofitClient;

    private final static APIFactory instance = new APIFactory();

    public static APIFactory getInstance() {
        return instance;
    }

    private APIFactory() {
        String baseUrl = PrefUtils.getBaseApiUrl();
        TILogger.getLog().d(LOG_TAG, "Creating api factory for url: " + baseUrl);
        retrofitClient = RetrofitConfig.getInstance().createRetrofitClient(baseUrl);
    }

    // SERVICES
    // -----------------------------------------------

    public enum ServiceTypes {
        BROKER_REGISTRATION_SERVICE(BrokerRegistrationService.class),
        BROKER_SERVICE(BrokerService.class),
        CONTENT_PAGE_SERVICE(ContentPageService.class),
        COUNTRY_SERVICE(Country.class),
        MARKET_REVIEW_SERVICE(MarketReview.class),
        SERVER_LOG_ENTRY_SERVICE(ServerLogEntry.class),
        SIGNAL_SERVICE(SignalService.class),
        USER_SERVICE(UserService.class),
        LEAD_SERVICE(LeadService.class);

        private final Class<?> serviceClass;
        ServiceTypes(Class<?> serviceClass){
            this.serviceClass = serviceClass;
        }
    }

    public <T> T getService(ServiceTypes type) {
        return (T) retrofitClient.create(type.serviceClass);
    }

    public LeadService getLeadService() { return retrofitClient.create(LeadService.class); }

    public SignalService getSignalService() {
        return retrofitClient.create(SignalService.class);
    }

    public BrokerService getBrokerService() {
        return retrofitClient.create(BrokerService.class);
    }

    public BrokerRegistrationService getBrokerRegistrationService() {
        return retrofitClient.create(BrokerRegistrationService.class);
    }

    public ContentPageService getContentPageService() {return retrofitClient.create(ContentPageService.class);}

    public MarketReviewService getMarketReviewService() {return retrofitClient.create(MarketReviewService.class);}

    public UserService getUserService() {return retrofitClient.create(UserService.class);}

    public CountryService getCountryService() {
        return retrofitClient.create(CountryService.class);
    }

    public ServerLogEntryService getServerLogEntryService() {
        return retrofitClient.create(ServerLogEntryService.class);
    }

    // API TYPES
    // ----------------------------------------------------

    public enum APITypes {
        BROKER(new BrokerAPI()),
        BROKER_REGISTRATION(new BrokerRegistrationAPI()),
        CONTENT_PAGE(new ContentPageAPI()),
        COUNTRY(new CountryAPI()),
        MARKET_REVIEW(new MarketReviewAPI()),
        SERVER_LOG_ENTRY(new ServerLogEntryAPI()),
        SIGNAL(new SignalAPI()),
        USER(new UserAPI());

        private final ServerAPI api;
        APITypes(ServerAPI api) {
            this.api = api;
        }
    }

    public ServerAPI getServerApi(APITypes type) {
        return type.api;
    }

    public RetrofitAPI getApi(APITypes type) { return type.api; }

    public BrokerAPI getBrokerApi() {
        return (BrokerAPI) APITypes.BROKER.api;
    }

    public BrokerRegistrationAPI getBrokerRegistrationAPI() {
        return (BrokerRegistrationAPI) APITypes.BROKER_REGISTRATION.api;
    }

    public ContentPageAPI getContentPageAPI() {
        return (ContentPageAPI) APITypes.CONTENT_PAGE.api;
    }

    public CountryAPI getCountryAPI() {
        return (CountryAPI) APITypes.COUNTRY.api;
    }

    public MarketReviewAPI getMarketReviewAPI() {
        return (MarketReviewAPI) APITypes.MARKET_REVIEW.api;
    }

    public ServerLogEntryAPI getServerLogEntryAPI() {
        return (ServerLogEntryAPI) APITypes.SERVER_LOG_ENTRY.api;
    }

    public SignalAPI getSignalAPI() {
        return (SignalAPI) APITypes.SIGNAL.api;
    }

    public UserAPI getUserAPI() {
        return (UserAPI) APITypes.USER.api;
    }

    public LeadAPI getLeadAPI() {
        return new LeadAPI();
    }

}

package com.tradeitsignals.helpers;

import com.tradeitsignals.datamodel.dao.BrokerDAO;
import com.tradeitsignals.datamodel.dao.UserDAO;
import com.tradeitsignals.datamodel.data.Broker;
import com.tradeitsignals.datamodel.data.BrokerRegistration;
import com.tradeitsignals.datamodel.data.Country;
import com.tradeitsignals.datamodel.data.User;
import com.tradeitsignals.external.brokers.UBinary;
import com.tradeitsignals.logging.TILogger;
import com.tradeitsignals.rest.ServerAPI;
import com.tradeitsignals.rest.api.APIFactory;
import com.tradeitsignals.rest.api.LeadAPI;
import com.tradeitsignals.rest.responses.UserRegistrationResponse;
import com.tradeitsignals.synchronization.SynchronizableTables;
import com.tradeitsignals.synchronization.SynchronizationExecutor;
import com.tradeitsignals.synchronization.SynchronizationListener;
import com.tradeitsignals.synchronization.SynchronizationTask;
import com.tradeitsignals.utils.Constants;
import com.tradeitsignals.utils.PrefUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Kostyantin on 10/19/2015.
 */
public class RegistrationHelper {

    private final static String LOG_TAG = "#RegistrationHelper";

    public void registerUser(String firstName, String lastName, String password, String email, String telephone, final RegistrationListener registrationListener) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setEmail(email);
        user.setTelephone(telephone);
        user.setIsRegistered(true);

        TILogger.getLog().d(LOG_TAG, "registering user: " + user.toString());

        LeadAPI leadAPI = APIFactory.getInstance().getLeadAPI();
        leadAPI.registerUser(user, new Callback<UserRegistrationResponse>() {
            @Override
            public void onResponse(Call<UserRegistrationResponse> call, Response<UserRegistrationResponse> response) {
                UserRegistrationResponse resp = response.body();
                User user = resp.getUser();
                Integer userId = user != null ? user.getId() : null;
                if(userId != null && userId >= 0) {
                    UserDAO.getInstance().insert(user);
                    PrefUtils.setUserId(userId);
                    BrokerRegistration[] registrations = resp.getBrokerRegistrationDetails();
                    if (registrations != null) {
                        TILogger.getLog().d(LOG_TAG, "Broker registration result: " + registrations);
                        for (BrokerRegistration registration : registrations) {
                            if (registration != null && registration.getBrokerId() == Constants.UBINARY_CODE
                                    && registration.isSuccess()) {
                                UBinary uBinary = new UBinary();
                                uBinary.saveRegistrationId(registration.getUserBrokerId());
                                uBinary.login();
                            }
                        }
                    } else {
                        TILogger.getLog().e(LOG_TAG, "Broker registration result was null");
                    }
                    if(registrationListener != null) {
                        registrationListener.onRegistrationComplete();
                    }
                } else {
                    if(registrationListener != null) {
                        registrationListener.onRegistrationError("Server Error, please try again later");
                    }
                    TILogger.getLog().e(LOG_TAG, "Error while registering user to server, response msg: " + response.message(), false, true);
                }
            }

            @Override
            public void onFailure(Call<UserRegistrationResponse> call, Throwable t) {
                if(registrationListener != null) {
                    registrationListener.onRegistrationError("Server Error, please try again later");
                }
                TILogger.getLog().e(LOG_TAG, "Error while registering user to server", t, false, true);
            }
        });
    }

    public interface RegistrationListener {
        void onRegistrationComplete();
        void onRegistrationError(String msg);
    }

}

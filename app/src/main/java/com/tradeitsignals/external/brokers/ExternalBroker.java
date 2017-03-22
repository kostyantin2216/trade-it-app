package com.tradeitsignals.external.brokers;

/**
 * Created by Kostyantin on 4/13/2016.
 */
public interface ExternalBroker {
    void saveRegistrationId(String id);
    String getRegistrationId();
    void login();
}

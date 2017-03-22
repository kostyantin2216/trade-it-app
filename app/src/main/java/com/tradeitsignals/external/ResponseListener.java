package com.tradeitsignals.external;

/**
 * Created by Kostyantin on 10/11/2015.
 */
public interface ResponseListener {
    void onResponse(String response);
    void onError(String error);
}

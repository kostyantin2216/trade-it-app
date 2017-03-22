package com.tradeitsignals.rest.interceptors;

import com.tradeitsignals.logging.TILogger;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;


/**
 * Created by Kostyantin on 1/8/2016.
 */
@Deprecated
public class LoggingInterceptor implements Interceptor {

    private final static String LOG_TAG = "#LoggingInterceptor";
    private final static TILogger log = TILogger.getLog();
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        log.i(LOG_TAG, "INTERCEPTING REQUEST!!");
        log.i(LOG_TAG, "request url: " + request.url().toString());
        log.i(LOG_TAG, "request headers:");
        logHeaders(request.headers());
        log.i(LOG_TAG, "request body: " + (request.body() != null ? bodyToString(request.body()) : "null"));

        Response response = chain.proceed(request);

        log.i(LOG_TAG, "INTERCEPTING RESPONSE!!");
        log.i(LOG_TAG, "response " + (response.isSuccessful() ? "was" : "wasn't") + " successful");
        log.i(LOG_TAG, "response message: " + response.message());
        log.i(LOG_TAG, "response headers:");
        logHeaders(response.headers());
        log.i(LOG_TAG, "response body: " + response.body().toString());

        return response;
    }

    private void logHeaders(Headers headers) {
        for(Map.Entry<String, List<String>> entry : headers.toMultimap().entrySet()) {
            String output = entry.getKey() + ": ";
            List<String> values = entry.getValue();
            for(int i = 0; i < values.size(); i++) {
                output += values.get(i);
                if(i < values.size() - 1) {
                    output += ", ";
                }
            }
            log.i(LOG_TAG, output);
        }
    }

    private String bodyToString(final RequestBody request){
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            copy.writeTo(buffer);
            return buffer.readUtf8();
        }
        catch (final IOException e) {
            return "did not work";
        }
    }
}

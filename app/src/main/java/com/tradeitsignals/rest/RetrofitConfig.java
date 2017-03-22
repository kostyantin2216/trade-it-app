package com.tradeitsignals.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.tradeitsignals.datamodel.data.Signal;
import com.tradeitsignals.logging.TILogger;
import com.tradeitsignals.rest.interceptors.AuthorizationInterceptor;
import com.tradeitsignals.helpers.TIConfiguration;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Kostyantin on 4/13/2016.
 */
public class RetrofitConfig {

    private final static String LOG_TAG = "#" + RetrofitConfig.class.getSimpleName();

    private OkHttpClient mHttpClient;

    public final static RetrofitConfig instance = new RetrofitConfig();

    private RetrofitConfig() {
        initHttpClient();
    }

    public static RetrofitConfig getInstance() {
        return instance;
    }

    private void initHttpClient() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        if(TIConfiguration.getBoolean("is_development", false)) {
            httpClientBuilder.addInterceptor(getLoggingInterceptor());
        }

        mHttpClient = httpClientBuilder.build();
    }

    public OkHttpClient getHttpClient() {
        return mHttpClient;
    }

    public Retrofit createRetrofitClient(String url) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        httpClientBuilder.addInterceptor(new AuthorizationInterceptor());

        if(TIConfiguration.getBoolean("is_development", false)) {
            TILogger.getLog().i(LOG_TAG, "setting retrofit http client timeouts to 3 minutes...");

            httpClientBuilder.addInterceptor(getLoggingInterceptor())
                             .connectTimeout(3, TimeUnit.MINUTES)
                             .readTimeout(3, TimeUnit.MINUTES)
                             .writeTimeout(3, TimeUnit.MINUTES);
        }

        return new Retrofit.Builder()
                .baseUrl(url)
                .client(httpClientBuilder.build())
                .addConverterFactory(getGsonConverterFactory())
                .build();
    }

    private HttpLoggingInterceptor getLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

    private GsonConverterFactory getGsonConverterFactory() {
        GsonBuilder builder = new GsonBuilder();

        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });

        builder.registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
            @Override
            public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(src.getTime());
            }
        });

        builder.registerTypeAdapter(Signal.class, new JsonSerializer<Signal>() {
            @Override
            public JsonElement serialize(Signal src, Type typeOfSrc, JsonSerializationContext context) {
                JsonObject json = new JsonObject();
                json.addProperty("asset", src.getAsset());
                json.addProperty("createdAt", src.getCreatedAt().getTime());
                json.addProperty("status", src.getStatus().ordinal());
                json.addProperty("expiryTime", src.getExpiryTime().getTime());
                json.addProperty("isCall", src.isCall());
                json.addProperty("expiryRate", src.getExpiryRate());
                json.addProperty("entryRate", src.getEntryRate());

                return json;
            }
        });

        Gson gson = builder.create();

        return GsonConverterFactory.create(gson);
    }

    public void test() {

        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("109.64.109.38")
                .port(3000)
                .addPathSegment("changeState")
                .addQueryParameter("action", "4")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(null, new byte[0]))
                .build();
    }
}

package com.tradeitsignals.json;

import android.content.Context;

import com.tradeitsignals.R;
import com.tradeitsignals.logging.TILogger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Kostyantin on 8/12/2015.
 */
public class JSONFetcher {

    public final static int URL_READ_TIMEOUT_MILLIS = 2 * 60 * 1000;
    public final static int URL_CONN_TIMEOUT_MILLIS = 3 * 60 * 1000;
    public final static String GET_METHOD = "GET";

    private Context mContext;

    public JSONFetcher(Context context) {
        this.mContext = context;
    }

    public JSONObject getJsonFromUrl(String _url) throws IOException, JSONException {
        JSONObject resultJson = null;
        URL url = new URL(_url);

        HttpURLConnection conn = getHttpConnection(url);
        conn.connect();

        int response = conn.getResponseCode();
        TILogger.getLog().i("The response code for getting json from url is " + response);

        InputStream is = null;
        try{
            is = conn.getInputStream();
            String result = getResultFromStream(is);
            resultJson = new JSONObject(result);
        } catch (IOException e) {
            TILogger.getLog().e("Failure with input stream while fetching JSON from " + _url, e);
        } finally {
            if(is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(conn != null) {
            conn.disconnect();
        }
        return resultJson;
    }

    private HttpURLConnection getHttpConnection(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(URL_READ_TIMEOUT_MILLIS);
        conn.setConnectTimeout(URL_CONN_TIMEOUT_MILLIS);
        conn.setRequestMethod(GET_METHOD);
        conn.setDoInput(true);
        return conn;
    }

    private String getResultFromStream(InputStream is) throws IOException {
        BufferedReader bReader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
        StringBuilder sBuilder = new StringBuilder();

        String line;
        while ((line = bReader.readLine()) != null) {
            sBuilder.append(line + "\n");
        }

        return sBuilder.toString();
    }

    public JSONObject getParseKeys() throws IOException, JSONException {
        InputStream is = mContext.getAssets().open(String.format(mContext.getString(R.string.random_file_name),
                mContext.getResources().getInteger(R.integer.secret_number)));

        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        return new JSONObject(new String(buffer));
    }

    public JSONArray getJsonArrayFromAssets(String assetsFileName) throws IOException, JSONException {
        InputStream is = mContext.getAssets().open(assetsFileName);

        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        return new JSONArray(new String(buffer));
    }

}

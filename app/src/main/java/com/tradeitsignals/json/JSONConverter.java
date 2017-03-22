package com.tradeitsignals.json;

import com.tradeitsignals.datamodel.dao.SignalDAO;
import com.tradeitsignals.datamodel.data.Signal;
import com.tradeitsignals.logging.LogAccess;
import com.tradeitsignals.logging.TILogger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Map;

/**
 * Created by Kostyantin on 8/27/2015.
 */
@Deprecated
public class JSONConverter {

    /**
     *  jsonSignal eg.  {
     *                          "id": "ji3dkk3j",
     *                          "time_created" : "2015-05-30 20:50",
     *                          "expiry_time" : "2015-05-30 21:00",
     *                          "currency_pair" : "AUD/USD",
     *                          "rate" : 2.5345,
     *                          "is_call" : true
     *                        }
     *
     * @return Signal Object of JSONObject jsonSignal.
     *//*
    public Signal toSignal(JSONObject jsonSignal) {
        Signal signal = null;
        try {
            signal = new Signal(
                    jsonSignal.getString(SignalDAO.KEY_ID),
                    new Date(jsonSignal.getString(SignalDAO.KEY_CREATED_AT)),
                    new Date(jsonSignal.getString(SignalDAO.KEY_EXPIRY_TIME)),
                    jsonSignal.getString(SignalDAO.KEY_ASSET),
                    jsonSignal.getDouble(SignalDAO.KEY_ENTRY_RATE),
                    jsonSignal.getBoolean(SignalDAO.KEY_IS_CALL)
            );

        } catch (JSONException e) {
            TILogger.getLog().e("Failed to convert jsonSignal - \n" + jsonSignal, e, true, LogAccess.SIGNALS_LOG_FILE_NAME);
        }

        return signal;
    }*/

    public static String toJson(Map<String, String> params) {


        return null;
    }
}

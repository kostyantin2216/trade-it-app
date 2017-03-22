package com.tradeitsignals.logging;

import android.util.Log;

import com.tradeitsignals.R;
import com.tradeitsignals.SignalsApplication;
import com.tradeitsignals.datamodel.data.ServerLogEntry;
import com.tradeitsignals.rest.api.APIFactory;
import com.tradeitsignals.rest.api.ServerLogEntryAPI;
import com.tradeitsignals.utils.DateUtils;
import com.tradeitsignals.utils.PrefUtils;
import com.tradeitsignals.helpers.TIConfiguration;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Kostyantin on 8/21/2015.
 *
 * TODO: remove saveToLog and LogName parameters from methods.
 */
public class TILogger {

    private static String BASE_TAG = "TILogger - ";
    private final static String USER_TAG = "#USER - ";

    private enum LogType {
        INFO, DEBUG, VERBOSE, WARN, ERROR
    }

    public static Boolean DEBUG;

    private String logTag;

    static {
        DEBUG = SignalsApplication.getAppContext().getResources().getBoolean(R.bool.is_log_debug_enabled);
    }

    /**
     *  Singleton
     */
    private TILogger(String logTag) {
        this.logTag = logTag;
    }

    private static TILogger log;

    public static TILogger getLog() {
        if(log == null || !log.logTag.equals("")) {
            log = new TILogger("");
        }

        return log;
    }

    public static TILogger getUserLog() {
        if(log == null || !log.logTag.equals(USER_TAG)) {
            log = new TILogger(USER_TAG);
        }

        return log;
    }

    public static Boolean isDebug() {
        return DEBUG;
    }

    public static String getBaseTag() {
        if(BASE_TAG == null) {
            BASE_TAG = SignalsApplication.getAppContext().getString(R.string.base_log_tag);
        }

        return BASE_TAG;
     }

    public void e(String message) {
        log(LogType.ERROR, null, message, null, false, null, false);
    }

    public void e(String tag, String message){
        log(LogType.ERROR, tag, message, null, false, null, false);
    }

    public void e(String message, boolean saveToFile) {
        log(LogType.ERROR, null, message, null, saveToFile, null, false);
    }

    public void e(String message, boolean saveToFile, boolean logOnServer) {
        log(LogType.ERROR, null, message, null, saveToFile, null, logOnServer);
    }

    public void e(String message, Throwable throwable) {
        log(LogType.ERROR, null, message, throwable, false, null, false);
    }

    public void e(String tag, String message, boolean saveToFile) {
        log(LogType.ERROR, tag, message, null, saveToFile, null, false);
    }

    public void e(String tag, String message, boolean saveToFile, boolean logOnServer) {
        log(LogType.ERROR, tag, message, null, saveToFile, null, logOnServer);
    }

    public void e(String tag, String message, Throwable throwable) {
        log(LogType.ERROR, tag, message, throwable, false, null, false);
    }

    public void e(String tag, String message, Throwable throwable, boolean saveToFile, boolean logOnServer) {
        log(LogType.ERROR, tag, message, throwable, saveToFile, null, logOnServer);
    }

    public void e(String message, boolean saveToFile, String fileName) {
        log(LogType.ERROR, null, message, null, saveToFile, fileName, false);
    }

    public void e(String message, Throwable throwable, boolean saveToFile) {
        log(LogType.ERROR, null, message, throwable, saveToFile, null, false);
    }

    public void e(String message, Throwable throwable, boolean saveToFile, boolean logOnServer) {
        log(LogType.ERROR, null, message, throwable, saveToFile, null, logOnServer);
    }

    public void e(String message, Throwable throwable, boolean saveToFile, String fileName) {
        log(LogType.ERROR, null, message, throwable, saveToFile, fileName, false);
    }

    public void e(String tag, String message, Throwable throwable, boolean saveToFile, String fileName) {
        log(LogType.ERROR, tag, message, throwable, saveToFile, fileName, false);
    }

    public void e(String tag, String message, Throwable throwable, boolean saveToFile, String fileName, boolean logOnServer) {
        log(LogType.ERROR, tag, message, throwable, saveToFile, fileName, logOnServer);
    }

    public void d(String message) {
        if(DEBUG) {
            log(LogType.INFO, null, message, null, false, null, false);
        }
    }

    public void d(String tag, String message){
        if(DEBUG) {
            log(LogType.INFO, tag, message, null, false, null, false);
        }
    }

    public void d(String message, boolean saveToFile) {
        if (DEBUG) {
            log(LogType.INFO, null, message, null, saveToFile, null, false);
        }
    }

    public void d(String message, Throwable throwable) {
        if (DEBUG) {
            log(LogType.INFO, null, message, throwable, false, null, false);
        }
    }

    public void d(String tag, String message, boolean saveToFile) {
        if(DEBUG) {
            log(LogType.INFO, tag, message, null, saveToFile, null, false);
        }
    }

    public void d(String message, boolean saveToFile, boolean logOnServer) {
        if(DEBUG) {
            log(LogType.INFO, null, message, null, saveToFile, null,logOnServer);
        }
    }

    public void d(String tag, String message, boolean saveToFile, boolean logOnServer) {
        if(DEBUG) {
            log(LogType.INFO, tag, message, null, saveToFile, null, logOnServer);
        }
    }

    public void d(String tag, String message, Throwable throwable, boolean saveToFile, boolean logOnServer) {
        if(DEBUG) {
            log(LogType.INFO, tag, message, throwable, saveToFile, null, logOnServer);
        }
    }

    public void d(String tag, String message, Throwable throwable) {
        if (DEBUG) {
            log(LogType.INFO, tag, message, throwable, false, null, false);
        }
    }

    public void d(String message, boolean saveToFile, String fileName) {
        if(DEBUG) {
            log(LogType.INFO, null, message, null, saveToFile, fileName, false);
        }
    }

    public void d(String message, Throwable throwable, boolean saveToFile) {
        if (DEBUG) {
            log(LogType.INFO, null, message, throwable, saveToFile, null, false);
        }
    }

    public void d(String message, Throwable throwable, boolean saveToFile, String fileName) {
        if(DEBUG) {
            log(LogType.INFO, null, message, throwable, saveToFile, fileName, false);
        }
    }

    public void d(String tag, String message, Throwable throwable, boolean saveToFile, String fileName, boolean saveTofile) {
        if (DEBUG) {
            log(LogType.INFO, tag, message, throwable, saveToFile, fileName, saveToFile);
        }
    }
    public void w(String message) {
        log(LogType.WARN, null, message, null, false, null, false);
    }

    public void w(String tag, String message){
        log(LogType.WARN, tag, message, null, false, null, false);
    }

    public void w(String message, boolean saveToFile) {
        log(LogType.WARN, null, message, null, saveToFile, null, false);
    }

    public void w(String tag, String message, boolean saveToFile, boolean logOnServer){
        log(LogType.WARN, tag, message, null, saveToFile, null, logOnServer);
    }

    public void w(String message, Throwable throwable) {
        log(LogType.WARN, null, message, throwable, false, null, false);
    }

    public void w(String tag, String message, Throwable throwable) {
        log(LogType.WARN, tag, message, throwable, false, null, false);
    }

    public void w(String message, Throwable throwable, boolean saveToFile) {
        log(LogType.WARN, null, message, throwable, saveToFile, null, false);
    }

    public void w(String message, Throwable throwable, boolean saveToFile, String fileName) {
        log(LogType.WARN, null, message, throwable, saveToFile, fileName, false);
    }

    public void w(String tag, String message, Throwable throwable, boolean saveToFile, String fileName, boolean logOnServer) {
        log(LogType.WARN, tag, message, throwable, saveToFile, fileName, logOnServer);
    }

    public void i(String message) {
        log(LogType.INFO, null, message, null, false, null, false);
    }

    public void i(String tag, String message){
        log(LogType.INFO, tag, message, null, false, null, false);
    }

    public void i(String message, boolean saveToFile) {
        log(LogType.INFO, null, message, null, saveToFile, null, false);
    }

    public void i(String tag, String message, boolean logOnServer){
        log(LogType.INFO, tag, message, null, false, null, logOnServer);
    }

    public void i(String message, boolean saveToFile, boolean logOnServer) {
        log(LogType.INFO, null, message, null, saveToFile, null, logOnServer);
    }

    public void i(String tag, String message, boolean saveToFile, boolean logOnServer){
        log(LogType.INFO, tag, message, null, saveToFile, null, logOnServer);
    }

    public void i(String message, Throwable throwable) {
        log(LogType.INFO, null, message, throwable, false, null, false);
    }

    public void i(String message, Throwable throwable, boolean saveToFile, boolean logOnServer) {
        log(LogType.INFO, null, message, throwable, saveToFile, null, logOnServer);
    }

    public void i(String tag, String message, Throwable throwable) {
        log(LogType.INFO, tag, message, throwable, false, null, false);
    }

    public void i(String tag, String message, Throwable throwable, boolean logOnServer) {
        log(LogType.INFO, tag, message, throwable, false, null, logOnServer);
    }

    public void i(String message, boolean saveToFile, String fileName) {
        log(LogType.INFO, null, message, null, saveToFile, fileName, false);
    }

    public void i(String message, boolean saveToFile, String fileName, boolean logOnServer) {
        log(LogType.INFO, null, message, null, saveToFile, fileName, logOnServer);
    }

    public void i(String message, Throwable throwable, boolean saveToFile) {
        log(LogType.INFO, null, message, throwable, saveToFile, null, false);
    }

    public void i(String tag, String message, boolean saveToFile, String fileName) {
        log(LogType.INFO, tag, message, null, saveToFile, fileName, false);
    }

    public void i(String tag, String message, boolean saveToFile, String fileName, boolean logOnServer) {
        log(LogType.INFO, tag, message, null, saveToFile, fileName, logOnServer);
    }

    public void i(String message, Throwable throwable, boolean saveToFile, String fileName) {
        log(LogType.INFO, null, message, throwable, saveToFile, fileName, false);
    }

    public void i(String tag, String message, Throwable throwable, boolean saveToFile, String fileName, boolean logOnServer) {
        log(LogType.INFO, tag, message, throwable, saveToFile, fileName, logOnServer);
    }

    public void log(LogType type, String tag, String message, Throwable throwable, boolean saveToFile, String fileName, boolean logToServer) {
        if (tag == null) {
            tag = "";
        }

        tag = getBaseTag() + logTag + tag;

        if(TIConfiguration.isDevelopment()) {
            if (message != null) {
                switch (type) {
                    case DEBUG:
                        Log.d(tag, message, throwable);
                        break;
                    case ERROR:
                        Log.e(tag, message, throwable);
                        break;
                    case INFO:
                        Log.i(tag, message, throwable);
                        break;
                    case VERBOSE:
                        Log.v(tag, message, throwable);
                        break;
                    case WARN:
                        Log.w(tag, message, throwable);
                        break;
                }
            } else {
                e(BASE_TAG, "message cannot be null in TILogger.log()");
            }
        }/* else */if((logToServer || type == LogType.ERROR) && message != null) {
            saveToServer(tag, message, throwable);
        }
    }

    private void saveToServer(String tag, String message, Throwable throwable) {
        final ServerLogEntry entry = new ServerLogEntry();
        entry.setTag(tag);
        entry.setMessage(message);
        entry.setStackTrace(Log.getStackTraceString(throwable));
        entry.setDeviceInfo(TIConfiguration.getDeviceInfo());
        entry.setVersionInfo(TIConfiguration.getApplicationInfo());
        entry.setUserId(PrefUtils.getUserId());

        ServerLogEntryAPI api = APIFactory.getInstance().getServerLogEntryAPI();
        api.post(entry).enqueue(new Callback<ServerLogEntry>() {
            @Override
            public void onResponse(Call<ServerLogEntry> call, Response<ServerLogEntry> response) {
                TILogger.getLog().d("Response from log entry from server: " + response.message());
            }

            @Override
            public void onFailure(Call<ServerLogEntry> call, Throwable t) {
                TILogger.getLog().w("Failure saving log entry on server(" + entry.toString() + ")", t);
            }
        });
    }

    public String buildLogOutput(String tag, String message, Throwable throwable) {
        StringBuilder output = new StringBuilder();
        output.append("\n").append(getCurrentDateTimeString());
        if (tag != null) {
            output.append(" -TAG: ").append(tag);
        }
        if (message != null) {
            output.append(" -MESSAGE:").append(message);
        }
        if (throwable != null) {
            output.append("\n").append(Log.getStackTraceString(throwable));
        }

        return output.toString();
    }

    private String getOutputFromStackTrace(StackTraceElement[] stackTraceElements) {
        return null;
    }

    private String getCurrentDateTimeString() {
        return DateUtils.formatToString(new Date(System.currentTimeMillis()));
    }

}

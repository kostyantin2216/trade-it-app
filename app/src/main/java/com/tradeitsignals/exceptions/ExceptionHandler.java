package com.tradeitsignals.exceptions;

import com.tradeitsignals.logging.TILogger;
import com.tradeitsignals.utils.DateUtils;

import java.util.Date;

/**
 * Created by Kostyantin on 8/26/2015.
 */
public class ExceptionHandler implements Thread.UncaughtExceptionHandler {

    private final static String LOG_TAG = " ####### EXCEPTION HANDLER #######";
    private final static String LOG_FILE_NAME = "UNCAUGHT_ERRORS_";

    private Thread.UncaughtExceptionHandler defaultExceptionHandler;

    public ExceptionHandler() {
        this.defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        StringBuilder logMessage = new StringBuilder();
        logMessage
                .append("UNCAUGHT EXCEPTION in:")
                .append("\n")
                .append("Thread [id = ")
                .append(thread.getId())
                .append(", name = ")
                .append(thread.getName())
                .append(", class = ")
                .append(thread.getClass())
                .append(", classLoader = ")
                .append(thread.getContextClassLoader())
                .append(", state = ")
                .append(thread.getState())
                .append(", priority = ")
                .append(thread.getPriority())
                .append(", threadGroup = ")
                .append(thread.getThreadGroup());

        String fileName = LOG_FILE_NAME + DateUtils.formatToString(DateUtils.FORMAT_LOG_FILE_NAME, new Date()) + ".txt";

        TILogger.getLog().e(LOG_TAG, logMessage.toString(), ex, true, fileName, true);

        defaultExceptionHandler.uncaughtException(thread, ex);
    }

}

package com.tradeitsignals.utils;

import com.tradeitsignals.logging.TILogger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Kostyantin on 8/7/2015.
 */
public class StringUtils {

    public static String firstLetterToUpper(String string) {
        if(string.length() > 1) {
            return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
        } else {
            return string.toUpperCase();
        }
    }

    public static boolean isEmpty(String string) {
        return string == null || string.trim().equals("");
    }

    public static boolean notEmpty(String string) {
        return string != null && !string.trim().equals("");
    }

    public static String toString(StackTraceElement[] stackTraceElements) {
         if (stackTraceElements == null)
             return "";
         StringBuilder stringBuilder = new StringBuilder();
         for (StackTraceElement element : stackTraceElements)
             stringBuilder.append(element.toString()).append("\n");
         return stringBuilder.toString();
    }

    public static <K, V> String toString(Map<K, V> map) {
        return  toString(map, false);
    }

    public static <K, V> String toString(Map<K, V> map, boolean encodeValues) {
        StringBuilder sb = new StringBuilder();

        Iterator<Map.Entry<K, V>> itr = map.entrySet().iterator();
        boolean hasNext = itr.hasNext();
        while(hasNext) {
            Map.Entry<K, V> entry = itr.next();
            String value = entry.getValue().toString();
            if(encodeValues) {
                try {
                    value = URLEncoder.encode(value, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    TILogger.getLog().e("Could not encode: " + value);
                }
            }
            sb.append(entry.getKey()).append("=").append(value);

            hasNext = itr.hasNext();
            if(hasNext) {
                sb.append("&");
            }
        }

        return sb.toString();
    }

    public static Map<String, String> toMap(String params) {
        Map<String, String> paramsMap = new HashMap<>();
        String[] splitParams = params.split("&");
        for(String param : splitParams) {
            String[] splitParam = param.split("=");
            if(splitParam.length == 2) {
                paramsMap.put(splitParam[0], splitParam[1]);
            }
        }

        return paramsMap;
    }

}

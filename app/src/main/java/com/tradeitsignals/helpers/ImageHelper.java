package com.tradeitsignals.helpers;

import android.content.Context;

/**
 * Created by Kostyantin on 7/1/2015.
 */
public class ImageHelper {

    private Context context;

    public ImageHelper(Context context) {
        this.context = context;
    }

    public String saveImageToDeviceFromUrl(String url) {
        String path = generatePathFromUrl(url);
        return path;
    }

    public String generatePathFromUrl(String url) {
        return "";
    }
}

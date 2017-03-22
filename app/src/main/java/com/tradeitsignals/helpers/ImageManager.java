package com.tradeitsignals.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.tradeitsignals.logging.TILogger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ImageManager {

    private final static String LOG_TAG = "Image Manager";

    private Context context;

    public ImageManager(Context context) {
        this.context = context;
    }

    public String downloadFromUrl(String imageURL, String fileName) {  //this is the downloader method
        try {
            // Example: "http://lumberjackproduction.com//tradeit//imgs//brokers.jpg"
            URL url = new URL(imageURL);
            File file = getFileFromFileName(fileName);

            long startTime = System.currentTimeMillis();
            TILogger.getLog().d("ImageManager", "download beginning");
            TILogger.getLog().d("ImageManager", "download url:" + url);
            TILogger.getLog().d("ImageManager", "downloaded file name:" + fileName);

            URLConnection ucon = url.openConnection();
            InputStream is = ucon.getInputStream();

            Bitmap bmp = BitmapFactory.decodeStream(is);

            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, fos);

            fos.close();

            TILogger.getLog().d("ImageManager", "download ready in"
                    + ((System.currentTimeMillis() - startTime) / 1000)
                    + " sec");
        } catch (IOException e) {
            TILogger.getLog().e(LOG_TAG, "Error while trying to download and save image from url: " + imageURL, e);
        }
        return fileName;
    }

    public File getFileFromFileName(String fileName) {
        return new File(getDataFolder(), fileName);
    }

    private File getDataFolder() {
        File dataDir = null;

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            dataDir = new File(Environment.getExternalStorageDirectory(), "myappdata");

            if(!dataDir.isDirectory()) {
                dataDir.mkdirs();
            }
        }

        if(dataDir == null || !dataDir.isDirectory()) {
            dataDir = context.getFilesDir();
        }

        return dataDir;
    }

}

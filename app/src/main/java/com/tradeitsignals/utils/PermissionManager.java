package com.tradeitsignals.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.UiThread;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;

import com.tradeitsignals.external.analytics.AppEventTracker;
import com.tradeitsignals.logging.TILogger;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 *  Created by Kostyantin on 5/25/2016.
 *
 *  Helper class for checking and requesting user permissions.
 *      - To check if user has permissions and/or needs an explanation before requesting for the
 *        permissions use instance method:
 *              {@link #requestPermissions(Activity, boolean, PermissionRequest, String...)}.
 *      - To only check if user has permissions use static method:
 *              {@link #hasPermissions(Context, String...)}
 */
public class PermissionManager {

    private final static String LOG_TAG = "#" + PermissionManager.class.getSimpleName();

    private final SparseArray<PermissionRequest> mPermissionRequests = new SparseArray<>();
    private int mRequestCount = 0;

    private final AppEventTracker mTracker;

    @UiThread
    public PermissionManager() {
        mTracker = null;
    }

    @UiThread
    public PermissionManager(AppEventTracker tracker) {
        mTracker = tracker;
    }

    /**
     *  Check if {@param permissions}'s are granted if so call the {@param request}'s granted method
     *  otherwise check if explanation's are needed and either call the {@param request}'s explanation
     *  request method or request the permissions from the user(if explanation is requested then this
     *  method should be could again with {@param explained} set to true).
     *
     *  Make sure to override the calling {@param activity}'s
     *  {@link Activity#onRequestPermissionsResult(int, String[], int[])}
     *  and delegate the result to {@link #onRequestPermissionsResult(int, String[], int[])}.
     *
     *  Must be called from the UI Thread as this class instance is tightly bound to the calling
     *  activity.
     *
     * @param activity The activity calling this method.
     * @param explained Flag indicating if permissions have already been explained.
     * @param request The callback for the request.
     * @param permissions The @{@link android.Manifest.permission}'s that are being requested.
     */
    @UiThread
    public void requestPermissions(Activity activity, boolean explained, PermissionRequest request, String... permissions) {
        if(!hasPermissions(activity, permissions)) {
            Set<String> neededPermissions = new HashSet<>();
            boolean explanationNeeded = false;
            for(String permission : permissions) {
                if(ContextCompat.checkSelfPermission(activity, permission)
                        != PackageManager.PERMISSION_GRANTED) {
                    neededPermissions.add(permission);
                    explanationNeeded = !explained && !explanationNeeded
                            && ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
                }
            }

            permissions = neededPermissions.toArray(new String[neededPermissions.size()]);
            if(explanationNeeded) {
                request.onPermissionExplanationRequest(permissions);
            } else {
                int requestCode = mRequestCount++;
                mPermissionRequests.append(requestCode, request);
                ActivityCompat.requestPermissions(activity, permissions, requestCode);
                TILogger.getLog().i(LOG_TAG, "Requesting permissions: " + Arrays.toString(permissions)
                        + ", for request code: " + requestCode, false, true);
                if(mTracker != null) {
                    mTracker.trackPermissionRequest(permissions);
                }
            }
        } else {
            request.onPermissionGranted(permissions);
        }
    }

    @UiThread
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        PermissionRequest request = mPermissionRequests.get(requestCode);
        if(request != null) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                TILogger.getLog().i(LOG_TAG, "Request granted for code: " + requestCode + ", for permissions: "
                        + Arrays.toString(permissions), true, true);
                request.onPermissionGranted(permissions);
                if(mTracker != null) {
                    mTracker.trackPermissionGranted(permissions);
                }
            } else {
                TILogger.getLog().w(LOG_TAG, "Request denied for code: " + requestCode + ", for permissions: "
                        + Arrays.toString(permissions), true, true);
                request.onPermissionDenied(permissions);
                if(mTracker != null) {
                    mTracker.trackPermissionDenied(permissions);
                }
            }

            mPermissionRequests.remove(requestCode);
        } else {
            TILogger.getLog().w(LOG_TAG, "Could not find request for result with code: " + requestCode + ", for permissions: "
                    + Arrays.toString(permissions), true, true);
        }
    }

    public static boolean hasPermissions(Context ctx, String... permissions) {
        if(Build.VERSION.SDK_INT >= 23) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(ctx, permission)
                        != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    public interface PermissionRequest {
        void onPermissionGranted(String[] permissions);
        void onPermissionDenied(String[] permissions);
        void onPermissionExplanationRequest(String[] permissions);
    }

}

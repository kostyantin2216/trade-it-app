package com.tradeitsignals.external.analytics;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;

/**
 * Created by Kostyantin on 9/10/2016.
 */
public class AppContentViewTracker {

    public static void track(String contentName, String contentType, String contentId) {
        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName(contentName)
                .putContentType(contentType)
                .putContentId(contentId));
    }



}

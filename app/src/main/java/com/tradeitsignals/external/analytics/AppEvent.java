package com.tradeitsignals.external.analytics;

import android.support.annotation.StringRes;

import com.tradeitsignals.datamodel.ImmutableKeyValue;

import java.util.Arrays;

/**
 * Created by Kostyantin on 9/10/2016.
 */
public class AppEvent {

    public final String event;
    public final ImmutableKeyValue<String, String>[] labels;
    public final boolean sendToAppsFlyer;
    public final boolean sendToFacebook;
    public final boolean sendToAnswers;

    private AppEvent(Builder builder) {
        this.event = builder.event;
        this.labels = builder.labels;
        this.sendToAppsFlyer = builder.sendToAppsFlyer;
        this.sendToFacebook = builder.sendToFacebook;
        this.sendToAnswers = builder.sendToAnswers;
    }

    @Override
    public String toString() {
        return "AppEvent{" +
                "event='" + event + '\'' +
                ", labels='" + Arrays.toString(labels) + '\'' +
                ", sendToAppsFlyer=" + sendToAppsFlyer +
                ", sendToFacebook=" + sendToFacebook +
                ", sendToAnswers=" + sendToAnswers +
                '}';
    }

    public static class Builder {
        private String event;
        private ImmutableKeyValue<String, String>[] labels;
        private boolean sendToAppsFlyer;
        private boolean sendToFacebook;
        private boolean sendToAnswers;

        public Builder(String event) {
            this.event = event;
            this.labels = null;
            this.sendToAnswers = true;
            this.sendToFacebook = false;
            this.sendToAppsFlyer = false;
        }

        @SuppressWarnings("unchecked")
        public final Builder label(String key, String value) {
            this.labels = new ImmutableKeyValue[1];
            this.labels[0] = new ImmutableKeyValue<>(key, value);
            return this;
        }

        @SafeVarargs
        public final Builder labels(ImmutableKeyValue<String, String>... labels) {
            this.labels = labels;
            return this;
        }

        public final Builder sendToAll() {
            this.sendToAppsFlyer = true;
            this.sendToFacebook = true;
            return this;
        }

        public final Builder sendToFacebook() {
            this.sendToFacebook = true;
            return this;
        }

        public final Builder sendToAppsFlyer() {
            this.sendToAppsFlyer = true;
            return this;
        }

        public final AppEvent build() {
            return new AppEvent(this);
        }
    }
}

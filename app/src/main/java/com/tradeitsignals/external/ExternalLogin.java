package com.tradeitsignals.external;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Kostyantin on 6/4/2016.
 */
public interface ExternalLogin {

    enum Status {
        SUCCESS,
        ERROR,
        CANCELED,
        NO_PROFILE,
        NO_EMAIL
    }

    enum Source {
        FACEBOOK,
        GOOGLE
    }

    interface Callback {
        FragmentActivity getLoginActivity();
        void onLoggedIn(Source source, Status status, String firstName, String lastName, String email);
    }
}

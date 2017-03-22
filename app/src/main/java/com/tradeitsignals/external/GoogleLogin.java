package com.tradeitsignals.external;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.tradeitsignals.logging.TILogger;
import com.tradeitsignals.utils.Constants;
import com.tradeitsignals.utils.StringUtils;

import java.io.Serializable;

/**
 * Created by Kostyantin on 6/4/2016.
 */
public class GoogleLogin
        implements ExternalLogin,
                   GoogleApiClient.OnConnectionFailedListener,
                   GoogleApiClient.ConnectionCallbacks {

    private final static String LOG_TAG = "#GoogleLogin";
    public final static int RC_SIGN_IN = 22;

    private final Callback mCallback;

    private final GoogleSignInOptions mOptions;
    private final GoogleApiClient mGoogleApiClient;
    private ConnectionResult mConnectionResult;

    private boolean mIntentInProgress;
    private boolean attainedProfileData = false;

    public GoogleLogin(Context context, Callback callback) {
        mCallback = callback;

        mOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, mOptions)
                .build();
    }

    public GoogleSignInOptions getOptions() {
        return mOptions;
    }

    public void setIntentInProgress(boolean intentInProgress) {
        this.mIntentInProgress = intentInProgress;
    }

    public void onActivityResult(Intent data) {
        mIntentInProgress = false;

        if (mGoogleApiClient.isConnected()) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            getProfileInformation(result);
        } else if(!mGoogleApiClient.isConnecting()) {
            mGoogleApiClient.connect();
        } else {
            TILogger.getLog().d(LOG_TAG, "google api client is connecting");
        }
    }

    public void login(Activity activity) {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        TILogger.getLog().e(LOG_TAG,
                "Error while connecting google api client for login, code: "
                        + result.getErrorCode() + ", message: "
                        + result.getErrorMessage(), true, true);

        mConnectionResult = result;
        if (!mIntentInProgress) {
            if (!result.hasResolution()) {
                onError();
                return;
            }

            resolveSignInError();
        }
    }

    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(mCallback.getLoginActivity(), RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        } else {
            onError();
        }
    }

    private void onError() {
        FragmentActivity activity = mCallback.getLoginActivity();
        if(activity != null && !activity.isDestroyed()) {
            mIntentInProgress = true;
            ErrorDialogFragment dialogFragment = new ErrorDialogFragment();
            Bundle args = new Bundle();
            args.putInt(Constants.ERROR_CODE, mConnectionResult.getErrorCode());
            args.putSerializable(Constants.LISTENER, new DialogInteractionsListener() {
                @Override
                public void onDismissed() {
                    mIntentInProgress = false;
                    TILogger.getLog().e(LOG_TAG, "Error dialog dismissed", false, true);
                }
            });
            dialogFragment.setArguments(args);
            dialogFragment.show(activity.getSupportFragmentManager(), "errordialog");
        }
        mCallback.onLoggedIn(Source.GOOGLE, Status.ERROR, null, null, null);
    }

    @Override
    public void onConnected(Bundle bundle) {
        if(mCallback != null && !attainedProfileData) {
            login(mCallback.getLoginActivity());
        }
    }

    private void getProfileInformation(GoogleSignInResult result) {
        if(result.isSuccess()) {
            GoogleSignInAccount acc = result.getSignInAccount();
            if(acc != null) {
                attainedProfileData = true;
                String firstName = acc.getGivenName();
                firstName = StringUtils.isEmpty(firstName) ? acc.getDisplayName() : firstName;
                String lastName = acc.getFamilyName();
                String email = acc.getEmail();
                mCallback.onLoggedIn(Source.GOOGLE, Status.SUCCESS, firstName, lastName, email);
            }
        }
        if(!attainedProfileData) {
            mCallback.onLoggedIn(Source.GOOGLE, Status.NO_PROFILE, null, null, null);
        }
    }

    private interface DialogInteractionsListener extends Serializable {
        void onDismissed();
    }

    @Override
    public void onConnectionSuspended(int i) {
        TILogger.getLog().d(LOG_TAG, "connection suspended, connecting again");
        mGoogleApiClient.connect();
    }

    /* A fragment to display an error dialog */
    public final static class ErrorDialogFragment extends DialogFragment {
        public ErrorDialogFragment() { }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int errorCode = this.getArguments().getInt(Constants.ERROR_CODE);
            return GoogleApiAvailability.getInstance().getErrorDialog(
                    this.getActivity(), errorCode, RC_SIGN_IN
            );
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            DialogInteractionsListener listener = (DialogInteractionsListener)
                    this.getArguments().getSerializable(Constants.LISTENER);

            listener.onDismissed();
        }
    }
}

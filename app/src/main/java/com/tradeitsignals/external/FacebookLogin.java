package com.tradeitsignals.external;

import android.content.Intent;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.tradeitsignals.logging.TILogger;
import com.tradeitsignals.ui.activities.RegistrationFlowActivity;
import com.tradeitsignals.utils.CommonUtils;
import com.tradeitsignals.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by Kostyantin on 6/4/2016.
 */
public class FacebookLogin implements ExternalLogin,
        FacebookCallback<LoginResult> {

    private final Callback mCallback;
    private final CallbackManager mCallbackManager;

    public FacebookLogin(Callback callback) {
        mCallback = callback;
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, this);

        checkPreviousLogin();
    }

    private void checkPreviousLogin() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        Profile profile = Profile.getCurrentProfile();
        if(accessToken != null && profile != null && !accessToken.isExpired()) {
            makeGraphRequest(profile, accessToken);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void setPermissions(LoginButton loginButton) {
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        final Profile profile = Profile.getCurrentProfile();

        if(profile != null) {
            makeGraphRequest(profile, loginResult.getAccessToken());
        } else {
            TILogger.getLog().e(RegistrationFlowActivity.LOG_TAG,
                    "could not got current profile from facebook");
            mCallback.onLoggedIn(Source.FACEBOOK, Status.NO_PROFILE, null, null, null);
        }
    }

    @Override
    public void onCancel() {
        TILogger.getLog().w(RegistrationFlowActivity.LOG_TAG, "User canceled login");
        mCallback.onLoggedIn(Source.FACEBOOK, Status.CANCELED, null, null, null);
    }

    @Override
    public void onError(FacebookException error) {
        TILogger.getLog().e(RegistrationFlowActivity.LOG_TAG, "Facebook login error: "
                + error.getMessage(), error, false, true);
        mCallback.onLoggedIn(Source.FACEBOOK, Status.ERROR, null, null, null);
    }

    private void makeGraphRequest(final Profile profile, AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        String email = "";
                        if(object != null) {
                            try {
                                email = object.getString("email");
                            } catch (JSONException exception) {
                                TILogger.getLog().e(RegistrationFlowActivity.LOG_TAG,
                                        "Failure while trying to get email from facebook graph json object",
                                        exception, false, true);
                            }
                        }

                        if(StringUtils.isEmpty(email)) {
                            mCallback.onLoggedIn(Source.FACEBOOK, Status.NO_EMAIL, profile.getFirstName(),
                                    profile.getLastName(), null);
                        } else {
                            mCallback.onLoggedIn(Source.FACEBOOK, Status.SUCCESS, profile.getFirstName(),
                                    profile.getLastName(), email);
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "email");
        request.setParameters(parameters);
        request.executeAsync();
    }

}

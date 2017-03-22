package com.tradeitsignals.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.tradeitsignals.R;
import com.tradeitsignals.external.ExternalLogin;
import com.tradeitsignals.external.FacebookLogin;
import com.tradeitsignals.external.GoogleLogin;
import com.tradeitsignals.external.analytics.AppContentViewTracker;
import com.tradeitsignals.ui.styleholders.FacebookLoginButtonStyleHolder;
import com.tradeitsignals.utils.Constants;

import java.util.Arrays;

public class ExternalLoginFragment extends Fragment {

//    private final static int NEXT_LOGIN_BUTTON_ANIM_DELAY_MILLIS = 1000;
    
    private GoogleLogin googleLogin;
    private FacebookLogin facebookLogin;
    private ExternalLogin.Callback mCallback;

    private ProgressBar pbLoader;
    private LoginButton btnFacebookLogin;
    private SignInButton btnGoogleLogin;

    public ExternalLoginFragment() {
        // Required empty public constructor
    }

    public static ExternalLoginFragment newInstance() {
        ExternalLoginFragment fragment = new ExternalLoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        facebookLogin = new FacebookLogin(mCallback);
        googleLogin = new GoogleLogin(getContext(), mCallback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_external_login, container, false);

        pbLoader = (ProgressBar) v.findViewById(R.id.pb_loader);

        btnFacebookLogin = (LoginButton) v.findViewById(R.id.btn_facebook_login);
        btnFacebookLogin.setFragment(this);

        btnGoogleLogin = (SignInButton) v.findViewById(R.id.btn_google_login);
        btnGoogleLogin.setScopes(googleLogin.getOptions().getScopeArray());
        btnGoogleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleLogin.login(getActivity());
                toggleLoader();
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        facebookLogin.setPermissions(btnFacebookLogin);
        new FacebookLoginButtonStyleHolder(getContext()).applyStyle(btnFacebookLogin);

        AppContentViewTracker.track("External Login", Constants.CONTENT_REGISTRATION, "");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == GoogleLogin.RC_SIGN_IN) {
            if(resultCode == Activity.RESULT_OK) {
                googleLogin.onActivityResult(data);
            } else {
                googleLogin.setIntentInProgress(false);
                toggleLoader();
            }
        } else {
            facebookLogin.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void showLoginButtons() {
        pbLoader.setVisibility(View.GONE);

        Context context = getContext();
        if(context != null) {
            Animation slideInAnim = AnimationUtils.loadAnimation(context, R.anim.slide_in_bottom);

            btnFacebookLogin.startAnimation(slideInAnim);
            btnGoogleLogin.startAnimation(slideInAnim);
        }
        btnFacebookLogin.setVisibility(View.VISIBLE);
        btnGoogleLogin.setVisibility(View.VISIBLE);
    }

    public void toggleLoader() {
        if (pbLoader.getVisibility() == View.VISIBLE) {
            pbLoader.setVisibility(View.GONE);
            btnGoogleLogin.setVisibility(View.VISIBLE);
            btnFacebookLogin.setVisibility(View.VISIBLE);
        } else {
            pbLoader.setVisibility(View.VISIBLE);
            btnGoogleLogin.setVisibility(View.INVISIBLE);
            btnFacebookLogin.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ExternalLogin.Callback) {
            mCallback = (ExternalLogin.Callback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

}

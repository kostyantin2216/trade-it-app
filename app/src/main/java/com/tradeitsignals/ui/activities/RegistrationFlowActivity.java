package com.tradeitsignals.ui.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Patterns;
import android.widget.Toast;

import com.tradeitsignals.R;
import com.tradeitsignals.external.ExternalLogin;
import com.tradeitsignals.helpers.RegistrationHelper;
import com.tradeitsignals.logging.TILogger;
import com.tradeitsignals.synchronization.SynchronizableTables;
import com.tradeitsignals.ui.components.AnimatedTradeItLogo;
import com.tradeitsignals.ui.fragments.ExternalLoginFragment;
import com.tradeitsignals.ui.fragments.NumberCheckerFragment;
import com.tradeitsignals.ui.fragments.TermsAndConditionsFragment;
import com.tradeitsignals.utils.PermissionManager;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by Kostyantin on 5/25/2016.
 *
 */
public class RegistrationFlowActivity extends SynchronizationActivity
        implements ExternalLogin.Callback,
                   NumberCheckerFragment.NumberVerificationCallback,
                   TermsAndConditionsFragment.TermsAndConditionsListener,
                   RegistrationHelper.RegistrationListener,
                   AnimatedTradeItLogo.TradeItLogoAnimationCallback {

    private final static String DEFAULT_PASSWORD = "123456";
    public final static String LOG_TAG = "#RegistrationFlow";

    private PermissionManager mPermissionManager;;

    private AnimatedTradeItLogo mAnimatedLogo;

    private ExternalLoginFragment mExternalLoginFragment;
    private NumberCheckerFragment mNumberCheckerFragment;
    private TermsAndConditionsFragment mTermsAndConditionsFragment;

    private ExternalLogin.Source mLoginSource;
    private String mFirstName;
    private String mLastName;
    private String mEmail;
    private String mTelephone;

    private boolean mIsTermsAndConditionsShown = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_flow);

        mPermissionManager = new PermissionManager(getEventTracker());

        initLogoAnimation();
        initFragments();

        getEventTracker().trackRegistrationBegin();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, mExternalLoginFragment)
                .commitAllowingStateLoss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mExternalLoginFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initFragments() {
        if(mExternalLoginFragment == null) {
            mExternalLoginFragment = new ExternalLoginFragment();
        }
        if(mNumberCheckerFragment == null) {
            mNumberCheckerFragment = new NumberCheckerFragment();
        }
        if(mTermsAndConditionsFragment == null) {
            mTermsAndConditionsFragment = new TermsAndConditionsFragment();
        }
    }

    private void initLogoAnimation() {
        mAnimatedLogo = (AnimatedTradeItLogo) findViewById(R.id.animated_trade_it_logo);
        mAnimatedLogo.setAnimationCallback(this);
        mAnimatedLogo.startWelcomeAnimation();
    }

    @Override
    public SynchronizableTables[] getTablesToSynchronize() {
        return new SynchronizableTables[] {SynchronizableTables.COUNTRY};
    }

    @Override
    public void onSynchronizationStart() {
        // Here to stop loader fragment from showing up on the whole screen
    }

    @Override
    public void onSynchronizationComplete() {
        if(!this.isDestroyed()) {
            mExternalLoginFragment.showLoginButtons();
        }
    }

    @Override
    public void onLoggedIn(ExternalLogin.Source source, ExternalLogin.Status status, String firstName, String lastName, String email) {
        TILogger.getLog().d(LOG_TAG, "Log in status: " + status.name() + ", firsName: " + firstName
                + ", lastName: " + lastName + ", email: " + email);
        mLoginSource = source;
        switch (status) {
            case SUCCESS:
                this.mFirstName = firstName;
                this.mLastName = lastName;
                this.mEmail = email;
                showNumberChecker();
                getEventTracker().trackExternalLogin(source.name());
                break;
            case NO_EMAIL:
                this.mFirstName = firstName;
                this.mLastName = lastName;
                getEmails();
                break;
            case NO_PROFILE:
            case CANCELED:
            case ERROR:
                onRegistrationError("Could not log in to external account due to LoginStatus: " + status.name());
                break;
        }
    }

    private void getEmails() {
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        Account[] accounts = AccountManager.get(this).getAccounts();
        ArrayList<String> emails = new ArrayList<>();
        for (Account account : accounts) {
            String email = account.name;
            if (emailPattern.matcher(email).matches()) {
                emails.add(email);
            }
        }

        if(!emails.isEmpty()) {
            mEmail = emails.get(0);
            showNumberChecker();
        } else {
            onRegistrationError("Could not find any emails for user");
        }
    }

    private void showNumberChecker() {
        if(!isDestroyed()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, mNumberCheckerFragment)
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public void onNumberVerified(String telephone) {
        this.mTelephone = telephone;
        if(mNumberCheckerFragment.acceptedTermsAndConditions()) {
            registerUser();
        } else {
            onTermsConditionsClicked();
        }
    }

    @Override
    public void onTermsConditionsClicked() {
        if(!mIsTermsAndConditionsShown) {
            mIsTermsAndConditionsShown = true;
            getEventTracker().trackTermsConditionsShow();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, mTermsAndConditionsFragment)
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public void requestVerificationPermission(boolean explained, PermissionManager.PermissionRequest request, String... permissions) {
        mPermissionManager.requestPermissions(this, explained, request, permissions);
    }

    @Override
    public void termsAndConditionsAccepted() {
        mNumberCheckerFragment.termsAndConditionsAccepted();
        if(mIsTermsAndConditionsShown) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(mTermsAndConditionsFragment)
                    .commitAllowingStateLoss();
            mIsTermsAndConditionsShown = false;
        }

        if(mNumberCheckerFragment.isNumberVerified()) {
            registerUser();
        }
    }

    @Override
    public void termsAndConditionsDeclined() {
        mNumberCheckerFragment.termsAndConditionsDeclined();

        Toast.makeText(this, R.string.need_to_accept_terms_and_conditions, Toast.LENGTH_LONG).show();
    }

    private void registerUser() {
        new RegistrationHelper().registerUser(
                mFirstName,
                mLastName,
                DEFAULT_PASSWORD,
                mEmail,
                mTelephone,
                this
        );
    }

    @Override
    public void onRegistrationComplete() {
        getEventTracker().trackRegistrationComplete(mLoginSource != null ? mLoginSource.name() : "");
        mAnimatedLogo.startFinishAnimation();
    }

    @Override
    public void onRegistrationError(String msg) {
        TILogger.getLog().e(LOG_TAG, "registration failed! msg: " + msg, false, true);
        Toast.makeText(this, R.string.could_not_register, Toast.LENGTH_LONG).show();
        hideLoaderDialog();
    }

    @Override
    public void onWelcomeAnimationFinished() { /* do nothing */ }

    @Override
    public void onFinishAnimationFinished() {
        startActivity(new Intent(this, SignalsActivity.class));
        finish();
    }

    @Override
    public FragmentActivity getLoginActivity() {
        return this;
    }
}

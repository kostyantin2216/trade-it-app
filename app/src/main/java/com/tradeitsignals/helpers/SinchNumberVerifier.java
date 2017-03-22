package com.tradeitsignals.helpers;

import android.content.Context;

import com.sinch.verification.CodeInterceptionException;
import com.sinch.verification.Config;
import com.sinch.verification.InvalidInputException;
import com.sinch.verification.PhoneNumberUtils;
import com.sinch.verification.ServiceErrorException;
import com.sinch.verification.SinchVerification;
import com.sinch.verification.Verification;
import com.sinch.verification.VerificationListener;
import com.tradeitsignals.R;
import com.tradeitsignals.logging.TILogger;

/**
 * Created by Kostyantin on 9/14/2016.
 */
public class SinchNumberVerifier implements TelephoneNumberVerifier {

    private final static String LOG_TAG = "#" + SinchNumberVerifier.class.getSimpleName();

    private final Config mConfig;
    private Verification mSmsVerification;

    private boolean mVerified = false;
    private boolean mVerificationOnGoing = false;

    public SinchNumberVerifier(Context context) {
        String key = context.getString(R.string.sinch_key);
        mConfig = SinchVerification
                .config()
                .applicationKey(key)
                .context(context.getApplicationContext())
                .build();
    }

    @Override
    public void verify(String number, final Callback callback) {
        if(!mVerificationOnGoing) {
            mVerificationOnGoing = true;
            final long start = System.currentTimeMillis();

            String countryIso = PhoneNumberUtils.getDefaultCountryIso(mConfig.getContext());
            final String phoneNumberInE164 = PhoneNumberUtils.formatNumberToE164(number, countryIso);
            mSmsVerification = SinchVerification.createSmsVerification(mConfig, phoneNumberInE164,
                    new VerificationListener() {
                        @Override
                        public void onInitiated() { }

                        @Override
                        public void onInitiationFailed(Exception e) {
                            mVerificationOnGoing = false;
                            TILogger.getLog().e(LOG_TAG, "Failed to initialize number verification", e, false, true);
                            callback.onNumberVerificationFailure(phoneNumberInE164, false, start, "InitiationFailed", e);
                        }

                        @Override
                        public void onVerified() {
                            mVerificationOnGoing = false;
                            mVerified = true;
                            callback.onNumberVerified(phoneNumberInE164, System.currentTimeMillis() - start);
                        }

                        @Override
                        public void onVerificationFailed(Exception e) {
                            mVerificationOnGoing = false;
                            String reason;
                            boolean promptManualVerification = false;
                            if (e instanceof InvalidInputException) {
                                reason = "InvalidInputException";
                                TILogger.getLog().e(LOG_TAG, "(" + phoneNumberInE164
                                        + ")Incorrect number provided during phone number verification", e, false, true);
                            } else if (e instanceof ServiceErrorException) {
                                reason = "ServiceErrorException";
                                TILogger.getLog().e(LOG_TAG, "(" + phoneNumberInE164
                                        + ")ServiceErrorException", e, false, true);
                            } else if (e instanceof CodeInterceptionException) {
                                promptManualVerification = true;
                                reason = "CodeInterceptionException";
                                TILogger.getLog().e(LOG_TAG, "(" + phoneNumberInE164
                                        + ")Intercepting the verification code automatically failed, input the code manually with verify()", e, false, true);
                            } else {
                                reason = "Unknown";
                                TILogger.getLog().e(LOG_TAG, "(" + phoneNumberInE164
                                        + ")Number verification failed due to unexpected exception", e, false, true);
                            }

                            callback.onNumberVerificationFailure(phoneNumberInE164, promptManualVerification, start, reason, e);
                        }
                    }
            );
            mSmsVerification.initiate();
        }
    }

    @Override
    public void verifySmsCode(String code) {
        mSmsVerification.verify(code);
    }

    @Override
    public boolean isVerified() {
        return mVerified;
    }

    @Override
    public boolean isVerificationOnGoing() {
        return mVerificationOnGoing;
    }

}

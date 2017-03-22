package com.tradeitsignals.helpers;

/**
 * Created by Kostyantin on 9/14/2016.
 */
public interface TelephoneNumberVerifier {

    void verify(String number, Callback callback);
    void verifySmsCode(String code);

    boolean isVerified();
    boolean isVerificationOnGoing();

    interface Callback {
        void onNumberVerified(String number, long timeToVerify);

        void onNumberVerificationFailure(String number, boolean promptManualVerification, long waitTime, String error, Exception exception);
    }
}

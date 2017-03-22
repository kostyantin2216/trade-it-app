package com.tradeitsignals.ui.fragments;

import android.Manifest;
import android.animation.LayoutTransition;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.tradeitsignals.R;
import com.tradeitsignals.datamodel.ImmutableKeyValue;
import com.tradeitsignals.external.analytics.AppEvent;
import com.tradeitsignals.external.analytics.AppEventTracker;
import com.tradeitsignals.helpers.SinchNumberVerifier;
import com.tradeitsignals.helpers.TelephoneNumberVerifier;
import com.tradeitsignals.ui.components.InputStatusView;
import com.tradeitsignals.utils.PermissionManager;
import com.tradeitsignals.utils.UIUtils;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Created by ThorneBird on 5/7/2016.
 */
public class NumberCheckerFragment extends BaseFragment
        implements View.OnClickListener,
                   TermsAndConditionsFragment.TermsAndConditionsListener,
                   TelephoneNumberVerifier.Callback {

    private final static String LOG_TAG = "#" + NumberCheckerFragment.class.getSimpleName();
    private final static String[] VERIFICATION_PERMISSIONS = new String[]
            {Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS};

    private NumberVerificationCallback numberVerificationCallback;
    private TelephoneNumberVerifier mNumberVerifier;

    private ViewHolder mView;
    private InputViewState mInputState;

    private String telephone;

    public static NumberCheckerFragment newInstance() {
        return new NumberCheckerFragment();
    }

    private final static class ViewHolder {
        public final InputStatusView isvStatus;
        public final EditText etNumber;
        public final CheckBox cbTermsCond;
        public final TextView tvManualVerificationInstruction;
        public final Button btnSave;
        public final Button btnEnterNumber;

        private ViewHolder(Context context, View view) {
            isvStatus = (InputStatusView) view.findViewById(R.id.isv_telephone_verifier_status);
            etNumber = (EditText) view.findViewById(R.id.et_number);
            Drawable wrappedDrawable = DrawableCompat.wrap(etNumber.getBackground());
            DrawableCompat.setTint(wrappedDrawable.mutate(), context.getResources().getColor(R.color.white));
            etNumber.setBackgroundDrawable(wrappedDrawable);
            cbTermsCond = (CheckBox) view.findViewById(R.id.cb_agree_to_terms);
            tvManualVerificationInstruction = (TextView) view.findViewById(R.id.tv_manual_verification_instruction);
            btnSave = (Button) view.findViewById(R.id.btn_save);
            btnEnterNumber = (Button) view.findViewById(R.id.btn_reenter_number);
        }
    }

    private enum InputViewState {
        REQUEST_NUMBER,
        REQUEST_SMS_CODE,;

        private static void alter(ViewHolder view, InputViewState viewState) {
            switch (viewState) {
                case REQUEST_NUMBER:
                    view.etNumber.setText("");
                    view.etNumber.setHint(R.string.enter_number);
                    view.cbTermsCond.setVisibility(View.VISIBLE);
                    view.tvManualVerificationInstruction.setVisibility(View.GONE);
                    view.btnEnterNumber.setVisibility(View.GONE);
                    break;

                case REQUEST_SMS_CODE:
                    view.etNumber.setText("");
                    view.etNumber.setHint(R.string.enter_sms_code);
                    view.cbTermsCond.setVisibility(View.GONE);
                    view.tvManualVerificationInstruction.setVisibility(View.VISIBLE);
                    view.btnEnterNumber.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    private enum StatusViewState {
        GONE,
        AWAIT_VERIFY,
        VERIFY_FAILED,
        VERIFY_COMPLETE;

        private static void alter(ViewHolder view, StatusViewState viewState) {
            switch (viewState) {
                case GONE:
                    view.isvStatus.hide();
                    break;

                case AWAIT_VERIFY:
                    view.isvStatus.loading();
                    break;

                case VERIFY_FAILED:
                    view.isvStatus.failure();
                    break;

                case VERIFY_COMPLETE:
                    view.isvStatus.success();
                    break;
            }
        }
    }

    private void changeViewState(StatusViewState statusState) {
        StatusViewState.alter(mView, statusState);
    }

    private void changeViewState(InputViewState inputState) {
        InputViewState.alter(mView, inputState);
        mInputState = inputState;
    }

    private void changeViewState(InputViewState inputState, StatusViewState statusState) {
        StatusViewState.alter(mView, statusState);
        InputViewState.alter(mView, inputState);
        mInputState = inputState;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mNumberVerifier = new SinchNumberVerifier(getContext().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_number_checker, container, false);

        view.setLayoutTransition(new LayoutTransition());

        mView = new ViewHolder(getContext(), view);
        mView.cbTermsCond.setOnClickListener(this);
        mView.btnSave.setOnClickListener(this);
        mView.btnEnterNumber.setOnClickListener(this);

        changeViewState(InputViewState.REQUEST_NUMBER, StatusViewState.GONE);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            numberVerificationCallback = (NumberVerificationCallback) context;
        }catch (ClassCastException ex){
            throw new IllegalArgumentException("Could not cast context to NumberVerificationCallback", ex);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        numberVerificationCallback = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                if(numberVerificationCallback != null) {
                    final String input = mView.etNumber.getText().toString();
                    if (mInputState == InputViewState.REQUEST_SMS_CODE) {
                        verifySmsCode(input);
                    } else {
                        verifyTelephoneNumber(input);
                    }
                }
                break;

            case R.id.cb_agree_to_terms:
                if(numberVerificationCallback != null) {
                    numberVerificationCallback.onTermsConditionsClicked();
                }
                break;

            case R.id.btn_reenter_number:
                changeViewState(InputViewState.REQUEST_NUMBER, StatusViewState.GONE);
                mView.etNumber.setText(telephone);
                break;
        }
    }

    private void verifySmsCode(String code) {
        UIUtils.showSnackbar(getActivity(), R.string.verifying_sms_code);
        changeViewState(StatusViewState.AWAIT_VERIFY);
        mNumberVerifier.verifySmsCode(code);
    }

    private void verifyTelephoneNumber(String telephone) {
        if (telephone.length() > 6
                && !mNumberVerifier.isVerified()
                && !mNumberVerifier.isVerificationOnGoing()) {
            preVerificationCheck(telephone);
        } else {
            String message;
            if (telephone.length() < 8) {
                message = getString(R.string.invalid_telephone);
            } else if (mNumberVerifier.isVerificationOnGoing()) {
                message = getString(R.string.verification_ongoing);
            } else {
                message = getString(R.string.error);
            }
            UIUtils.showSnackbar(getActivity(), message);
        }
    }

    private void preVerificationCheck(final String telephone) {
        final PermissionManager.PermissionRequest permissionRequest = new PermissionManager.PermissionRequest() {
            @Override
            public void onPermissionGranted(String[] permissions) {
                sendVerificationCheck(telephone);
            }

            @Override
            public void onPermissionDenied(String[] permissions) {
                onPermissionExplanationRequest(permissions);
            }

            @Override
            public void onPermissionExplanationRequest(String[] permissions) {
                final PermissionManager.PermissionRequest request = this;
                new MaterialDialog.Builder(getContext())
                        .cancelable(false)
                        .content(R.string.sms_permission_explanation)
                        .contentGravity(GravityEnum.CENTER)
                        .positiveText(R.string._continue)
                        .negativeText(R.string.close)
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if(which == DialogAction.POSITIVE) {
                                    numberVerificationCallback.requestVerificationPermission(true, request, VERIFICATION_PERMISSIONS);
                                } else {
                                    sendVerificationCheck(telephone);
                                }
                                dialog.dismiss();
                            }
                        })
                        .show();
            }

        };
        numberVerificationCallback.requestVerificationPermission(false, permissionRequest, VERIFICATION_PERMISSIONS);
    }

    private void sendVerificationCheck(String telephone) {
        UIUtils.showSnackbar(getActivity(), R.string.verifying_number);
        changeViewState(StatusViewState.AWAIT_VERIFY);

        trackEvent(new AppEvent.Builder(AppEventTracker.EVENT_VERIFY_NUMBER)
                .sendToAll()
                .label(AppEventTracker.LABEL_NUMBER, telephone)
                .build());

        mNumberVerifier.verify(telephone, NumberCheckerFragment.this);
    }

    @Override
    public void onNumberVerified(String number, long timeToVerify) {
        changeViewState(StatusViewState.VERIFY_COMPLETE);

        trackEvent(new AppEvent.Builder(AppEventTracker.EVENT_VERIFY_NUMBER_SUCCESS)
                                .sendToAll()
                                .labels(new ImmutableKeyValue<>(AppEventTracker.LABEL_NUMBER, number),
                                        new ImmutableKeyValue<>(AppEventTracker.LABEL_SECONDS, String.valueOf(
                                                TimeUnit.MILLISECONDS.toSeconds(timeToVerify)
                                        )))
                                .build());

        if (numberVerificationCallback != null) {
            numberVerificationCallback.onNumberVerified(number);
        }
    }

    @Override
    public void onNumberVerificationFailure(String number, boolean promptManualVerification, long waitTime, String reason, Exception exception) {
        trackEvent(new AppEvent.Builder(AppEventTracker.EVENT_VERIFY_NUMBER_ERROR)
                                .sendToAll()
                                .labels(new ImmutableKeyValue<>(AppEventTracker.LABEL_NUMBER, number),
                                        new ImmutableKeyValue<>(AppEventTracker.LABEL_REASON, reason),
                                        new ImmutableKeyValue<>(AppEventTracker.LABEL_SECONDS, String.valueOf(
                                                TimeUnit.MILLISECONDS.toSeconds(waitTime)
                                        )))
                                .build());

        Activity activity = getActivity();
        if(activity != null && !activity.isDestroyed()) {
            if(promptManualVerification) {
                telephone = mView.etNumber.getText().toString();
                changeViewState(InputViewState.REQUEST_SMS_CODE, StatusViewState.GONE);
            } else {
                changeViewState(StatusViewState.VERIFY_FAILED);
                if(mInputState == InputViewState.REQUEST_NUMBER) {
                    UIUtils.showSnackbar(activity, R.string.phone_verification_error);
                } else {
                    UIUtils.showSnackbar(activity, R.string.sms_code_verification_error);
                }
            }
        }
    }

    public boolean isNumberVerified() {
        return mNumberVerifier.isVerified();
    }

    public interface NumberVerificationCallback {
        void onNumberVerified(String telephone);
        void onTermsConditionsClicked();
        void requestVerificationPermission(boolean explained, PermissionManager.PermissionRequest request, String... permissions);
    }

    @Override
    public void termsAndConditionsAccepted() {
        mView.cbTermsCond.setChecked(true);
    }

    @Override
    public void termsAndConditionsDeclined() {
        mView.cbTermsCond.setChecked(false);
    }

    public boolean acceptedTermsAndConditions() {
        return mView.cbTermsCond.isChecked();
    }

}

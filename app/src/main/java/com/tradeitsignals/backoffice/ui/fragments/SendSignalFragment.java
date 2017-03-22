package com.tradeitsignals.backoffice.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.tradeitsignals.R;
import com.tradeitsignals.datamodel.data.Signal;
import com.tradeitsignals.logging.TILogger;
import com.tradeitsignals.parse.ParseHelper;
import com.tradeitsignals.rest.ServerAPI;
import com.tradeitsignals.rest.api.APIFactory;
import com.tradeitsignals.utils.UIUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendSignalFragment extends Fragment {

    private TimePicker tpExpiryTime;
    private Spinner spCurrencyPair;
    private RadioGroup rgCallOrPut;
    private EditText etRate;
    private EditText etNotificationMessage;
    private Spinner spChannels;

     public static SendSignalFragment newInstance() {
        SendSignalFragment fragment = new SendSignalFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_send_signal, container, false);

        tpExpiryTime = (TimePicker) v.findViewById(R.id.tp_expiry_time);
        spCurrencyPair = (Spinner) v.findViewById(R.id.s_currency_pairs);
        rgCallOrPut = (RadioGroup) v.findViewById(R.id.rg_call_put);
        etRate = (EditText) v.findViewById(R.id.et_rate);
        etNotificationMessage = (EditText) v.findViewById(R.id.et_notification_message);
        spChannels = (Spinner) v.findViewById(R.id.sp_channels);

        Button btnSendSignal = (Button) v.findViewById(R.id.b_send_signal);
        btnSendSignal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commitSignal();
            }
        });

        return v;
    }

    private void commitSignal() {
        String formResult = validForm();
        if(formResult.equals(getString(R.string.valid_input))) {
            Signal signal = getSignalFromForm();
            saveSignalOnServer(signal);
        } else {
            UIUtils.showError(getActivity(), formResult);
        }
    }

    private void sendSignal(Signal signal) {
        JSONObject jsonSignal = null;
        try {
            jsonSignal = signal.toJSONParseSignal();
        } catch (JSONException e) {
            TILogger.getLog().e("Could not convert " + signal + " into a JSONObject.", e);
        }
        if(jsonSignal != null) {
            String message = etNotificationMessage.getText().toString().trim();
            String channel = spChannels.getSelectedItem().toString();

            TILogger.getLog().i("Sending signal: " + jsonSignal + "\nMessage: " + message + ", Channel: " + channel);

            final HashMap<String, Object> params = new HashMap<>();
            params.put("signal", jsonSignal);
            params.put("message", message);
            params.put("channel", channel);
            ParseCloud.callFunctionInBackground(ParseHelper.FUNC_SEND_SIGNAL, params, new FunctionCallback<String>() {
                @Override
                public void done(String result, ParseException e) {
                    if (e != null || !result.equals("success")) {
                        UIUtils.showError(getActivity(), "ParseException code " + e.getCode() + ", Failure in cloud code function: "
                                + ParseHelper.FUNC_SEND_SIGNAL + ", Result message: " + result + ", Could not send JSONObject: "
                                + params.get("signal").toString(), e, true);
                    }
                }
            });
        } else {
            UIUtils.showError(getActivity(), "Failure sending signal");
        }
    }

    private void saveSignalOnServer(Signal signal) {
        ServerAPI<Signal> api = APIFactory.getInstance().getSignalAPI();
        api.post(signal).enqueue(new Callback<Signal>() {
            @Override
            public void onResponse(Call<Signal> call, Response<Signal> response) {
                Signal signal = response.body();
                if(signal != null) {
                    sendSignal(signal);
                    TILogger.getLog().d("Successfully saved signal on server");
                } else {
                    String responseMsg = response.message();
                    UIUtils.showError(getActivity(), "Failure saving signal on server due to: "+ responseMsg);
                    TILogger.getLog().e("#SENDSIGNAL", "Could not save signal on server due to: " + responseMsg);
                }
            }

            @Override
            public void onFailure(Call<Signal> call, Throwable t) {
                UIUtils.showError(getActivity(), "Failure saving signal on server");
                TILogger.getLog().e("#SENDSIGNAL", "Could not save signal on server", t, false, true);
            }
        });
    }

    private Signal getSignalFromForm() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, tpExpiryTime.getCurrentHour());
        cal.set(Calendar.MINUTE, tpExpiryTime.getCurrentMinute());
        Date expiryTime = new Date(cal.getTimeInMillis());

        String currencyPair = spCurrencyPair.getSelectedItem().toString();

        double rate = Double.parseDouble(etRate.getText().toString());

        boolean isCall = rgCallOrPut.getCheckedRadioButtonId() == R.id.rb_call;

        return new Signal(expiryTime, currencyPair, rate, isCall);
    }

    private String validForm() {
        // Expiry time validation.
        Calendar now = Calendar.getInstance();
        int currentHour = now.get(Calendar.HOUR_OF_DAY);
        int currentMinute = now.get(Calendar.MINUTE);
        int chosenHour = tpExpiryTime.getCurrentHour();
        int chosenMinute = tpExpiryTime.getCurrentMinute();
        if(chosenHour < currentHour || (chosenHour == currentHour && chosenMinute < currentMinute)) {
            return getString(R.string.invalid_expiry_time);
        }

        // Rate validation.
        String rateStr = etRate.getText().toString();
        try {
            if(Double.parseDouble(rateStr) < 0) {
                return getString(R.string.invalid_rate);
            }
        } catch(NumberFormatException e) {
            return getString(R.string.invalid_rate);
        }

        return getString(R.string.valid_input);
    }

}

package com.tradeitsignals.ui.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.tradeitsignals.R;
import com.tradeitsignals.datamodel.data.Broker;
import com.tradeitsignals.ui.components.BrokerView;

/**
 * Created by Kostyantin on 6/14/2015.
 */
@Deprecated
public class BrokerDialogFragment extends DialogFragment {

    private Broker broker;

    public static BrokerDialogFragment newInstance(Broker broker) {
        BrokerDialogFragment frag = new BrokerDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable("broker", broker);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        broker = (Broker) getArguments().get("broker");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        BrokerView v = (BrokerView) inflater.inflate(R.layout.layout_broker, container, false);

        v.showBroker(broker);

        Button btnLeave = (Button) v.findViewById(R.id.b_broker_leave);
        btnLeave.setVisibility(View.VISIBLE);
        btnLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return v;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

}
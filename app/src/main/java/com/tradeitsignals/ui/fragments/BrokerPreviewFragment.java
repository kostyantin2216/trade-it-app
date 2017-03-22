package com.tradeitsignals.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tradeitsignals.R;
import com.tradeitsignals.datamodel.data.Broker;
import com.tradeitsignals.ui.components.BrokerView;

public class BrokerPreviewFragment extends Fragment {

	private Broker broker;

	public static BrokerPreviewFragment newInstance(Broker broker) {
		BrokerPreviewFragment frag = new BrokerPreviewFragment();
		Bundle args = new Bundle();
		args.putParcelable("broker", broker);
		frag.setArguments(args);
		return frag;
	}
	
	public BrokerPreviewFragment() { }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		broker = getArguments().getParcelable("broker");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_brokers, container, false);

		BrokerView brokerView = (BrokerView) v.findViewById(R.id.broker_view);
		if(broker != null) {
			brokerView.showBroker(broker);
		}

		return v;
	}

}

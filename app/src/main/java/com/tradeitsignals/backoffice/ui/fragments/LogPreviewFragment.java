package com.tradeitsignals.backoffice.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tradeitsignals.R;
import com.tradeitsignals.logging.TILogger;

/**
 * Created by Kostyantin on 8/23/2015.
 */
public class LogPreviewFragment extends Fragment {

    private String log;

    public static LogPreviewFragment newInstance(String log) {
        LogPreviewFragment frag = new LogPreviewFragment();
        Bundle args = new Bundle();
        args.putString("log", log);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null && getArguments().containsKey("log")) {
            log = getArguments().getString("log");
        } else {
            if(TILogger.isDebug()) {
                TILogger.getLog().w("LogPreviewFragment onCreate() getArguments() "
                        + getArguments() == null ? "returns null" : "does not conatin key: log");
            }
            log = getString(R.string.empty_error_log);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_log_preview, container, false);

        TextView tvErrorLog = (TextView) v.findViewById(R.id.tv_error_log);
        tvErrorLog.setText(log);

        return v;
    }
}

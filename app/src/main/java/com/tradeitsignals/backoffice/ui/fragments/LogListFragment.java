package com.tradeitsignals.backoffice.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tradeitsignals.R;
import com.tradeitsignals.logging.TILogger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class LogListFragment extends Fragment {

    private OnLogSelectionListener mCallbacks;

    private List<String> errorLogsList;

    public static LogListFragment newInstance(ArrayList<String> savedLogsFileNames) {
        LogListFragment frag = new LogListFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("log_names", savedLogsFileNames);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null && getArguments().containsKey("log_names")) {
            errorLogsList = getArguments().getStringArrayList("log_names");
        } else {
            if(TILogger.isDebug()) {
                TILogger.getLog().w("LogListFragment getArguments() "
                        + (getArguments() == null ? "returns null" : "does not contain key: log_names")
                        + ".\nNothing to show in error log list!\n");
            }
            errorLogsList = Collections.emptyList();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);

        ListView lvLogs = (ListView) v.findViewById(R.id.lv_list);
        if(errorLogsList.size() > 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, errorLogsList);

            lvLogs.setAdapter(adapter);
            lvLogs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mCallbacks.onLogSelected(errorLogsList.get(position));
                }
            });
        } else {
            TextView tvEmptyList = (TextView) v.findViewById(R.id.tv_empty_list);
            tvEmptyList.setVisibility(View.VISIBLE);
            lvLogs.setVisibility(View.GONE);
        }

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (OnLogSelectionListener) activity;
        } catch (ClassCastException e) {
            TILogger.getLog().e("Could not cast Activity: [" + activity.toString()
                    + "] to OnLogSelectionListener in LogListFragment.onAttach", e);
        }
    }

    public interface OnLogSelectionListener extends Serializable {
        void onLogSelected(String logName);
    }
}

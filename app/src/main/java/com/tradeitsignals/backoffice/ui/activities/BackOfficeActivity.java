package com.tradeitsignals.backoffice.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.tradeitsignals.R;
import com.tradeitsignals.SignalsApplication;
import com.tradeitsignals.backoffice.ui.fragments.AdminSettingsFragment;
import com.tradeitsignals.logging.LogAccess;
import com.tradeitsignals.backoffice.ui.fragments.LogListFragment;
import com.tradeitsignals.backoffice.ui.fragments.LogPreviewFragment;
import com.tradeitsignals.backoffice.ui.fragments.SendSignalFragment;
import com.tradeitsignals.logging.TILogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BackOfficeActivity extends AppCompatActivity  {

   // private ViewHolder mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bo_activity_back_office);
        initView();

        /** Await for input in {@link BackOfficeActivity#optionClickListener} **/
    }

    // ACTIVITY VIEW HOLDER
    // ==================================================

    private void initView() {
        ListView optionsList = (ListView) findViewById(R.id.lv_dev_options);
        optionsList.setOnItemClickListener(optionClickListener);
        optionsList.setAdapter(new SimpleAdapter(
                this,
                DeveloperOption.toAdapterData(this),
                android.R.layout.simple_list_item_1,
                new String[] {"option"},
                new int[] {android.R.id.text1}
        ));

     //   mView = new ViewHolder(optionsList);
    }

   /* private final static class ViewHolder {
        public final ListView optionsList;

        private ViewHolder(ListView lvOptionsList) {
            this.optionsList = lvOptionsList;
        }
    }*/

    // FRAGMENTS
    // =================================================

    private void showFragment(DeveloperOption option) {
        String tag = option.name();
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentByTag(tag);

        if(fragment == null) {
            switch (option) {
                case SEND_SIGNAL:
                    fragment = SendSignalFragment.newInstance();
                    break;
                case SETTINGS:
                    fragment = AdminSettingsFragment.newInstance();
                    break;
            }
        }

        if(fragment != null) {
            fm.beginTransaction()
              .add(android.R.id.content, fragment, tag)
              .addToBackStack(tag)
              .commit();
        }
    }

    // DEVELOPER OPTIONS
    // ==================================================

    private final AdapterView.OnItemClickListener optionClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            showFragment(DeveloperOption.values()[i]);
        }
    };

    private enum DeveloperOption {
        SEND_SIGNAL(R.string.send_signal),
        SETTINGS(R.string.action_settings);

        private final int nameResId;

        DeveloperOption(int nameResId) {
            this.nameResId = nameResId;
        }

        static List<Map<String, String>> toAdapterData(Context context) {
            List<Map<String, String>> options = new ArrayList<>();
            for(DeveloperOption option : values()) {
                Map<String, String> optionsMap = new HashMap<>();
                optionsMap.put("option", context.getString(option.nameResId));
                options.add(optionsMap);
            }
            return options;
        }

    }

}

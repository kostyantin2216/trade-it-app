package com.tradeitsignals.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.afollestad.materialdialogs.MaterialDialog;
import com.tradeitsignals.helpers.TIDictionary;
import com.tradeitsignals.synchronization.SynchronizableTables;
import com.tradeitsignals.synchronization.SynchronizationExecutor;
import com.tradeitsignals.synchronization.SynchronizationListener;
import com.tradeitsignals.ui.fragments.LoaderFragment;

/**
 * Created by Kostyantin on 1/31/2016.
 */
public abstract class SynchronizationActivity extends BaseActivity implements SynchronizationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SynchronizableTables[] tables = getTablesToSynchronize();

        if(tables != null && tables.length > 0) {
            SynchronizationExecutor executor = new SynchronizationExecutor(SynchronizableTables.createTasks(tables), this);
            executor.execute();
        } else {
            onSynchronizationComplete();
        }

    }

    public abstract SynchronizableTables[] getTablesToSynchronize();

    @Override
    public void onSynchronizationStart() {
        showLoaderDialog(TIDictionary.translate("loading"));
    }

    @Override
    public void onSynchronizationProgressUpdate(String taskName, Integer progress) {
    }

    @Override
    public void onSynchronizationComplete() {
        hideLoaderDialog();
    }

}

package com.tradeitsignals.synchronization;

import android.os.AsyncTask;

import com.tradeitsignals.datamodel.DataModelObject;
import com.tradeitsignals.rest.HTTPStatusCodes;

import com.tradeitsignals.logging.TILogger;
import com.tradeitsignals.rest.queries.QueryCriteria;
import com.tradeitsignals.rest.queries.QueryRestrictions;
import com.tradeitsignals.rest.queries.ServiceQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Kostyantin on 1/8/2016.
 */
public class SynchronizationExecutor {

    private final static String LOG_TAG = "#SynchronizationExecutor";

    private final static TILogger log = TILogger.getLog();

    private List<SynchronizationTask> tasks;
    private SynchronizationListener listener;

    private SynchronizationTimestamps syncTimestamps;

    public SynchronizationExecutor(SynchronizationTask task, SynchronizationListener listener) {
        this.tasks = new ArrayList<>();
        this.tasks.add(task);
        this.listener = listener;
        this.syncTimestamps = new SynchronizationTimestamps();
    }

    public SynchronizationExecutor(List<SynchronizationTask> tasks, SynchronizationListener listener) {
        this.tasks = tasks;
        this.listener = listener;
        this.syncTimestamps = new SynchronizationTimestamps();
    }

    public void execute() {
        new TaskExecutor().execute(tasks.toArray(new SynchronizationTask[tasks.size()]));
    }

    public void setTasks(List<SynchronizationTask> tasks) {
        this.tasks = tasks;
    }

    public void setListener(SynchronizationListener listener) {
        this.listener = listener;
    }

    private class TaskExecutor extends AsyncTask<SynchronizationTask, String, Void> {

        int incrementBy;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listener.onSynchronizationStart();
        }

        @Override
        protected Void doInBackground(SynchronizationTask... tasks) {
            prepareSynchronizationTasks(tasks);
            incrementBy = tasks.length > 0 ? tasks.length / 100 : 0;
            for(SynchronizationTask task : tasks) {
                synchronize(task);
                publishProgress(task.dao.getTableName());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            syncTimestamps.save();
            listener.onSynchronizationComplete();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            listener.onSynchronizationProgressUpdate(values[0], incrementBy);
        }
    }

    private void prepareSynchronizationTasks(SynchronizationTask... tasks) {
        for(SynchronizationTask task : tasks) {
            String tableName = task.dao.getTableName();
            SynchronizableTables table = SynchronizableTables.get(tableName);
            if(table != null) {
                Long timestamp = syncTimestamps.get(tableName);
                if(timestamp == null) {
                    timestamp = 0L;
                }
                if(timestamp == 0L) {
                    task.operation = SynchronizationOperation.INSERT_OR_UPDATE;
                }
                if(task.criteria == null) {
                    task.criteria = QueryCriteria.create();
                }
                ServiceQuery query = QueryRestrictions.ge(table.timestampProperty, timestamp);
                TILogger.getLog().d("Adding timestamp query to " + task.api.getApiName() + " api with query: " + query);
                task.criteria.add(query);
            }
        }

    }

    private void synchronize(SynchronizationTask task) {
        log.i(LOG_TAG, " -- STARTING SYNC WITH TABLE " + task.dao.getTableName());

        log.d(LOG_TAG, "Querying " + task.api.getApiName() + " with criteria " + task.criteria);

        List<DataModelObject> objs = processQuery(task);
        if(objs != null) {
            log.d(LOG_TAG, "Synchronizing " + objs.size() + " objects");
            for(DataModelObject obj : objs) {
                boolean dbContainsItem = task.dao.contains(obj.getId());
                boolean success = true;
                if(dbContainsItem && task.operation != SynchronizationOperation.INSERT_ONLY) {
                    success = task.dao.update(obj);
                } else if(!dbContainsItem && task.operation != SynchronizationOperation.UPDATE_ONLY) {
                    success = task.dao.insert(obj);
                }
                if(!success) {
                    log.w(LOG_TAG, "Could not insert/update item in task: " + task);
                }
            }
            syncTimestamps.update(task.dao.getTableName(), System.currentTimeMillis());
        } else {
            log.w(LOG_TAG, "Synchronization task aborting, no objects returned from query...");
        }
    }

    private List<DataModelObject> processQuery(SynchronizationTask task) {
        Call<List<DataModelObject>> call = task.executeQuery();
        if(call != null) {
            try {
                Response<List<DataModelObject>> response = call.execute();
                int responseCode = response.code();
                if (responseCode == HTTPStatusCodes.OK) {
                    return response.body();
                } else if (responseCode == HTTPStatusCodes.NO_CONTENT) {
                    log.d(LOG_TAG, "No results with query criteria " + task.criteria);
                }
            } catch (IOException e) {
                log.e(LOG_TAG, "Error during request to server with task: " + task);
            }
        }

        return null;
    }
}

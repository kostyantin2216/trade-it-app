package com.tradeitsignals.synchronization;

/**
 * Created by Kostyantin on 1/13/2016.
 */
public interface SynchronizationListener {

    void onSynchronizationStart();
    void onSynchronizationProgressUpdate(String taskTable,Integer progress);
    void onSynchronizationComplete();

}

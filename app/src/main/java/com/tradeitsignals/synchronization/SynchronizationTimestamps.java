package com.tradeitsignals.synchronization;

import com.tradeitsignals.utils.Constants;
import com.tradeitsignals.utils.PrefUtils;

import java.util.Map;

/**
 * Created by Kostyantin on 2/1/2016.
 */
public class SynchronizationTimestamps {

    private Map<String, Long> syncTimestampsMap;

    public SynchronizationTimestamps() {
        load();
    }

    public void load() {
        syncTimestampsMap = PrefUtils.getPrefSynchronizationTimestampsAsMap();
        for(SynchronizableTables table : SynchronizableTables.values()) {
            String tableName = table.tableName;
            if(syncTimestampsMap.containsKey(tableName)) {
                long updateTimestamp = syncTimestampsMap.get(tableName);
                if(updateTimestamp > 0) {
                    syncTimestampsMap.put(tableName, updateTimestamp - Constants.TIMESTAMP_1_DAY);
                }
            } else {
                syncTimestampsMap.put(tableName, 0L);
            }
        }
    }

    public void save() {
        String timestamps = "";
        for(Map.Entry<String, Long> entry : syncTimestampsMap.entrySet()) {
            timestamps += entry.getKey() + "=" + entry.getValue() + "&";
        }
        timestamps = timestamps.substring(0, timestamps.length() - 1);
        PrefUtils.setPrefSynchronizationTimestamps(timestamps);
    }

    public Long get(String tableName) {
        return syncTimestampsMap.get(tableName);
    }

    public void update(String tableName, Long timestamp) {
        update(tableName, timestamp, false);
    }

    public void update(String tableName, Long timestamp, boolean save) {
        if(syncTimestampsMap.containsKey(tableName)) {
            syncTimestampsMap.put(tableName, timestamp);
            if(save) {
                save();
            }
        }
    }

    public void reset() {
        for(Map.Entry<String, Long> entry : syncTimestampsMap.entrySet()) {
            entry.setValue(0L);
        }
        save();
    }

}

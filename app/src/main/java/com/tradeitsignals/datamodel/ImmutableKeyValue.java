package com.tradeitsignals.datamodel;

/**
 * Created by Kostyantin on 9/14/2016.
 */
public class ImmutableKeyValue<K, V> {
    public final K key;
    public final V value;

    public ImmutableKeyValue(K key, V value) {
        this.key = key;
        this.value = value;
    }
}

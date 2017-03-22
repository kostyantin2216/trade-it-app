package com.tradeitsignals.datamodel.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kostyantin on 9/19/2015.
 */
public enum ContentPageType {

    EDUCATION(0);

    private final static Map<Integer, ContentPageType> typesCache = new HashMap(){{
            put(EDUCATION.id, EDUCATION);
    }};

    private int id;

    ContentPageType(int id) {
        this.id = id;
    }

    public int id() {
        return id;
    }

    public static ContentPageType findById(int id) {
        return typesCache.get(id);
    }
}

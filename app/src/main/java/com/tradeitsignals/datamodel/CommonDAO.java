package com.tradeitsignals.datamodel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Kostyantin on 1/8/2016.
 */
public interface CommonDAO<T extends DataModelObject> {

    boolean insert(T obj);

    /**
     * @param objs Objects to insert into database
     * @return amount of objects unsuccessfully inserted
     */
    int insert(List<T> objs);
    T findById(Integer id);
    List<T> findByQuery(String query, String[] values);
    List<T> findByProperty(String property, String value);

    /**
     * @return List&lt;T&gt; All objects of current type or an empty immutable List
     *         if no objects can be found.
     */
    List<T> findAll();
    List<T> findAll(String orderBy);
    boolean update(T obj);

    /**
     * @param objs Objects to update in database.
     * @return Amount of objects failed to update.
     */
    int updateAll(List<T> objs);
    boolean delete(String id);
    boolean contains(Serializable id);
    int count();
    int countQuery(String query, String[] selectionArgs);
    String getTableName();

}

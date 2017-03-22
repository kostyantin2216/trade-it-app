package com.tradeitsignals.rest.queries;

/**
 * Created by Kostyantin on 1/14/2016.
 */
public class QueryRestrictions {

    /**
     * Apply an "equal" constraint to the named property
     *
     * @param property The name of the property
     * @param value The value to use in comparison
     *
     * @return ServiceQuery
     */
    public static ServiceQuery eq(String property, String value) {
        return new ServiceQuery(property, "=", value);
    }

    public static ServiceQuery eq(String property, Integer value) {
        return new ServiceQuery(property, "=", String.valueOf(value));
    }

    /**
     * Apply a "not equal" constraint to the named property
     *
     * @param property The name of the property
     * @param value The value to use in comparison
     *
     * @return ServiceQuery
     */
    public static ServiceQuery ne(String property, String value) {
        return new ServiceQuery(property, "!=", value);
    }

    /**
     * Apply a "greater than" constraint to the named property
     *
     * @param property The name of the property
     * @param value The value to use in comparison
     *
     * @return ServiceQuery
     */
    public static ServiceQuery gt(String property, String value) {
        return new ServiceQuery(property, ">", value);
    }

    public static ServiceQuery gt(String property, Long value) {
        return new ServiceQuery(property, ">", String.valueOf(value));
    }

    /**
     * Apply a "less than" constraint to the named property
     *
     * @param property The name of the property
     * @param value The value to use in comparison
     *
     * @return ServiceQuery
     */
    public static ServiceQuery lt(String property, String value) {
        return new ServiceQuery(property, "<", value);
    }

    /**
     * Apply a "greater then or equal" constraint to the named property
     *
     * @param property The name of the property
     * @param value The value to use in comparison
     *
     * @return ServiceQuery
     */
    public static ServiceQuery ge(String property, String value) {
        return new ServiceQuery(property, ">=", value);
    }

    public static ServiceQuery ge(String property, Long value) {
        return new ServiceQuery(property, ">=", String.valueOf(value));
    }

    /**
     * Apply a "less then or equal" constraint to the named property
     *
     * @param property The name of the property
     * @param value The value to use in comparison
     *
     * @return ServiceQuery
     */
    public static ServiceQuery le(String property, String value) {
        return new ServiceQuery(property, "<=", value);
    }

}

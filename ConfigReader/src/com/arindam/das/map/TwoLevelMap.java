package com.arindam.das.map;

/**
 * Created by arindam.das on 18/12/15.
 */
public interface TwoLevelMap {
    Object getObject(Object group, Object key, Object override);
    void setObject(Object group, Object key, Object override, Object value);
    void clear();
}

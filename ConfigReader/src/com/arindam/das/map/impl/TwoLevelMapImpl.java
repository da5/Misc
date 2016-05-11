package com.arindam.das.map.impl;

import com.arindam.das.map.TwoLevelMap;
import javafx.util.Pair;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by arindam.das on 18/12/15.
 */
public class TwoLevelMapImpl implements TwoLevelMap {

    Map<String, Map<Pair<String, String>, Object>> configMap;

    public TwoLevelMapImpl() {
        configMap = new HashMap<String, Map<Pair<String, String>, Object>>();
    }

    @Override
    public Object getObject(Object group, Object key, Object override) {
        Object returnValue = null;
        try{
            returnValue = configMap.get(group).get(new Pair<String, String>(String.valueOf(key),String.valueOf(override)));
        }catch (Exception e){
            System.out.println("Caught exception!");
        }
        return returnValue;
    }

    @Override
    public void setObject(Object group, Object key, Object override, Object value) {
        if (configMap.get(group) == null) {
            configMap.put(String.valueOf(group), new HashMap<Pair<String, String>, Object>());
        }
        configMap.get(group).put(new Pair<String, String>(String.valueOf(key),String.valueOf(override)),value);
    }

    @Override
    public void clear() {
        configMap.clear();
    }
}

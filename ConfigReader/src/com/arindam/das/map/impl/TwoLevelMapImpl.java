package com.arindam.das.map.impl;

import com.arindam.das.map.TwoLevelMap;
import javafx.util.Pair;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by arindam.das on 18/12/15.
 */
public class TwoLevelMapImpl implements TwoLevelMap {

    Map<String, Map<String,Map<String, String>>> configMap;

    public TwoLevelMapImpl() {
        configMap = new HashMap<>();
    }

    @Override
    public Object getObject(Object group, Object key, Object override) {
        Object returnValue = null;
        try{
            returnValue = configMap.get(group).get(String.valueOf(key)).get(String.valueOf(override));
        }catch (Exception e){
            System.out.println("Caught exception!");
        }
        return returnValue;
    }

    @Override
    public void setObject(Object group, Object key, Object override, Object value) {
        if (configMap.get(group) == null) {
            configMap.put(String.valueOf(group), new HashMap<>());
            configMap.get(group).put(String.valueOf(key), new HashMap<>());
            configMap.get(group).get(String.valueOf(key)).put(String.valueOf(override),String.valueOf(value));
        }else if(configMap.get(group).get(String.valueOf(key)) == null){
            configMap.get(group).put(String.valueOf(key), new HashMap<>());
            configMap.get(group).get(String.valueOf(key)).put(String.valueOf(override),String.valueOf(value));
        }else {
            configMap.get(group).get(String.valueOf(key)).put(String.valueOf(override),String.valueOf(value));
        }
    }

    @Override
    public void clear() {
        configMap.clear();
    }
}

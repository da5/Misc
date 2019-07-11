package practice.Miscellaneous;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class TimeMap {

    Map<String, NavigableMap<Integer, String>> map;
    /** Initialize your data structure here. */
    public TimeMap() {
        map = new HashMap<>();
    }

    public void set(String key, String value, int timestamp) {
        map.putIfAbsent(key, new TreeMap<>());
        map.get(key).put(timestamp, value);
    }

    public String get(String key, int timestamp) {
        NavigableMap<Integer, String> timeMap = map.get(key);
        String result = "";
        if(timeMap!=null) {
            if(timeMap.containsKey(timestamp)) {
                result = timeMap.get(timestamp);
            } else {
                Integer lowerTime = timeMap.lowerKey(timestamp);
                if(lowerTime!=null) {
                    result = timeMap.get(lowerTime);
                }
            }
        }
        return result;
    }
}

class TimeMapDriver {
    public static void main(String[] args) {
        TimeMap timeMap = new TimeMap();
        timeMap.set("ctondw","ztpearaw",1);
        timeMap.set("vrobykydll","hwliiq",2);
        timeMap.set("gszaw","ztpearaw",3);
        timeMap.set("ctondw","gszaw",4);
        System.out.println(timeMap.get("gszaw",5));
        System.out.println(timeMap.get("ctondw",6));
    }
}

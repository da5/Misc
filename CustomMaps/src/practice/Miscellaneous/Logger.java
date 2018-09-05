package practice.Miscellaneous;

import java.util.HashMap;
import java.util.Map;

public class Logger {
    Map<String, Integer> map;
    public Logger() {
        map = new HashMap<>();
    }

    /** Returns true if the message should be printed in the given timestamp, otherwise returns false.
     If this method returns false, the message will not be printed.
     The timestamp is in seconds granularity. */
    public boolean shouldPrintMessage(int timestamp, String message) {
        boolean response = false;
        Integer last = map.get(message);
        if(last == null){
            map.put(message, timestamp);
            response = true;
        }else if(Math.abs(timestamp-last)>10){
            map.put(message, timestamp);
            response = true;
        }
        return response;
    }
}

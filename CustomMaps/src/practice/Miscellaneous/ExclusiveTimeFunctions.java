package practice.Miscellaneous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/*
https://leetcode.com/problems/exclusive-time-of-functions/
 */
public class ExclusiveTimeFunctions {
    static class Event {
        int id;
        int type;
        int time;

        Event(int i, String t, int ti) {
            id = i;
            type = t.equals("start")? 1: 2;
            time = ti;
        }
    }

    Event createEvent(String log) {
        String[] parts = log.split(":");
        return new Event(
                Integer.parseInt(parts[0]),
                parts[1], Integer.parseInt(parts[2])
        );
    }

    public int[] exclusiveTime(int n, List<String> logs) {
        int[] counter = new int[n];
        Deque<Integer> stack = new LinkedList<>();
        List<Event> list = new ArrayList<>();
        Event event = null;
        for(String log: logs) {
            Event eventObj = createEvent(log);
            if(event==null) {
                list.add(eventObj);
                event = eventObj;
                stack.push(event.id);
            } else {
                Event next = eventObj;
                if(next.type==1) {
                    if(!stack.isEmpty()) {
                        list.add(new Event(stack.peek(), "end", next.time-1));
                    }
                    list.add(next);
                    stack.push(next.id);
                } else {
                    list.add(next);
                    stack.pop();
                    if(!stack.isEmpty()) {
                        list.add(new Event(stack.peek(), "start", next.time+1));
                    }
                }
                event = next;
            }
        }

        for(int i=0; i<list.size(); i+=2) {
            int id = list.get(i).id;
            counter[id] += list.get(i+1).time-list.get(i).time+1;
        }

        return counter;
    }
}

class ExclusiveTimeFunctionsDriver {
    public static void main(String[] args) {
        ExclusiveTimeFunctions exclusiveTimeFunctions = new ExclusiveTimeFunctions();
        List<String> list = new ArrayList<>();
        list.add("0:start:0");
        list.add("1:start:2");
        list.add("1:end:5");
        list.add("0:end:6");

        List<String> list1 = Arrays.asList("0:start:0","0:start:2","0:end:5","1:start:6","1:end:6","0:end:7");

        for(int n: exclusiveTimeFunctions.exclusiveTime(2, list1)) {
            System.out.println(n);
        }
    }
}
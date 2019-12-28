package practice.Miscellaneous;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TaskScheduler {
    //https://leetcode.com/problems/task-scheduler/
    public int leastInterval(char[] tasks, int n) {
        int total = tasks.length;
        char[] counter = new char[26];
        for(char c: tasks) {
            counter[c-'A']++;
        }
        Arrays.sort(counter);
        int max = counter[25];
        int gaps = (max-1)*n;
        for(int i=24; i>=0; i--) {
            gaps -= Math.min(counter[i], max-1);
        }
        if(gaps>0) {
            total += gaps;
        }
        return total;
    }
}

class TaskSchedulerDriver {
    public static void main(String[] args) {
        TaskScheduler taskScheduler = new TaskScheduler();
        char[] tasks = {'A','A','A','B','B','B'};
        Map<Integer, Integer> map = new HashMap<>();

//        String str = "I live     in    Amsterdam!";
        System.out.println(taskScheduler.leastInterval(tasks, 2));
    }
}
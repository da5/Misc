package practice.Miscellaneous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class Solution {



    static void generate(List<String> result, String str, int open, int close){
        if(close == 0){
            result.add(str);
        }
        if(open > 0){
            generate(result, str+"(", open-1, close);
        }
        if(open<close){
            generate(result, str+")", open, close-1);
        }
    }

    static void generate(int n){
        Queue<String> previous = new LinkedList<>();
        Queue<String> queue;
        previous.offer("");
        while(n>0){
            queue = new LinkedList<>();
            for(String str: previous){
                queue.offer(str+"()");
                queue.offer("("+str+")");
                queue.offer("()"+str);
            }
            n--;
            previous = queue;
        }
        Set<String> result = previous.stream().collect(Collectors.toSet());
    }

    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        generate(result, "", n, n);
        return result;
    }

    static List<Integer> getOccurences(String str){
        Map<Character, Integer> map = new HashMap<>();
        for(int i=0;i<str.length(); i++){
            Character c = str.charAt(i);
            if(!map.containsKey(c)){
                map.put(c, 0);
            }
            int ctr = map.get(c);
            map.put(c, ctr+1);
        }
        List<Integer> list = map.values().stream().collect(Collectors.toList());
        Collections.sort(list);
        return list;
    }

    static boolean isIsomorphic(String s, String t) {
        List<Integer> l1 =    getOccurences(s);
        List<Integer> l2 =    getOccurences(t);
        if(l1.size()!= l2.size()){
            while(!l1.isEmpty()){
                if(l1.remove(0)!=l2.remove(0)){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static String longestWord(String[] words) {
        Arrays.sort(words, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if(o1.length()!=o2.length()){
                    return o1.length()-o2.length();
                }
                return o1.compareTo(o2);
            }
        });
        Set<String> set = new HashSet<>();
        int n = words.length;
        for(int i = 0; i<n; i++){
            set.add(words[i]);
        }
        for(int i = n-1; i>=0; i--){
            String word = words[i];
            int j;
            for(j = word.length()-1; j>0; j--){
                if(!set.contains(word.substring(0, j))){
                    break;
                }
            }
            if(j == 0 ){
                return word;
            }
        }
        return null;
    }

    static class Task{
        String name;
        int instance;
        int lastExec;
        public Task(String n, int i, int l){name = n; instance = i; lastExec = l;}
    }

    static Queue<Task> gettaskQueue(char[] tasks){
        Map<String, Integer> taskCount = new HashMap<>();

        for(int i = 0; i<tasks.length; i++){
            String task = ""+tasks[i];
            if(!taskCount.containsKey(task)){
                taskCount.put(task, 0);
            }
            int count = taskCount.get(task);
            taskCount.put(task, count+1);
        }

        Queue<Task> queue = new PriorityQueue<>(new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return o2.instance - o1.instance;
            }
        });

        for(Map.Entry<String, Integer> entry: taskCount.entrySet()){
            queue.offer(new Task(entry.getKey(), entry.getValue(), -1));
        }
        return queue;
    }

    public static int leastInterval(char[] tasks, int n) {

        Queue<Task> taskQueue = gettaskQueue(tasks);
        int interval = 0;
        while(!taskQueue.isEmpty()){
            Queue<Task> current = new LinkedList<>();
            for(int i =0; i<=n; i++){
                String t = "idle";
                if(!taskQueue.isEmpty()){
                    Task task = taskQueue.poll();
                    if(task.instance > 1){
                        task.instance--;
                        current.offer(task);
                    }
                    t = task.name;
                }
                interval++;
                if(taskQueue.isEmpty() && current.isEmpty()){
                    break;
                }

//                System.out.println(t + " at " + interval);
            }
            for(Task task: current){
                taskQueue.offer(task);
            }
        }

        return interval;
    }

    static Map<String, Integer> init(){
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("MMM", 3000);
        map.put("MM", 2000);
        map.put("M", 1000);
        map.put("CM", 900);
        map.put("DCCC", 800);
        map.put("DCC", 700);
        map.put("DC", 600);
        map.put("D", 500);
        map.put("CD", 400);
        map.put("CCC", 300);
        map.put("CC", 200);
        map.put("C", 100);
        map.put("XC", 90);
        map.put("LXXX", 80);
        map.put("LXX", 70);
        map.put("LX", 60);
        map.put("L", 50);
        map.put("XL", 40);
        map.put("XXX", 30);
        map.put("XX", 20);
        map.put("X", 10);
        map.put("IX", 9);
        map.put("VIII", 8);
        map.put("VII", 7);
        map.put("VI", 6);
        map.put("V", 5);
        map.put("IV", 4);
        map.put("III", 3);
        map.put("II", 2);
        map.put("I", 1);
        return map;
    }

    public static int romanToInt(String s) {
        Map<String, Integer> map = init();
        int result = 0;
        for(Map.Entry<String, Integer> entry: map.entrySet()){
            String prefix = entry.getKey();
            if(s.indexOf(prefix) == 0){
                result += entry.getValue();
                s = s.substring(prefix.length());
            }

        }
        return result;
    }

    public static void main(String[] args){



//        String S = "-";
//
//        String str = licenseKeyFormatting(S, 1);
//
//        isIsomorphic("add", "egg");

//        String[] strings = {"yo","ew","fc","zrc","yodn","fcm","qm","qmo","fcmz","z","ewq","yod","ewqz","y"};
//        System.out.println(longestWord(strings));

//        char[] tasks = {'A','A','A','A','A','A','B','C','D','E','F','G'};
//        System.out.println(leastInterval(tasks, 2));

        System.out.println(romanToInt("MMCCCXCIX"));

    }
}


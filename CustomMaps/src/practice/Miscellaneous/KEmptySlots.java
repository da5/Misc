package practice.Miscellaneous;


import java.util.NavigableSet;
import java.util.TreeSet;

public class KEmptySlots {
    public static int kEmptySlots(int[] flowers, int k) {
        NavigableSet<Integer> set = new TreeSet<>();
        int result = -1;
        for(int f: flowers){
            set.add(f);
            if(set.size() > 1){
                Integer lower = set.lower(f);
                Integer higher = set.higher(f);
                if(lower!=null && Math.abs(f-lower-1) == k){
                    result = set.size();
                    break;
                }
                if(higher!=null && Math.abs(f-higher-1) == k){
                    result = set.size();
                    break;
                }
            }
        }
        return result;

    }

    public static void main(String[] args) {
//        int[] flowers = {1,3,2};
//        int k = 1;
//        System.out.println(kEmptySlots(flowers, k));

        Logger obj = new Logger();
        System.out.println(obj.shouldPrintMessage(1,"foo"));
        System.out.println(obj.shouldPrintMessage(2,"bar"));
        System.out.println(obj.shouldPrintMessage(3,"foo"));
        System.out.println(obj.shouldPrintMessage(8,"bar"));
        System.out.println(obj.shouldPrintMessage(10,"foo"));
        System.out.println(obj.shouldPrintMessage(11,"foo"));
//        System.out.println(obj.shouldPrintMessage(1,"foo"));
//        System.out.println(obj.shouldPrintMessage(1,"foo"));
//        System.out.println(obj.shouldPrintMessage(1,"foo"));

    }
}

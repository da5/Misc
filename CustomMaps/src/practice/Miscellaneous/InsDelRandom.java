package practice.Miscellaneous;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

/*
Design a data structure that supports all following operations in average O(1) time.
Note: Duplicate elements are allowed.

    insert(val): Inserts an item val to the collection.
    remove(val): Removes an item val from the collection if present.
    getRandom: Returns a random element from current collection of elements. The probability of each element being returned is linearly related to the number of same value the collection contains.

 */
public class InsDelRandom {
    Random random;
    List<Integer> list;
    Map<Integer, LinkedHashSet<Integer>> map;

    public InsDelRandom() {
        random = new Random();
        map = new HashMap<>();
        list = new ArrayList<>();
    }

    /** Inserts a value to the collection. Returns true if the collection did not already contain the specified element. */
    public boolean insert(int val) {
        boolean res = false;
        if(!map.containsKey(val)){
            map.put(val, new LinkedHashSet<>());
            res = true;
        }
        map.get(val).add(list.size());
        list.add(val);
        return res;
    }

    /** Removes a value from the collection. Returns true if the collection contained the specified element. */
    public boolean remove(int val) {
        if(!map.containsKey(val)){
            return false;
        }
        LinkedHashSet<Integer> valIndices = map.get(val);
        int substitute = list.get(list.size()-1);

        if(val == substitute){
            valIndices.remove(list.size()-1);
        }else{
            LinkedHashSet<Integer> substituteIndices = map.get(substitute);
            int valIdx = valIndices.iterator().next();
            list.set(valIdx, substitute);
            substituteIndices.remove(list.size()-1);
            substituteIndices.add(valIdx);
            valIndices.remove(valIdx);
        }

        list.remove(list.size()-1);
        if(valIndices.isEmpty()){
            map.remove(val);
        }
        return true;
    }

    /** Get a random element from the collection. */
    public int getRandom() {
        return list.get(random.nextInt(list.size()));
    }
}

class InsDelRandomDriver {
    public static void main(String[] args){
        InsDelRandom insDelRandom = new InsDelRandom();
        System.out.println(insDelRandom.insert(4));
        System.out.println(insDelRandom.insert(3));
        System.out.println(insDelRandom.insert(4));
        System.out.println(insDelRandom.insert(2));
        System.out.println(insDelRandom.insert(4));
        System.out.println(insDelRandom.remove(4));
        System.out.println(insDelRandom.remove(3));
        System.out.println(insDelRandom.remove(4));
        System.out.println(insDelRandom.remove(4));


    }
}

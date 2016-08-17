package practice.Miscellaneous;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by arindam.das on 07/08/16.
 */
public class Solution {

    static int minimalCost(int n, String[] pairs) {
        Set<String> rods = new HashSet<>();
        List<Set<String>> rodSets = new ArrayList<>();
        for(int i=1; i<=n; i++){
            rods.add(Integer.toString(i));
        }
        for(int i =0; i< pairs.length; i++){
            String[] pair = pairs[i].split(" ");
            rods.remove(pair[0]);
            rods.remove(pair[1]);
            boolean found = false;
            for(Set<String> rodSet: rodSets){
                if(rodSet.contains(pair[0]) || rodSet.contains(pair[1])){
                    rodSet.add(pair[0]);
                    rodSet.add(pair[1]);
                    found = true;
                    break;
                }
            }
            if(!found){
                Set<String> rodSet = new HashSet<>();
                rodSet.add(pair[0]);
                rodSet.add(pair[1]);
                rodSets.add(rodSet);
            }
        }
        int cost = rods.size();
//        System.out.println(rods);
        for(Set<String> rodSet: rodSets){
//            System.out.println(rodSet);
            cost += (int)Math.ceil(Math.sqrt(rodSet.size()));
        }
        return cost;
    }

    public static void main(String[] args){
        System.out.println(minimalCost(10, new String[]{"8 1","5 8","7 3","8 6"}));
    }
}

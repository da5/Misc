package practice.Miscellaneous;

import java.util.*;

/**
 * Created by arindam.das on 06/08/16.
 */
public class CrowdFire {

    static List<List<Integer>> globalSet;

    static  void generateSets_(int[] array, int target, int sum, List<Integer> currentList, int k){
        if(target == sum){
            globalSet.add(new ArrayList<>(currentList));
            return;
        }
        if(k<0){
            return;
        }
        int diff = target - sum;
        int q = diff/array[k];
       for(int i=0; i<=q;i++){
                for(int l =0; l< i; l++){
                    currentList.add(0, array[k]);
                }
                generateSets_(array, target, sum + i*array[k], currentList, k-1);
                for(int l =0; l< i; l++){
                    currentList.remove(0);
                }
        }

    }

    static  void generateSets(int[] array, int target){
        Arrays.sort(array);
        globalSet = new ArrayList<>();
        generateSets_(array, target, 0, new ArrayList<Integer>(), array.length-1);
        for(List<Integer> list: globalSet){
            System.out.println(list);
        }
    }

    public static void main(String args[]){
        int[] array = {2,2,3,6,7};
        int target = 7;
        generateSets(array, target);
    }
}

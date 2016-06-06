package practice.drivers;

import practice.core.CustomLRUEntry;
import practice.core.CustomMaze;

import java.util.*;

/**
 * Created by arindam.das on 11/05/16.
 */
public class DummyDriver {

    private static void print(List<CustomLRUEntry<String, Double>> list){
        System.out.println("Printing list :");
        for(CustomLRUEntry<String,Double> entry: list){
            System.out.print(entry + " ");
        }
        System.out.println("");
    }

    private static void comparatorDemo(){
        List<CustomLRUEntry<String, Double>> list = new ArrayList<>();
        list.add(new CustomLRUEntry<>("Charlie", 2300.22, null, null));
        list.add(new CustomLRUEntry<>("Andy", 60.72, null, null));
        list.add(new CustomLRUEntry<>("Bob", 60.52, null, null));
        list.add(new CustomLRUEntry<>("Edd", 12300.72, null, null));
        list.add(new CustomLRUEntry<>("Dale", 10.62, null, null));
        print(list);
        list.sort(new Comparator<CustomLRUEntry<String, Double>>() {
            @Override
            public int compare(CustomLRUEntry<String, Double> o1, CustomLRUEntry<String, Double> o2) {
                return (o1.getValue() == o2.getValue()) ? 0 : ((o1.getValue() > o2.getValue()) ? 1 : -1);
            }
        });
        print(list);
        Collections.sort(list);
        print(list);
    }

    private static String getAnagramId(String string){
        HashMap<Character, Integer> characterIntegerHashMap = new HashMap<>();
        char[] chars = string.toCharArray();
        for(int i =0 ;i<chars.length; i++){
            Character character = new Character(chars[i]);
            Integer count = (characterIntegerHashMap.containsKey(character))?characterIntegerHashMap.get(character):0;
            characterIntegerHashMap.put(character, count+1);
        }
        String anagramId = "";
        for(Map.Entry<Character, Integer> entry: characterIntegerHashMap.entrySet()){
            anagramId = anagramId + entry.getKey()  + entry.getValue();
        }
        return anagramId;
    }

    private static int GetMaximumSubset(List<String> strings){
        Set<String> uniqueAnagramIdSet = new HashSet<>();
        for(String string: strings){
            uniqueAnagramIdSet.add(getAnagramId(string));
        }
        return uniqueAnagramIdSet.size();
    }

    private static int searchEvenInSortedEvenOddArray(int[] sortedEvenOddArray, int key, int n){
        int low = 0;
        int high = n-1;
        while(low<=high){
            int mid = (low+high)/2;
            int element = sortedEvenOddArray[mid*2];
            if(element<key){
                low = mid +1;
            }else if(element>key){
                high = mid -1;
            }else{
                return mid*2;
            }
        }
        return -1;//in case of failure
    }

    private static int searchOddInSortedEvenOddArray(int[] sortedEvenOddArray, int key, int n){
        int low = 0;
        int high = n-1;

        while(low<=high){
            int mid = (low+high)/2;
            int element = sortedEvenOddArray[mid*2+1];
            if(element<key){
                low = mid +1;
            }else if(element>key){
                high = mid -1;
            }else{
                return mid*2+1;
            }
        }
        return -1;//in case of failure
    }

    private static int searchSortedEvenOddArray(int[] sortedEvenOddArray, int key, int n){//the array sortedEvenOddArray contains elements from 0 to (2n-1)
        if(key%2==0){
            return searchEvenInSortedEvenOddArray(sortedEvenOddArray, key, n);
        }else {
            return searchOddInSortedEvenOddArray(sortedEvenOddArray, key, n);
        }
    }

    public static void main(String[] args){
        List<String> strings = new ArrayList<>();
        strings.add("baba");
        strings.add("abab");
        strings.add("aabb");
        strings.add("abba");

        strings.add("aaab");
        strings.add("aab");
        strings.add("aabc");
        strings.add("aaaa");

        System.out.println(GetMaximumSubset(strings));

        int[] array = {2, 1, 10, 3, 16, 201, 56, 303, 82, 517 , 110, 519};
        System.out.println(searchSortedEvenOddArray(array, 3, array.length/2));


    }
}

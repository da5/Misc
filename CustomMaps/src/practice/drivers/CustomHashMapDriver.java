package practice.drivers;

import practice.core.CustomEntry;
import practice.generic.util.CustomHashMap;
import java.util.Iterator;

/**
 * Created by arinda on 7/26/2015.
 */
public class CustomHashMapDriver {

    public static void main(String[] args){
//        CustomHashMap<String,Integer> customHashMap = new CustomHashMap<String,Integer>(10);
//        customHashMap.put("Andy",100);
//        customHashMap.put("Bob", 400);
//        customHashMap.put("Charlie",1000);
//        customHashMap.put("Dale", 300);
//        customHashMap.put("Ed",900);
//        customHashMap.put("Frank", 700);
//        System.out.println("Get(Andy) : " + customHashMap.get("Andy").toString());
//        customHashMap.displayAll();
//        customHashMap.put("Andy", 200);
//        customHashMap.remove("Bob");
//        customHashMap.put("Bob", 500);
//        Iterator it = customHashMap.entrySet().iterator();
//        customHashMap.remove("Frank");
//
//        System.out.println("Iterating through the HashMap");
//        while (it.hasNext()){
//            CustomHashMap.CustomEntry customEntry = (CustomHashMap.CustomEntry)it.next();
//            System.out.println(customEntry.toString());
//        }

        CustomHashMap<Integer,String> customHashMap = new CustomHashMap<Integer,String>(10);
        customHashMap.put(1, "Andy");
        customHashMap.put(4, "Bob");
        customHashMap.put(3, "Charlie");
        customHashMap.put(5, "Dale");
        customHashMap.put(2, "Ed");
        customHashMap.put(7, "Frank");
        System.out.println("Get(1) : " + customHashMap.get(1).toString());
        System.out.println("Get(9) is null : " + ((customHashMap.get(9)==null)?"true":"false"));
        customHashMap.displayAll();
        customHashMap.put(21, "AndyX");
//        customHashMap.remove(4);
        customHashMap.put(11,"Bob");
        customHashMap.remove(1);
        customHashMap.remove(7);
        customHashMap.displayAll();
        System.out.println("Iterating through the HashMap");
        Iterator it = customHashMap.entrySet().iterator();
        while (it.hasNext()){
            CustomEntry customEntry = (CustomEntry)it.next();
            System.out.println(customEntry.toString());
        }
    }
}

package practice.drivers;

import practice.product.CustomLRU;

/**
 * Created by arinda on 8/8/2015.
 */
public class CustomLRUCacheDriver {
    public static void main(String[] args){
        CustomLRU<Integer,String> customLRU = new CustomLRU<Integer,String>(3);
        customLRU.put(1, "Andy");
        customLRU.put(2, "Bob");
        System.out.print("Fetching key 1: " + customLRU.get(1) + "::");
        customLRU.showLRUcache();
        customLRU.put(3, "Charlie");
        customLRU.put(4, "Dale");
        System.out.print("Fetching key 1: " + customLRU.get(1) + "::");
        customLRU.showLRUcache();
        System.out.print("Fetching key 4: " + customLRU.get(4) + "::");
        customLRU.showLRUcache();
        System.out.print("Fetching key 3: " + customLRU.get(3) + "::");
        customLRU.showLRUcache();
        customLRU.put(2,"Bob");
    }
}

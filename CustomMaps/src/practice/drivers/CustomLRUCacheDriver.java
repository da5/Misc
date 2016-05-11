package practice.drivers;

import practice.product.CustomLRU;

/**
 * Created by arinda on 8/8/2015.
 */
public class CustomLRUCacheDriver {
    public static void main(String[] args){
        CustomLRU<Integer,String> customLRU = new CustomLRU<Integer,String>(3, true);
        customLRU.put(1, "Andy");
        customLRU.put(2, "Bob");
        customLRU.get(1);
        customLRU.put(3, "Charlie");
        customLRU.put(4, "Dale");
        customLRU.put(1, "AndyX");
        customLRU.get(1);
        customLRU.get(4);
        customLRU.get(3);
        customLRU.put(2,"Bob");
    }
}

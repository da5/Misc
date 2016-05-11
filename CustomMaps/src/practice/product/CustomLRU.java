package practice.product;

import practice.core.CustomLRUEntry;
import practice.generic.util.CustomHashMap;

/**
 * Created by arinda on 7/26/2015.
 */
public class CustomLRU<K,V> {
    int capacity;
    int elements;
    CustomLRUEntry<K,V> first,last;
    CustomHashMap<K,CustomLRUEntry<K,V>> customHashMap;

    public CustomLRU(int capacity){
        this.capacity = ((capacity<=2)? 3 : capacity);
        this.elements = 0;
        this.first = null;
        this.last = null;
        customHashMap = new CustomHashMap<>(capacity);
        System.out.println("Cache created with capacity "+capacity);
    }

    public CustomLRUEntry<K,V> get(K key){
        CustomLRUEntry<K,V> customLRUEntry = customHashMap.get(key);
        if(customLRUEntry != null){
            if(first != customLRUEntry){
                if(elements == 2){
                    last = first;
                    customLRUEntry.setNext(last);
                    customLRUEntry.setPrevious(null);
                    last.setPrevious(customLRUEntry);
                    last.setNext(null);
                }else if(elements > 2){
                    customLRUEntry.getPrevious().setNext(customLRUEntry.getNext());
                    if(last!=customLRUEntry){
                        customLRUEntry.getNext().setPrevious(customLRUEntry.getPrevious());
                    }else{
                        last = customLRUEntry.getPrevious();
                    }
                    customLRUEntry.setNext(first);
                    customLRUEntry.setPrevious(null);
                    first.setPrevious(customLRUEntry);
                }
                first = customLRUEntry;
            }
        }
//        showLRUcache();
        return customLRUEntry;
    }

    public void put(K key, V value){
        if(customHashMap.get(key)==null){
            if(elements == capacity){   //evicting the last element
                last.getPrevious().setNext(null);
                customHashMap.remove(last.getKey());
                last = last.getPrevious();
                elements--;
            }
            CustomLRUEntry<K,V> customLRUEntry = new CustomLRUEntry<K,V>(key,value,null,null);
            customHashMap.put(customLRUEntry.getKey(),customLRUEntry);
            if(elements>0){
                customLRUEntry.setNext(first);
                first.setPrevious(customLRUEntry);
            }else{
                last = customLRUEntry;
            }
            first = customLRUEntry;
            elements++;
        }
        showLRUcache();
    }

    public void showLRUcache(){
        CustomLRUEntry<K,V> customLRUEntry = first;
        System.out.print("Cache content :: ");
        while(customLRUEntry!=null){
            System.out.print(customLRUEntry.toString());
            if(first == customLRUEntry){
                System.out.print("* ");
            }else if(last == customLRUEntry){
                System.out.print("+ ");
            }else{
                System.out.print(" ");
            }
            customLRUEntry = customLRUEntry.getNext();
        }
//        System.out.print("***"+first.toString() + ", " + last.toString());
        System.out.println("");
    }
}

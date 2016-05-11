package practice.product;

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
        customHashMap = new CustomHashMap<K,CustomLRUEntry<K,V>>(capacity);
        System.out.println("Cache created with capacity "+capacity);
    }

    public class CustomLRUEntry<K,V>{
        K key;
        V value;
        CustomLRUEntry<K,V> previous, next;
        public CustomLRUEntry(K key, V value, CustomLRUEntry<K,V> previous, CustomLRUEntry<K,V> next){
            this.key = key;
            this.value = value;
            this.previous = previous;
            this.next = next;
        }

        public String toString(){
            return "("+this.key+", "+ this.value+")";
        }
    }

    public CustomLRUEntry<K,V> get(K key){
        CustomLRUEntry<K,V> customLRUEntry = customHashMap.get(key).getValue();
        if(customLRUEntry != null){
            if(first != customLRUEntry){
                if(elements == 2){
                    last = first;
                    customLRUEntry.next = last;
                    customLRUEntry.previous = null;
                    last.previous = customLRUEntry;
                    last.next = null;
                }else if(elements > 2){
                    customLRUEntry.previous.next = customLRUEntry.next;
                    if(last!=customLRUEntry){
                        customLRUEntry.next.previous = customLRUEntry.previous;
                    }else{
                        last = customLRUEntry.previous;
                    }
                    customLRUEntry.next = first;
                    customLRUEntry.previous = null;
                    first.previous = customLRUEntry;
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
                last.previous.next = null;
                customHashMap.remove(last.key);
                last = last.previous;
                elements--;
            }
            CustomLRUEntry<K,V> customLRUEntry = new CustomLRUEntry<K,V>(key,value,null,null);
            customHashMap.put(customLRUEntry.key,customLRUEntry);
            if(elements>0){
                customLRUEntry.next = first;
                first.previous = customLRUEntry;
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
        System.out.print("Cache content .....");
        while(customLRUEntry!=null){
            System.out.print(customLRUEntry.toString() + "|");
            customLRUEntry = customLRUEntry.next;
        }
        System.out.print("***"+first.toString() + ", " + last.toString());
        System.out.println("");
    }
}

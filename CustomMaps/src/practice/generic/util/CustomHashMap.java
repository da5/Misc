package practice.generic.util;

import practice.core.CustomMapEntry;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by arinda on 7/26/2015.
 */
public class CustomHashMap<K,V> {

    int capacity;
    int elements;
    CustomMapEntry<K,V>[] buckets;

    public CustomHashMap(int capacity){
        this.capacity = capacity;
        buckets = new CustomMapEntry[capacity];
        elements = 0;
    }

    private int hash(K key){
        return Math.abs(key.hashCode())%capacity;
    }

    public void put(K key, V value){
        int hash = hash(key);
        CustomMapEntry<K,V> customMapEntry = new CustomMapEntry<K,V>(key, value, null);

        if(buckets[hash] == null){
            buckets[hash] = customMapEntry;
        }else{
            CustomMapEntry<K,V> previous = null;
            CustomMapEntry<K,V> current = buckets[hash];
            boolean inserted = false;
            while(current!=null){
                if(current.getKey().equals(key)){
                    if(previous == null){
                        buckets[hash] = customMapEntry;
                    }else{
                        previous.setNext(customMapEntry);
                        customMapEntry.setNext(current.getNext());
                    }
                    inserted = true;
                    break;
                }
                previous = current;
                current = current.getNext();
            }
            if(!inserted){
                previous.setNext(customMapEntry);
            }
        }
        this.elements++;
    }

    public V get(K key){
        CustomMapEntry<K,V> customMapEntry;
        for( customMapEntry = buckets[hash(key)]; customMapEntry !=null && !customMapEntry.getKey().equals(key); customMapEntry = customMapEntry.getNext());
        return (customMapEntry!=null)?customMapEntry.getValue():null;
    }

    public CustomMapEntry<K,V> remove(K key) {
        CustomMapEntry<K, V> current = buckets[hash(key)];
        CustomMapEntry<K, V> previous = null;
        if(current!=null){
            while (current != null) {
                if(current.getKey().equals(key)){
                    if(previous==null){
                        buckets[hash(key)] = current.getNext();
                    }else{
                        previous.setNext(current.getNext());
                    }
                    break;
                }
                previous = current;
                current = current.getNext();
            }
            this.elements--;
        }
        return current;
    }

    public boolean isEmpty(){
        return (this.elements==0);
    }

    public Set<CustomMapEntry<K,V>> entrySet(){
        Set<CustomMapEntry<K,V>> setOfEntries = null;
        if(!this.isEmpty()){
            setOfEntries = new HashSet<>(this.elements);
            CustomMapEntry<K,V> customMapEntry;
            for(int i =0; i<capacity;i++){
                customMapEntry = buckets[i];
                while (customMapEntry !=null){
                    setOfEntries.add(customMapEntry);
                    customMapEntry = customMapEntry.getNext();
                }
            }
        }
        return  setOfEntries;
    }

    public void displayAll(){
        System.out.println("Printing the HashMap");
        for(int i =0;i<capacity;i++){
            CustomMapEntry<K,V> customMapEntry = buckets[i];
            System.out.print("Bucket "+ i + " : ");
            while (customMapEntry !=null){
                System.out.print(customMapEntry.toString() + " -> ");
                customMapEntry = customMapEntry.getNext();
            }
            System.out.println("NULL");
        }
    }
}

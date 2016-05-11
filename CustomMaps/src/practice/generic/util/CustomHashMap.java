package practice.generic.util;

import practice.core.CustomEntry;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by arinda on 7/26/2015.
 */
public class CustomHashMap<K,V> {

    int capacity;
    int elements;
    CustomEntry<K,V>[] buckets;

    public CustomHashMap(int capacity){
        this.capacity = capacity;
        buckets = new CustomEntry[capacity];
        elements = 0;
    }

    private int hash(K key){
        return Math.abs(key.hashCode())%capacity;
    }

    public void put(K key, V value){
        int hash = hash(key);
        CustomEntry<K,V> customEntry = new CustomEntry<K,V>(key, value, null);

        if(buckets[hash] == null){
            buckets[hash] = customEntry;
        }else{
            CustomEntry<K,V> previous = null;
            CustomEntry<K,V> current = buckets[hash];
            while(current!=null){
                if(current.getKey().equals(key)){
                    if(previous == null){
                        buckets[hash] = customEntry;
                    }else{
                        previous.setNext(customEntry);
                        customEntry.setNext(current.getNext());
                    }
                }
                previous = current;
                current = current.getNext();
            }
            previous.setNext(customEntry);
        }
        this.elements++;
    }

    public CustomEntry<K,V> get(K key){
        CustomEntry<K,V> customEntry;
        for( customEntry = buckets[hash(key)]; customEntry!=null && !customEntry.getKey().equals(key); customEntry = customEntry.getNext());
        return customEntry;
    }

    public CustomEntry<K,V> remove(K key) {
        CustomEntry<K, V> current = buckets[hash(key)];
        CustomEntry<K, V> previous = null;
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

    public Set<CustomEntry<K,V>> entrySet(){
        Set<CustomEntry<K,V>> setOfEntries = null;
        if(!this.isEmpty()){
            setOfEntries = new HashSet<>(this.elements);
            CustomEntry<K,V> customEntry;
            for(int i =0; i<capacity;i++){
                customEntry = buckets[i];
                while (customEntry!=null){
                    setOfEntries.add(customEntry);
                    customEntry = customEntry.getNext();
                }
            }
        }
        return  setOfEntries;
    }

    public void displayAll(){
        System.out.println("Printing the HashMap");
        for(int i =0;i<capacity;i++){
            CustomEntry<K,V> customEntry = buckets[i];
            System.out.print("Bucket "+ i + " : ");
            while (customEntry!=null){
                System.out.print(customEntry.toString() + " -> ");
                customEntry = customEntry.getNext();
            }
            System.out.println("NULL");
        }
    }
}

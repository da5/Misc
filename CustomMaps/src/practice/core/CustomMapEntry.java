package practice.core;

/**
 * Created by arindam.das on 10/05/16.
 */
public class CustomMapEntry<K,V>{
    private K key;
    private V value;
    private CustomMapEntry<K,V> next;
    public CustomMapEntry(K key, V value, CustomMapEntry<K,V> next){
        this.key = key;
        this.value = value;
        this.next = next;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public CustomMapEntry<K, V> getNext() {
        return next;
    }

    public void setNext(CustomMapEntry<K, V> next) {
        this.next = next;
    }

    public String toString(){
        return "("+this.key+", "+ this.value+")";
    }
}

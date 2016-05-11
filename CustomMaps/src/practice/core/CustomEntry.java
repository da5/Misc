package practice.core;

/**
 * Created by arindam.das on 10/05/16.
 */
public class CustomEntry<K,V>{
    private K key;
    private V value;
    private CustomEntry<K,V> next;
    public CustomEntry(K key, V value, CustomEntry<K,V> next){
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

    public CustomEntry<K, V> getNext() {
        return next;
    }

    public void setNext(CustomEntry<K, V> next) {
        this.next = next;
    }

    public String toString(){
        return "("+this.key+", "+ this.value+")";
    }
}

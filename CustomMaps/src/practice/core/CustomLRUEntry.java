package practice.core;

/**
 * Created by arindam.das on 11/05/16.
 */
public class CustomLRUEntry<K,V>{
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

    public CustomLRUEntry<K, V> getPrevious() {
        return previous;
    }

    public void setPrevious(CustomLRUEntry<K, V> previous) {
        this.previous = previous;
    }

    public CustomLRUEntry<K, V> getNext() {
        return next;
    }

    public void setNext(CustomLRUEntry<K, V> next) {
        this.next = next;
    }

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

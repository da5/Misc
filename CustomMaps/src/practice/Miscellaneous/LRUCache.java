package practice.Miscellaneous;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class LRUCache {
    int size;
    int capacity;
    Map<Integer, CacheEntry> map;
    Deque<CacheEntry> queue;

    static class CacheEntry{
        public Integer key;
        public Integer value;
        CacheEntry(int k, int v){key = k; value = v;}
    }

    public LRUCache(int capacity) {
        this.capacity = capacity;
        size = 0;
        map = new HashMap<>();
        queue = new LinkedList<>();
    }

    public int get(int key) {
        CacheEntry entry = map.get(key);
        if(entry == null){
            return -1;
        }
        queue.remove(entry);
        queue.offerLast(entry);
        return entry.value;
    }

    private void evict(){
        CacheEntry entry = queue.pollFirst();
        map.remove(entry.key);
        size--;
    }

    public void put(int key, int value) {
        CacheEntry entry = map.get(key);
        if(entry==null && size == capacity) {
            evict();
        }
        if(entry!=null){
            entry.value = value;
            queue.remove(entry);
            queue.offerLast(entry);
        }else{
            entry = new CacheEntry(key, value);
            map.put(key, entry);
            queue.offerLast(entry);
            size++;
        }

    }
}

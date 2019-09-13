package practice.Miscellaneous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class SmallestRangeFromLists {
    static class Element {
        int value;
        int index;

        Element(int v, int i) {
            value = v;
            index = i;
        }
    }

    public int[] smallestRange(List<List<Integer>> nums) {
        int n = nums.size();
        int[] indices = new int[n];
        Queue<Element> queue = new PriorityQueue<>(n,
                (Element e1, Element e2) -> Integer.compare(e1.value, e2.value)
                );
        int[] result = new int[2];
        int toBeEnqueued = 0;
        for(List<Integer> list: nums) {
            toBeEnqueued += list.size();
        }
        int x = nums.get(0).get(indices[0]++);
        toBeEnqueued--;
        queue.offer(new Element(x, 0));
        int max = x;
        for(int i=1; i<n; i++) {
            x = nums.get(i).get(indices[i]++);
            if(max<x) {
                max=x;
            }
            toBeEnqueued--;
            queue.offer(new Element(x, i));
        }
        result[0] = queue.peek().value;
        result[1] = max;
        while (toBeEnqueued>0) {
            Element element = queue.poll();
            int index = element.index;
            if(indices[index]==nums.get(index).size()) {
                break;
            }
            x = nums.get(index).get(indices[index]++);
            queue.offer(new Element(x, index));
            if(x>max) {
                max = x;
            }
            int min = queue.peek().value;
            if(max-min < result[1]-result[0]) {
                result[0] = min;
                result[1] = max;
            }
            toBeEnqueued--;
        }
        return result;
    }
}

class SmallestRangeFromListsDriver {
    public static void main(String[] args){
        SmallestRangeFromLists smallestRangeFromLists = new SmallestRangeFromLists();
        List<List<Integer>> nums = new ArrayList<>();
        nums.add(Arrays.asList(4,10,15,24,26));
        nums.add(Arrays.asList(0,9,12,20));
        nums.add(Arrays.asList(5,18,22,30));

        int[] result = smallestRangeFromLists.smallestRange(nums);
        System.out.println(result[0] + ", " + result[1]);
    }
}

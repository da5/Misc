package practice.Miscellaneous;

import java.util.Deque;
import java.util.LinkedList;

public class SlidingWindowMax {

    private static int[] printSlidingWindowMax(int[] nums, int k){
        if(nums.length<1 || nums.length<k){
            return new int[0];
        }
        Deque<Integer> queue = new LinkedList<>();
        int[] result = new int[nums.length-k+1];
        int i = 0;
        int j = 0;
        for(; i<k; i++){
            while(!queue.isEmpty() && nums[i]>=nums[queue.peekLast()]){
                queue.removeLast();
            }
            queue.addLast(i);
        }

        for(;i<nums.length;i++){

            result[j++] = nums[queue.peekFirst()];

            while (!queue.isEmpty() && queue.peekFirst()<=i-k){
                queue.removeFirst();
            }

            while(!queue.isEmpty() && nums[i]>=nums[queue.peekLast()]){
                queue.removeLast();
            }
            queue.addLast(i);
        }
        result[j++] = nums[queue.peekFirst()];
        return result;
    }

    public static void main(String[] args){
        int[] arr = {12, 1, 78, 90, 57, 89, 56};
        System.out.println(printSlidingWindowMax(arr, 3));
        int[] arr1 = {};
        System.out.println(printSlidingWindowMax(arr1, 3));
    }
}

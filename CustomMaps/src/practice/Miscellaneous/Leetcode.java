package practice.Miscellaneous;

import java.util.*;

public class Leetcode {

    private String getAnagramKey(String str) {
        int[] freq = new int[26];
        int n = str.length();
        for(int i=0; i<n; i++) {
            freq[str.charAt(i)-'a']++;
        }

        StringBuilder builder = new StringBuilder();
        for(int i=0; i<26; i++) {
            if(freq[i]>0) {
                builder.append('a'+i);
                builder.append(freq[i]);
            }
        }
        return builder.toString();
    }

    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for(String str: strs) {
            String key = getAnagramKey(str);
            if(!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            map.get(key).add(str);
        }

        List<List<String>> result = new ArrayList<>();
        result.addAll(map.values());
        return result;
    }

    public void moveZeroes(int[] nums) {
        int n = nums.length;
        int nonZerosSoFar = 0;
        for(int i=0; i<n; i++) {
            if(nums[i]!=0) {
                nums[nonZerosSoFar++] = nums[i];
            }
        }

        for(int i = nonZerosSoFar; i<n; i++) {
            nums[i] = 0;
        }
    }

    private int getSum(int n) {
        int sum = 0;
        while(n>0) {
            int r = n%10;
            n = n/10;
            sum += r*r;
        }
        return sum;
    }

    public boolean isHappy(int n) {
        Set<Integer> seen = new HashSet<>();
        while(n!=1 && !seen.contains(n)) {
            seen.add(n);
            n = getSum(n);

        }
        return n==1;
    }

    public boolean _isHappy(int n) {
        int slow = n;
        int fast = getSum(n);

        while(slow!=1 && slow!=fast) {
            slow = getSum(slow);
            fast = getSum(getSum(fast));
        }
        return slow==1;
    }

    public static class Obj {
        int x;
    }
    public int func(int n, Obj m) {
        if(n==0) {
            return 0;
        }
        Obj med = new Obj();
        int result = 1+func(n-1, med);
        m.x= result;
        System.out.println(n +  ", " + med.x);
        return result;
    }

    public int findMaxLength(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        int sum = 0, max = 0;
        map.put(0,-1);
        for(int i=0; i<nums.length; i++) {
            sum += (nums[i]==0)? -1 : 1;
            if(!map.containsKey(sum)) {
                map.put(sum, i);
            } else {
                max = Math.max(max, i-map.get(sum));
            }

        }
        return max;
    }
}

class LeetcodeDriver {
    public static void main(String[] args) {
        Leetcode leetcode = new Leetcode();
        int[] nums = {0,1,0,0,1};
        System.out.println(leetcode.findMaxLength(nums));
    }
}

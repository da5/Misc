package practice.Miscellaneous;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import java.util.HashMap;
import java.util.Map;

public class AndroidUnlockPattern {

    /*
        1 - 3 : 2
        3 - 1 : 2

        1 - 7 : 4
        7 - 1 : 4

        3 - 9 : 6
        9 - 3 : 6

        9 - 7 : 8
        7 - 9 : 8

        1 - 9 : 5
        3 - 7 : 5
        7 - 3 : 5
        9 - 1 : 5
        2 - 8 : 5
        8 - 2 : 5
        4 - 6 : 5
        6 - 4 : 5
     */

    int[][] skip;
    boolean[] visited;
    Map<Integer, Map<Integer, Integer>> memoized;

    int _numberOfPatternsMemoized(int current, int left) {
        if(left<0) {
            return 0;
        }
        if(left == 0) {
            return 1;
        }
        if(memoized.containsKey(current) && memoized.get(current).containsKey(left)) {
            return memoized.get(current).get(left);
        }
        int count = 0;
        visited[current] = true;
        for(int i=1; i<=9; i++) {
            if(!visited[i] && (skip[current][i]==0 || visited[skip[current][i]])) {
                count += _numberOfPatternsMemoized(i, left-1);
            }
        }
        visited[current] = false;
        memoized.putIfAbsent(current, new HashMap<>());
        memoized.get(current).put(left, count);
        return count;
    }

    int _numberOfPatterns(int current, int left) {
        if(left<0) {
            return 0;
        }
        if(left == 0) {
            return 1;
        }
        int count = 0;
        visited[current] = true;
        for(int i=1; i<=9; i++) {
            if(!visited[i] && (skip[current][i]==0 || visited[skip[current][i]])) {
                count += _numberOfPatterns(i, left-1);
            }
        }
        visited[current] = false;
        return count;
    }

    public int numberOfPatterns(int m, int n) {
        skip = new int[10][10];
        skip[1][3] = skip[3][1] = 2;
        skip[1][7] = skip[7][1] = 4;
        skip[9][3] = skip[3][9] = 6;
        skip[9][7] = skip[7][9] = 8;

        skip[1][9] = skip[3][7] = skip[7][3]
                = skip[9][1] = skip[2][8] = skip[8][2]
                = skip[4][6] = skip[6][4] = 5;

        visited = new boolean[10];
        visited[0] = true;

        memoized = new HashMap<>();

        int total = 0;
        for(int left=m; left<=n; left++) {
            total += _numberOfPatternsMemoized(1, left-1) * 4;
            total += _numberOfPatternsMemoized(2, left-1) * 4;
            total += _numberOfPatternsMemoized(5, left-1);


        }
        return total;
    }
}

class AndroidUnlockPatternDriver {
    public static void main(String[] args) {
        AndroidUnlockPattern pattern = new AndroidUnlockPattern();
        System.out.println(pattern.numberOfPatterns(1,2));
        System.out.println(pattern.numberOfPatterns(1,3));

    }
}
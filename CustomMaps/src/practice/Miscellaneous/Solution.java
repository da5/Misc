package practice.Miscellaneous;

import practice.core.CustomMaze;

import java.util.HashSet;
import java.util.Set;

public class Solution {

    public static int solution1(int[] A) {
        int n = 0;
        Set<Integer> onFromLeft = new HashSet<>();
        Set<Integer> onOthers = new HashSet<>();
        for(int i = 0 ; i<A.length; i++){
            if(A[i] <= i+1){
                onFromLeft.add(A[i]);
            }else {
                onOthers.add(A[i]);
            }
            if(onOthers.contains(i+1)){
                onOthers.remove(i+1);
                onFromLeft.add(i+1);
            }
            if(onFromLeft.size() == i+1){
                n++;
            }
        }
        return n;
    }

    static void resetSets(Set<String> left, Set<String> middle, Set<String> right){
        left.clear();
        middle.clear();
        right.clear();

        left.add("A");
        left.add("B");
        left.add("C");

        middle.add("D");
        middle.add("E");
        middle.add("F");
        middle.add("G");

        right.add("H");
        right.add("J");
        right.add("K");
    }

    public static int solution2(int N, String S) {

            Set<String> left = new HashSet<>();
            Set<String> middle = new HashSet<>();
            Set<String> right = new HashSet<>();

            String current = S;
            String next = "";
            int result = 0;
            for(int i = 1; i<=N; i++){
                resetSets(left, middle, right);
                current.trim();
                if(current.length() > 0) {
                    String[] seats = current.split(" ");
                    for (int j = 0; j < seats.length; j++) {
                        int row = Character.getNumericValue(seats[j].charAt(0));
                        if (row == i) {
                            left.remove("" + seats[j].charAt(1));
                            middle.remove("" + seats[j].charAt(1));
                            right.remove("" + seats[j].charAt(1));
                        } else {
                            next = next + seats[j] + " ";
                        }
                    }
                }
                if(left.size() >=3){
                    result++;
                }
                if(middle.size() >=3){
                    if(middle.contains("E") && middle.contains("F")){
                        result++;
                    }

                }
                if(right.size() >=3){
                    result++;
                }
                current = next;
                next = "";
            }
            return result;

    }

        static int findMax(int[] result){
        int max = result[0];
        for(int i = 1; i<result.length;i++){
            if(max < result[i]){
                max = result[i];
            }
        }
        return max;
    }

    static int checkDifferent(int a, int b, int d){
        if (d == 0) {
            if ((a>0 && b<=0)){
                return -1;
            }else if (a<=0 && b>0){
                return 1;
            }
        }else if(d == -1){
            if ((a<=0 && b>0) || (a<0 && b>=0)){
                return 1;
            }
        }else{
            if ((a>0 && b<=0) || (a>=0 && b<0)){
                return -1;
            }
        }
        return 0;
    }

    public static int solution3(int[] A) {
        int[] resultL = new int[A.length];
        int[] resultR = new int[A.length];
        int n = A.length;
        resultL[0] = 1;
        int dir = 0;
        for(int i = 1; i < n; i++){
            dir = checkDifferent(A[i],A[i-1], dir);
            if(dir != 0){
                resultL[i] = resultL[i-1] + 1;
            }else{
                resultL[i] = 1;
            }
        }

        resultR[n-1] = 1;
        dir = 0;
        for(int i = n-2; i >= 0; i--){
            dir = checkDifferent(A[i],A[i+1], dir);
            if(dir != 0){
                resultR[i] = resultR[i+1] + 1;
            }else{
                resultR[i] = 1;
            }
        }

         return findMax(resultL);
    }

    public static void main(String[] args){
//        int[] array = {2,1,3,4,6,5};
//        System.out.println("Problem 1 :: " + solution1(array));
//        int[] array1 = {6,5,4,3,2,1};
//        System.out.println("Problem 1 :: " + solution1(array1));
//
//        System.out.println("Problem 2 :: " + solution2(2, "1A 2F 1C"));
//        System.out.println("Problem 2 :: " + solution2(1, ""));
//
//
//        int[] arr = {5, 4, -3, 2, 0, 1, -1, 0, 2, -3, 4, -5};
//        System.out.println("Problem 3 :: " + solution3(arr));
//        int[] arr1 = {5, 4, 2};
//        System.out.println("Problem 3 :: " + solution3(arr1));


//        int[][] food = {{2,0},{0,0},{0,2},{2,2}};
//        SnakeGame game = new SnakeGame(3,3, food);
//        String moves = "DDRUULDRRULD";
//        for(char c: moves.toCharArray()){
//            System.out.println(game.move(""+c));
//        }

        System.out.println((int)('f'-'a'));

//        ["SnakeGame","move","move","move","move","move","move","move","move","move","move","move","move"]
//[[3,3,[[2,0],[0,0],[0,2],[2,2]]],["D"],["D"],["R"],["U"],["U"],["L"],["D"],["R"],["R"],["U"],["L"],["D"]]
    }
}

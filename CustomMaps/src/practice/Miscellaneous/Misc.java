package practice.Miscellaneous;

import java.util.*;

/**
 * Created by dasarindam on 11/11/2016.
 */
class Point3D {
    public int x;
    public int y;
    public int z;

    public Point3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString(){
        return String.format("[%d, %d, %d]", x,y,z);
    }
}

public class Misc {

    public static void main(String args[]){
//        int[] A = {-1, 3, -4, 5, 1, -6, 2, 1};
//        System.out.println(solution(A));
//        int[] A = {40,40,100,80,20};
//        int[] B = {3,3,2,2,3};
//        System.out.println(solution(A, B, 3,5,200));

//        int[] A = {60,80,40};
//        int[] B = {2,3,5};
//        System.out.println(solution(A, B, 5,1,200));

//        solution(120);
//        Point3D[] A = {
//                new Point3D(0,5,4),
//                new Point3D(0,0,-3),
//                new Point3D(-2,1,-6),
//                new Point3D(1,-2,2),
//                new Point3D(1,1,1),
//                new Point3D(4,-4,3),
//        };
//        System.out.println(solution(A));
//        List<Point3D> point3DS = Arrays.asList(A);
//        Collections.sort(point3DS, new Comparator<Point3D>() {
//            @Override
//            public int compare(Point3D o1, Point3D o2) {
//                return (o1.z - o2.z);
//            }
//        });
//        System.out.println(point3DS);
        generateSubsets(3, Arrays.asList(new Integer[]{2,3,6,7,4,4}));
    }

    public static void parenthesis(int n){
        List<String> results = new ArrayList<>();
        parenthesis(results, "", n, n);
        System.out.println(results.size());
        System.out.println(results);
    }

    public static void parenthesis(List<String> results, String str, int left, int right){
        if(right == 0){
            results.add(str);
            return;
        }
        if(right<left){
            return;
        }
        if(left > 0){
            parenthesis(results, str+"(", left-1, right);
        }
        if(left<right){
            parenthesis(results, str+")", left, right-1);
        }
    }

    public static void generateSubsets(int k, List<Integer> list){
        List<List<Integer>> subsets = new ArrayList<>();
        generateSubsets(subsets, 0, k, new ArrayList<>(), list);
        System.out.println(subsets.size());
        for(List<Integer> subset: subsets){
            System.out.println(subset);
        }
    }

    public static void generateSubsets(List<List<Integer>> subsets, int i, int k, List<Integer> subset, List<Integer> list){
        if(k==subset.size()){
            subsets.add(subset);
            return;
        }
        if(list.size() == i){
            return;
        }
        generateSubsets(subsets, i+1, k, subset, list);
        List<Integer> subset1 = new ArrayList<>(subset);
        subset1.add(list.get(i));
        generateSubsets(subsets, i+1, k, subset1, list);

    }

    public static int solution(Point3D[] A) {
        // write your code in Java SE 8
        Set<Double> spheres = new HashSet<>();
        for(int i =0 ; i< A.length; i++){
            spheres.add(
                    Math.sqrt(
                            A[i].x*A[i].x + A[i].y*A[i].y + A[i].z*A[i].z
                    )
            );
        }
        return spheres.size();
    }

    public static void solution(int N) {
        for(int i =1; i<=N; i++){
            boolean flag = false;
            if(i%3==0){
                flag = true;
                System.out.print("Fizz");
            }
            if(i%5==0){
                flag = true;
                System.out.print("Buzz");
            }
            if(i%7==0){
                flag = true;
                System.out.print("Woof");
            }
            if(!flag){
                System.out.println(i);
            }else{
                System.out.println("");
            }
        }
    }

    public static int solution(int[] A, int[] B, int M, int X, int Y) {
        // write your code in Java SE 8
        int i = 0, moves = 0;
        int total = A.length;
        long count = 0 , load = 0;
        Set<Integer> floors = new HashSet<>();
        for(;i<total; i++){
            if(count+1>X || load+A[i]>Y){
                moves += floors.size() + 1;
                count = 1; load = A[i];
                floors.clear();
                floors.add(B[i]);
            }else{
                count++; load+= A[i];
                floors.add(B[i]);
            }

        }
        if(!floors.isEmpty()){
            moves += floors.size()+1;
        }
        return moves;
    }

    public static int solution(int[] A) {
        // write your code in Java SE 8
        long[] sumLR = new long[A.length];
        long[] sumRL = new long[A.length];
        sumLR[0] = A[0];
        sumRL[A.length-1] = A[A.length-1];
        for(int i = 1; i< A.length; i++){
            sumLR[i] = sumLR[i-1]+A[i];
        }
        for(int i = A.length-2; i>=0; i--){
            sumRL[i] = sumLR[i+1] + A[i];
        }
        int i;
        for(i=0; i< A.length; i++){
            long left, right;
            if(i==0){
                left = 0;
            }else{
                left = sumLR[i-1];
            }
            if(i==A.length-1){
                right = 0;
            }else{
                right = sumRL[i+1];
            }
            if(left == right){
                break;
            }
        }
        if(i==A.length){
            return -1;
        }else{
            return i;
        }
    }
}

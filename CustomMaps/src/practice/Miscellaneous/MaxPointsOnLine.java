package practice.Miscellaneous;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MaxPointsOnLine {
    int egcd(int a, int b) {
        while(b != 0){
            int rem = a%b;
            a = b;
            b = rem;
        }
        return a;
    }


    public int maxPoints(Point[] points) {
        int n = points.length;
        if(n<=2){
            return n;
        }
        Map<Integer, Map<Integer, Integer>> map = new HashMap<>();
        int result = 0;
        for(int i=0; i<n; i++){
            int overlaps = 0;
            int max = 0;
            for(int j=i+1; j<n; j++){
                int x = points[i].x - points[j].x;
                int y = points[i].y - points[j].y;
                if(x==0 && y ==0){
                    overlaps++;
                    continue;
                }
                int gcd = egcd(x, y);
                if(gcd != 0){
                    x/= gcd;
                    y/= gcd;
                }

                if(!map.containsKey(x)){
                    map.put(x, new HashMap<>());
                }
                if(!map.get(x).containsKey(y)){
                    map.get(x).put(y, 0);
                }
                int count = map.get(x).get(y);
                map.get(x).put(y, count+1);
                max = Math.max(max, map.get(x).get(y));
            }
            result = Math.max(result, max+overlaps+1);
            map.clear();
        }
        return result;
    }
}

class MaxPointsOnLineDriver{
    public static void main(String[] args){
        int[][] pts = {{40,-23},{9,138},{429,115},{50,-17},{-3,80},{-10,33},{5,-21},{-3,80},{-6,-65},{-18,26},{-6,-65},
                {5,72},{0,77},{-9,86},{10,-2},{-8,85},{21,130},{18,-6},{-18,26},{-1,-15},{10,-2},{8,69},{-4,63},{0,3},
                {-4,40},{-7,84},{-8,7},{30,154},{16,-5},{6,90},{18,-6},{5,77},{-4,77},{7,-13},{-1,-45},{16,-5},{-9,86},
                {-16,11},{-7,84},{1,76},{3,77},{10,67},{1,-37},{-10,-81},{4,-11},{-20,13},{-10,77},{6,-17},{-27,2},
                {-10,-81},{10,-1},{-9,1},{-8,43},{2,2},{2,-21},{3,82},{8,-1},{10,-1},{-9,1},{-12,42},{16,-5},{-5,-61},
                {20,-7},{9,-35},{10,6},{12,106},{5,-21},{-5,82},{6,71},{-15,34},{-10,87},{-14,-12},{12,106},{-5,82},
                {-46,-45},{-4,63},{16,-5},{4,1},{-3,-53},{0,-17},{9,98},{-18,26},{-9,86},{2,77},{-2,-49},{1,76},{-3,-38}
                ,{-8,7},{-17,-37},{5,72},{10,-37},{-4,-57},{-3,-53},{3,74},{-3,-11},{-8,7},{1,88},{-12,42},{1,-37},
                {2,77},{-6,77},{5,72},{-4,-57},{-18,-33},{-12,42},{-9,86},{2,77},{-8,77},{-3,77},{9,-42},{16,41},
                {-29,-37},{0,-41},{-21,18},{-27,-34},{0,77},{3,74},{-7,-69},{-21,18},{27,146},{-20,13},{21,130},{-6,-65}
                ,{14,-4},{0,3},{9,-5},{6,-29},{-2,73},{-1,-15},{1,76},{-4,77},{6,-29}};
//        int[][] pts = {{0,0}, {1,1}, {0,0}};
        int n = pts.length;
        Point[] points = new Point[n];
        for(int i=0; i<n; i++){
            points[i] = new Point(pts[i][0], pts[i][1]);
        }
        MaxPointsOnLine maxPointsOnLine = new MaxPointsOnLine();
        System.out.println(maxPointsOnLine.maxPoints(points));
    }
}

class Point{
    int x;
    int y;
    Point(int a, int b) { x = a; y = b; }
}
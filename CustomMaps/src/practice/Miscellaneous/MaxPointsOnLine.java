package practice.Miscellaneous;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MaxPointsOnLine {
    static class Line{
        int mn;
        int md;
//        int cn;
//        int cd;
        Line(int a, int b /*, int c, int d*/) { mn = a; md = b; /*cn = c; cd = d;*/ }

        @Override
        public boolean equals(Object obj) {
            if(this == obj){
                return true;
            }
            if(obj == null || this.getClass()!=obj.getClass()){
                return false;
            }
            Line line = (Line)obj;
            return (this.mn==line.mn && this.md==line.md /*&& this.cn==line.cn && this.cd==line.cd*/);
        }

        @Override
        public int hashCode(){
            int hash = 7;
            hash = hash*31 + mn;
            hash = hash*31 + md;
//            hash = hash*31 + cn;
//            hash = hash*31 + cd;
            return hash;
        }
    }

    int egcd(int a, int b) {
        if (a == 0)
            return b;

        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            if (a > b)
                a = a - b;
            else
                b = b - a;
        }
        return a;
    }
/*
y = mx+c
y = (mn/md)x +c

c = y - (mn * x)/md = (md.y-mn.x)/md
 */
    Line getLine(Point p1, Point p2){
        int mn = p1.y-p2.y;
        int md = p1.x-p2.x;
        int gcd = egcd(mn, md);
        if(gcd!=0){
            mn /= gcd;
            md /= gcd;
        }
        if(mn<0 && md<0){
            mn = -mn;
            md = -md;
        }
//        int cn = md*p1.y-mn*p1.x;
//        int cd = md;
//        gcd = egcd(cn, cd);
//        if(gcd != 0){
//            cn /= gcd;
//            cd /= gcd;
//        }
//        if(cn<0 && cd<0){
//            cn = -cn;
//            cd = -cd;
//        }
        return new Line(mn, md /*, cn, cd*/);
    }

    int getMax(Point[] points,  Map<Line, Set<Point>> lineMap){
        Map<String, Integer> map = new HashMap<>();
        int n = points.length;
        for(int i=0; i<n; i++){
            int count = 0;
            String pointStr = getPointStr(points[i]);
            if(map.containsKey(pointStr)){
                count = map.get(pointStr);
            }
            map.put(pointStr, count+1);
        }
        int max = 0;
        for(Map.Entry<Line, Set<Point>> entry: lineMap.entrySet()){
//            int ctr = 0;
//            for(String point: entry.getValue()){
//                ctr += map.get(point);
//            }
//            max = Math.max(max, ctr);
            max = Math.max(max, entry.getValue().size());
        }
        return max;
    }

    String getPointStr(Point point){
        StringBuilder builder = new StringBuilder();
        builder.append(point.x);
        builder.append('~');
        builder.append(point.y);
        return builder.toString();
    }

    public int maxPoints(Point[] points) {
        int n = points.length;
        if(n<=2){
            return n;
        }
        Map<Line, Set<Point>> map = new HashMap<>();
        for(int i=0; i<n-1; i++){
            for(int j=i+1; j<n; j++){
                if( points[i].x == points[j].x && points[i].y == points[j].y ){
                    continue;
                }
                Line line = getLine(points[i], points[j]);
                if(!map.containsKey(line)){
                    map.put(line, new HashSet<>());
                }
                map.get(line).add(points[i]);
                map.get(line).add(points[j]);
            }
        }
        return getMax(points, map);
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
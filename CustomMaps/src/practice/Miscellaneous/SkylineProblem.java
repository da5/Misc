package practice.Miscellaneous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkylineProblem {

    public static class Building {
        int leftX;
        int rightX;
        int height;

        public Building(int leftX, int rightX, int height) {
            this.leftX = leftX;
            this.rightX = rightX;
            this.height = height;
        }
    }

    void append(List<List<Integer>> list, List<Integer> coordinate) {
        int size = list.size();
        if(size>0 && list.get(size-1).get(1).equals(coordinate.get(1))) {
            return;
        }
        if(size==0 || !list.get(size-1).get(0).equals(coordinate.get(0))) {
            list.add(coordinate);
        } else {
            list.get(size-1).set(1, coordinate.get(1));
        }
    }

    List<List<Integer>> merge(List<List<Integer>> subResult1, List<List<Integer>> subResult2) {
        int subResult1Size = subResult1.size();
        int subResult2Size = subResult2.size();

        List<List<Integer>> result = new ArrayList<>();
        int i1 = 0, i2 = 0;
        int h1 = 0;
        int h2 = 0;
        int x, maxH;
        while(i1<subResult1Size && i2<subResult2Size) {
            List<Integer> result1 = subResult1.get(i1);
            List<Integer> result2 = subResult2.get(i2);
            if(result1.get(0) < result2.get(0)) {
                h1 = result1.get(1);
                x = result1.get(0);
                i1++;
            } else {
                h2 = result2.get(1);
                x = result2.get(0);
                i2++;
            }
            maxH = Math.max(h1, h2);
            append(result, Arrays.asList(x, maxH));
        }

        while(i1<subResult1Size) {
            append(result, subResult1.get(i1));
            i1++;
        }
        while(i2<subResult2Size) {
            append(result, subResult2.get(i2));
            i2++;
        }
        return result;
    }

    List<List<Integer>> getSkyline(Building[] buildings, int start, int end) {
        List<List<Integer>> result = new ArrayList<>();
        if(start==end) {
            append(result, Arrays.asList(buildings[start].leftX, buildings[start].height));
            append(result, Arrays.asList(buildings[start].rightX, 0));
        } else {
            int mid = (start+end)/2;
            List<List<Integer>> subResult1 = getSkyline(buildings, start, mid);
            List<List<Integer>> subResult2 = getSkyline(buildings, mid+1, end);
            result = merge(subResult1, subResult2);
        }
        return result;
    }

    public List<List<Integer>> getSkyline(int[][] buildings) {
        int n = buildings.length;
        if(n==0) {
            return new ArrayList<>();
        }
        Building[] buildingArr = new Building[n];
        Map<Integer, Map<Integer, Integer>> map = new HashMap<>();
        for(int i=0; i<n; i++) {
            map.putIfAbsent(buildings[i][0], new HashMap<>());
            map.get(buildings[i][0]).putIfAbsent(buildings[i][1], 0);
            int val = map.get(buildings[i][0]).get(buildings[i][1]);
            map.get(buildings[i][0]).put(buildings[i][1], Math.max(val, buildings[i][2]));
        }
        int i=0;
        for(Map.Entry<Integer, Map<Integer, Integer>> entry: map.entrySet()) {
            for(Map.Entry<Integer, Integer> entry1: entry.getValue().entrySet()) {
                buildingArr[i++] = new Building(entry.getKey(), entry1.getKey(), entry1.getValue());
            }
        }
        return getSkyline(buildingArr, 0, i-1);
    }
}

class SkylineProblemDriver {
    public static void main(String[] args){
        SkylineProblem skylineProblem = new SkylineProblem();

//        int[][] arr = {{2, 9, 10},{3, 7, 15},{5, 12, 12},{15, 20, 10},{19, 24, 8}};
//        int[][] arr = {{1, 5, 11}, {2, 7, 6}, {3, 9, 13},
//                {12, 16, 7}, {14, 25, 3}, {19, 22, 18},
//                {23, 29, 13}, {24, 28, 4}};
//        int[][] arr = {{1,2,1},{1,2,2},{1,2,3}};
        int[][] arr = {{6765,184288,53874},{13769,607194,451649},{43325,568099,982005},{47356,933141,123943},
                {59810,561434,119381},{75382,594625,738524},{111895,617442,587304},{143767,869128,471633},
                {195676,285251,107127},{218793,772827,229219},{316837,802148,899966},{329669,790525,416754},
                {364886,882642,535852},{368825,651379,6209},{382318,992082,300642},{397203,478094,436894},
                {436174,442141,612149},{502967,704582,918199},{503084,561197,625737},{533311,958802,705998},
                {565945,674881,149834},{615397,704261,746064},{624917,909316,831007},{788731,924868,633726},{791965,912123,438310}};

//        int[][] arr = {{6765,184288,53874}, {47356,933141,123943}, {50000,100000,123}, {150000,250000,1239}};

        System.out.println(skylineProblem.getSkyline(arr));
    }
}

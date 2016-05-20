package practice.drivers;

import practice.core.CustomLRUEntry;
import practice.core.CustomMaze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by arindam.das on 11/05/16.
 */
public class DummyDriver {

    private static void print(List<CustomLRUEntry<String, Double>> list){
        System.out.println("Printing list :");
        for(CustomLRUEntry<String,Double> entry: list){
            System.out.print(entry + " ");
        }
        System.out.println("");
    }

    private static void comparatorDemo(){
        List<CustomLRUEntry<String, Double>> list = new ArrayList<>();
        list.add(new CustomLRUEntry<>("Charlie", 2300.22, null, null));
        list.add(new CustomLRUEntry<>("Andy", 60.72, null, null));
        list.add(new CustomLRUEntry<>("Bob", 60.52, null, null));
        list.add(new CustomLRUEntry<>("Edd", 12300.72, null, null));
        list.add(new CustomLRUEntry<>("Dale", 10.62, null, null));
        print(list);
        list.sort(new Comparator<CustomLRUEntry<String, Double>>() {
            @Override
            public int compare(CustomLRUEntry<String, Double> o1, CustomLRUEntry<String, Double> o2) {
                return (o1.getValue() == o2.getValue()) ? 0 : ((o1.getValue() > o2.getValue()) ? 1 : -1);
            }
        });
        print(list);
        Collections.sort(list);
        print(list);
    }

    public static void main(String[] args){
        CustomMaze customMaze = new CustomMaze(11);
        customMaze.fillMaze();
        customMaze.printMaze();
    }
}

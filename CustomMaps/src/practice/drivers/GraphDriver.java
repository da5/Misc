package practice.drivers;

import com.sun.tools.javac.util.Pair;
import practice.generic.util.CustomGraph;

import java.util.List;

/**
 * Created by arindam.das on 17/05/16.
 */
public class GraphDriver {
    public static void main(String[] args){
        CustomGraph customGraph = new CustomGraph(7);
        customGraph.addEdge(0,1);
        customGraph.addEdge(2,1);
        customGraph.addEdge(0,2);
        customGraph.addEdge(3,2);
        customGraph.addEdge(3,4);
        customGraph.addEdge(4,5);
        customGraph.addEdge(5,3);
        customGraph.addEdge(4,6);
        boolean[] articulationPoints = customGraph.articulationPoints();
        List<Pair> bridges = customGraph.bridges();
        System.out.print("Articulation points are :: ");
        for(int i =0 ;i < articulationPoints.length; i++){
            if(articulationPoints[i]){
                System.out.print(i + " ");
            }
        }
        System.out.println("");
        System.out.print("Bridges are :: ");
        for(Pair pair: bridges){
            System.out.print(pair.fst + "<->" + pair.snd + " ");
        }
        System.out.println("");

        articulationPoints = customGraph.getArticulationPoints();
        System.out.print("Articulation points are :: ");
        for(int i =0 ;i < articulationPoints.length; i++){
            if(articulationPoints[i]){
                System.out.print(i + " ");
            }
        }
        System.out.println("");
        bridges = customGraph.getBridges();
        System.out.print("Bridges are :: ");
        for(Pair pair: bridges){
            System.out.print(pair.fst + "<->" + pair.snd + " ");
        }
        System.out.println("");
    }
}

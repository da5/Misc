package practice.Miscellaneous;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by arindam.das on 22/06/16.
 */

class Node{
    int id;
    List<Node> neighbors;

    int distanceFromCapital;

    public Node(int i){
        id = i;
        neighbors = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Node> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<Node> neighbors) {
        this.neighbors = neighbors;
    }

    public int getDistanceFromCapital() {
        return distanceFromCapital;
    }

    public void setDistanceFromCapital(int distanceFromCapital) {
        this.distanceFromCapital = distanceFromCapital;
    }

}

public class Main {


    static int n, k, capitalId, queries;
    static Node[] nodes;
    static int[] cityToProducts;
    static int[][] query;
    static int[] distanceFromCapital;
    static int[] component;
    static Map<String, Node> farthestNodeForProductAndComponent;
    static Node[] lowestIndexCityForProduct;

    public static void getInput(){
//        Scanner scanner = new Scanner(System.in);
        try {


            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String inputLine = bufferedReader.readLine();
            n = Integer.parseInt(inputLine.split(" ")[0]);
            k = Integer.parseInt(inputLine.split(" ")[1]);
            inputLine = bufferedReader.readLine();
            capitalId = Integer.parseInt(inputLine);
            nodes = new Node[n + 1];
            cityToProducts = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                nodes[i] = new Node(i);
            }
            for (int i = 0; i < n - 1; i++) {
                inputLine = bufferedReader.readLine();
                int x = Integer.parseInt(inputLine.split(" ")[0]);
                int y = Integer.parseInt(inputLine.split(" ")[1]);
                nodes[x].getNeighbors().add(nodes[y]);
                nodes[y].getNeighbors().add(nodes[x]);
            }
            for (int i = 1; i <= n; i++) {
                inputLine = bufferedReader.readLine();
                cityToProducts[i] = Integer.parseInt(inputLine);
            }
            inputLine = bufferedReader.readLine();
            queries = Integer.parseInt(inputLine);
            query = new int[queries][2];
            for (int i = 0; i < n; i++) {
                inputLine = bufferedReader.readLine();
                int x = Integer.parseInt(inputLine.split(" ")[0]);
                int y = Integer.parseInt(inputLine.split(" ")[1]);
                query[i][0] = x;
                query[i][1] = y;
            }
        }catch (IOException e){

        }
    }

    public static void computerDistanceFromCapital(){
        boolean[] visited = new boolean[n+1];
        Arrays.fill(visited, false);

        distanceFromCapital = new int[n+1];
        component = new int[n+1];
        distanceFromCapital[capitalId] = 0;
        component[capitalId] = 0;
//        visited[capitalId] = true;
        int componentIdx = 1;
        int distance = 1;
        Queue<Node> toVisit = new LinkedList<>();
        toVisit.offer(nodes[capitalId]);
        toVisit.offer(new Node(-1));
        while(toVisit.size()>1){
            Node node = toVisit.poll();
            if(node.getId() == -1){
                distance++;
                if(!toVisit.isEmpty()){
                    toVisit.offer(node);
                }
            }else {
                visited[node.getId()] = true;
                for(Node neighbor: node.getNeighbors()){
                    if(!visited[neighbor.getId()]){
                        distanceFromCapital[neighbor.getId()] = distance;
                        component[neighbor.getId()] = (node.getId()==capitalId)? componentIdx++ : component[node.getId()];
                        toVisit.offer(neighbor);
                    }
                }
            }
        }
        farthestNodeForProductAndComponent = new HashMap<>();
        lowestIndexCityForProduct = new Node[k+1];
        for(int i =1; i<=n; i++){
            String key = cityToProducts[i]+"~"+component[i];
            if(farthestNodeForProductAndComponent.containsKey(key)){
                if(distanceFromCapital[farthestNodeForProductAndComponent.get(key).getId()] < distanceFromCapital[nodes[i].getId()]){
                    farthestNodeForProductAndComponent.put(key, nodes[i]);
                }
            }else {
                farthestNodeForProductAndComponent.put(key, nodes[i]);
            }
            if(lowestIndexCityForProduct[cityToProducts[i]] == null){
                lowestIndexCityForProduct[cityToProducts[i]] = nodes[i];
            }else if(lowestIndexCityForProduct[cityToProducts[i]].getId() > i){
                lowestIndexCityForProduct[cityToProducts[i]] = nodes[i];
            }
        }
    }

    public static void printComputed(){
        for(int i =1; i<=n; i++){
            System.out.println(i + "-> (" + distanceFromCapital[i] + ", " + component[i] + ")");
        }
        for(Map.Entry<String, Node> entry: farthestNodeForProductAndComponent.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue().getId());
        }
        for(int i =1; i<=k; i++){
            System.out.println(i + "->" + lowestIndexCityForProduct[i].getId());
        }
    }

    public static void answerQueries(){
        for(int i =0; i<queries; i++){
            int cityIdx = query[i][0];
            int productIdx = query[i][1];
            if(cityToProducts[cityIdx] == productIdx){
                if(cityIdx!=capitalId){
                    System.out.println(cityIdx);
                }else {
                    System.out.println(lowestIndexCityForProduct[productIdx].getId());
                }

            }else {
                int componentIdx = component[cityIdx];
                String key = productIdx+"~"+componentIdx;
                if(farthestNodeForProductAndComponent.containsKey(key)){
                    System.out.println(farthestNodeForProductAndComponent.get(key).getId());
                }else{
                    System.out.println(lowestIndexCityForProduct[productIdx].getId());
                }
            }
        }
    }

    public static void main(String[] args) throws java.lang.Exception{
        getInput();
        computerDistanceFromCapital();
//        printComputed();
        answerQueries();
    }
}

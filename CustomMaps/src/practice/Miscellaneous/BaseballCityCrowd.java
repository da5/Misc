package practice.Miscellaneous;

import java.util.*;

/**
 * Created by arindam.das on 17/08/16.
 * You are given a graph with no cycles, each node representing different cities and there are stadiums for baseball games in all cities.

 Each node contains a value representing the population of the city, and a list of neighbors. (feel free to extend data structure)

 Every time there is a baseball game at a city, everyone from all the different cities will go to the city with the baseball game.

 Return the maximum traffic between a city and its neighbours when there is a game at that city, for all cities. (Does not have to be sorted)

 The total run-time after returning everything should be O(n).

 Examples:


 Input:
 1   2
 \ /
  5
 /  \
 4   3
 Output:
 1 14
 2 13
 3 12
 4 11
 5 4

 Input:
      7
     /
 1  2
  \/ \
  5  15
 / \
 4  3
 Output:
 1 36
 2 15
 3 34
 4 33
 5 24
 7 30
 15 22
 */
class BaseballCity {
    public class City{
        int id;
        int totalPopulationAtSubtree;
        int population;
        List<City> neighbours;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTotalPopulationAtSubtree() {
            return totalPopulationAtSubtree;
        }

        public void setTotalPopulationAtSubtree(int totalPopulationAtSubtree) {
            this.totalPopulationAtSubtree = totalPopulationAtSubtree;
        }

        public int getPopulation() {
            return population;
        }

        public void setPopulation(int population) {
            this.population = population;
        }

        public List<City> getNeighbours() {
            return neighbours;
        }

        public void setNeighbours(List<City> neighbours) {
            this.neighbours = neighbours;
        }

        public City(int id, int population){
            this.id = id;
            this.population = population;
            neighbours = new ArrayList<>();
            this.totalPopulationAtSubtree = population;
        }

        public void addNeighbour(City city){
            this.neighbours.add(city);
        }
    }

    Map<Integer, Boolean> cityVisitedMap;
    Map<Integer, City> cityIdMap;
    Map<Integer, List<Integer>> dfsTreeParentChildMap;
    int totalPopulation;

    public BaseballCity(){
        cityVisitedMap = new HashMap<>();
        cityIdMap = new HashMap<>();
        dfsTreeParentChildMap = new HashMap<>();
        totalPopulation = 0;
    }

    public City createCity(int id, int population){
        City city = new City(id, population);
        cityVisitedMap.put(city.getId(), false);
        cityIdMap.put(id, city);
        return city;
    }

    public void createEdge(int id1, int id2){
        City city1 = cityIdMap.get(id1);
        City city2 = cityIdMap.get(id2);
        city1.addNeighbour(city2);
        city2.addNeighbour(city1);
    }

    public int dfs(City city){
        totalPopulation += city.getPopulation();
        cityVisitedMap.put(city.getId(), true);
        int totalPopolationAtSubtree = city.getTotalPopulationAtSubtree();
        for(City neighbour: city.getNeighbours()){
            if(!cityVisitedMap.get(neighbour.getId())){
                if(dfsTreeParentChildMap.get(city.getId())==null){
                    List<Integer> list = new ArrayList<>();
                    list.add(neighbour.getId());
                    dfsTreeParentChildMap.put(city.getId(), list);
                }else{
                    dfsTreeParentChildMap.get(city.getId()).add(neighbour.getId());
                }
                totalPopolationAtSubtree += dfs(neighbour);
            }
        }
        city.setTotalPopulationAtSubtree(totalPopolationAtSubtree);
        return totalPopolationAtSubtree;
    }

    public void printFlow(){
        for(Map.Entry<Integer, City> entry: cityIdMap.entrySet()){
            if(!cityVisitedMap.get(entry.getKey())){
                dfs(cityIdMap.get(entry.getKey()));
            }
        }

        for(Map.Entry<Integer, City> entry: cityIdMap.entrySet()){
            System.out.print("City (" + entry.getKey() + ", " + entry.getValue().getPopulation()+ ") :: ");
            int populationFromParent = totalPopulation - entry.getValue().getPopulation();
            int maxFlow = 0;
            if(dfsTreeParentChildMap.get(entry.getKey())!=null){
                for(Integer neighbourId : dfsTreeParentChildMap.get(entry.getKey())){
                    City neighbour = cityIdMap.get(neighbourId);
//                    System.out.print(cityIdMap.get(neighbourId).getTotalPopulationAtSubtree() + " ");
                    if(maxFlow < neighbour.getTotalPopulationAtSubtree()){
                        maxFlow = neighbour.getTotalPopulationAtSubtree();
                    }
                    populationFromParent -= neighbour.getTotalPopulationAtSubtree();
                }
            }
            if(maxFlow < populationFromParent){
                maxFlow = populationFromParent;
            }
            System.out.println(maxFlow);
        }
    }
}

public class BaseballCityCrowd{
    public static void main(String[] args){
        BaseballCity baseballCity = new BaseballCity();
        baseballCity.createCity(1,1);
        baseballCity.createCity(2,2);
        baseballCity.createCity(3,3);
        baseballCity.createCity(4,4);
        baseballCity.createCity(5,5);
        baseballCity.createEdge(1, 5);
        baseballCity.createEdge(2, 5);
        baseballCity.createEdge(3,5);
        baseballCity.createEdge(4,5);

        baseballCity.createCity(7, 7);
        baseballCity.createCity(15, 15);
        baseballCity.createEdge(2,7);
        baseballCity.createEdge(2,15);



        baseballCity.printFlow();
    }
}

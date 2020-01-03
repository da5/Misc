package practice.Miscellaneous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class Amazon {

    static class Feature {
        String name;
        int count;

        public Feature(String name, int count) {
            this.name = name;
            this.count = count;
        }
    }


    private Map<String, Integer> getFrequencyMap(List<String> possibleFeatures, List<String> featureRequests) {
        Set<String> features = possibleFeatures.stream().map(String::toLowerCase).collect(Collectors.toSet());

        Map<String, Integer> frequencyMap = new HashMap<>();
        for(String request: featureRequests) {
            StringTokenizer tokenizer = new StringTokenizer(request, ",.!?\"':;`~$#@%^&*()-_+=| ", false);
            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken().toLowerCase();
                if(features.contains(token)) {
                    frequencyMap.putIfAbsent(token, 0);
                    int count = frequencyMap.get(token);
                    frequencyMap.put(token, count+1);
                }
            }
        }

        return frequencyMap;
    }

    private Queue<Feature> getTopFeatures(Map<String, Integer> frequencyMap, int topFeatures) {
        Queue<Feature> queue = new PriorityQueue<>((x,y) -> y.count-x.count);

        for(Map.Entry<String, Integer> entry: frequencyMap.entrySet()) {
            boolean shouldOffer = true;
            if(queue.size()==topFeatures) {
                if(queue.peek().count<entry.getValue()) {
                    queue.poll();
                } else {
                    shouldOffer = false;
                }
            }
            if(shouldOffer) {
                queue.offer(new Feature(entry.getKey(), entry.getValue()));
            }
        }
        return queue;
    }

    public ArrayList<String> popularNFeatures(int numFeatures,
                                              int topFeatures,
                                              List<String> possibleFeatures,
                                              int numFeatureRequests,
                                              List<String> featureRequests)
    {

        Map<String, Integer> frequencyMap = getFrequencyMap(possibleFeatures, featureRequests);

        Queue<Feature> features = getTopFeatures(frequencyMap, topFeatures);

        List<Feature> featureList = features.stream()
                .sorted((x,y) -> (y.count==x.count)? x.name.compareTo(y.name) : y.count-x.count)
                .collect(Collectors.toList());

        ArrayList<String> result = new ArrayList<>();
        for(Feature feature: featureList) {
            result.add(feature.name);
        }
        return result;

    }


    private int[][] buildMatrix(int rows, int columns, List<List<Integer> > grid) {
        int[][] matrix = new int[rows][columns];
        int r = 0, c =0;
        for(List<Integer> row: grid) {
            for(int cell: row) {
                matrix[r][c] = cell;
                c++;
            }
            c = 0;
            r++;
        }
        return matrix;
    }


    private void sendToNeighbour(int[][] matrix, int r, int c, int rows, int cols){
        if(matrix[r][c]%2==1) {
            if(r>0 && matrix[r-1][c]<=1) {
                matrix[r-1][c] = (matrix[r-1][c]==1)?3:2;
            }

            if(r<rows-1 && matrix[r+1][c]<=1) {
                matrix[r+1][c] = (matrix[r+1][c]==1)?3:2;
            }

            if(c>0 && matrix[r][c-1]<=1) {
                matrix[r][c-1] = (matrix[r][c-1]==1)?3:2;
            }

            if(c<cols-1 && matrix[r][c+1]<=1) {
                matrix[r][c+1] = (matrix[r][c+1]==1)?3:2;
            }
        }

    }

    private boolean nextIteration(int[][] matrix, int rows, int cols, int hour) {
        boolean moreIteration = false;
        System.out.println("---------");
        for(int i =0; i<rows; i++){
            for(int j=0; j<cols; j++){
                if(hour>0 && matrix[i][j]>0) {
                    matrix[i][j] = 1;
                }
                if(matrix[i][j]==0) {
                    moreIteration = true;
                }
                System.out.print(matrix[i][j]);
            }
            System.out.println("");
        }
        return moreIteration;
    }

    private void propagate(int rows, int columns, int[][] matrix) {
        for(int i=0; i<rows; i++) {
            for(int j=0; j<columns; j++) {
                sendToNeighbour(matrix, i, j, rows, columns);
            }
        }
    }


    int minimumHours(int rows, int columns, List<List<Integer> > grid)
    {
        int[][] matrix = buildMatrix(rows, columns, grid);

        int hours = 0;
        while(nextIteration(matrix, rows, columns, hours)) {
            propagate(rows, columns, matrix);
            hours++;
        }

        return hours;
    }
}

class AmazonDriver {
    public static void main(String[] args) {
        Amazon amazon = new Amazon();
        List<String> features = amazon.popularNFeatures(5,22,
                Arrays.asList("top", "Tank", "try"),
                3,
                Arrays.asList("I love Try","I love tank", "I love top")
        );
        System.out.println(features);


        List<List<Integer>> grid =
                Arrays.asList(
                        Arrays.asList(1,0,0,0,0),
                        Arrays.asList(0,0,0,0,0),
                        Arrays.asList(0,0,0,0,0),
                        Arrays.asList(0,0,0,0,0),
                        Arrays.asList(0,0,0,0,0)
                );
        System.out.println(amazon.minimumHours(5, 5, grid));
    }
}

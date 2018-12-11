package practice.Miscellaneous;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class EvaluateDivision {
    static class Operand{
        String operand;
        Double value;

        Operand(String o, double v){
            operand = o;
            value = v;
        }
    }

    void putValues(Map<String, Map<String, Double>> map, String numerator, String denominator, Double value){
        if(!map.containsKey(numerator)){
            map.put(numerator, new HashMap<>());
        }
        map.get(numerator).put(denominator, value);
        Collections.sort(map.get("").values().stream().collect(Collectors.toList()));
    }

    double calculateBFS(Map<String, Map<String, Double>> map, String numerator, String denominator){
        double result = -1.0;
        Set<String> seen = new HashSet<>();
        Queue<Operand> queue = new LinkedList<>();
        if(map.get(numerator)!=null){
            for(Map.Entry<String, Double> entry : map.get(numerator).entrySet()){
                seen.add(entry.getKey());
                queue.offer(new Operand(entry.getKey(), entry.getValue()));
            }
        }
        while(!queue.isEmpty()){
            Operand obj = queue.poll();
            if(obj.operand.equals(denominator)){
                result = obj.value;
                break;
            }
            if(map.get(obj.operand)!=null){
                for(Map.Entry<String, Double> entry : map.get(obj.operand).entrySet()){
                    if(!seen.contains(entry.getKey())) {
                        seen.add(entry.getKey());
                        queue.offer(new Operand(entry.getKey(), obj.value * entry.getValue()));
                    }
                }
            }
        }
        return result;
    }

    double  calculate(Map<String, Map<String, Double>> map, String numerator, String denominator){
        double result = -1.0;
        Map<String, Double> numeratorMap = map.get(numerator);
        Map<String, Double> denominatorMap = map.get(denominator);
        if(map.containsKey(numerator) && map.get(numerator).containsKey(denominator)){
            result = map.get(numerator).get(denominator);
        }else if(numeratorMap != null) {
            result = calculateBFS(map, numerator, denominator);
        }

        return result;
    }

    public double[] calcEquation(String[][] equations, double[] values, String[][] queries) {
        double[] result = new double[queries.length];
        Map<String, Map<String, Double>> map = new HashMap<>();
        for(int i =0; i<values.length; i++){
            String numerator = equations[i][0];
            String denominator = equations[i][1];
            putValues(map, numerator, denominator, values[i]);
            putValues(map, denominator, numerator, 1.0/values[i]);
        }
        for(int i =0; i<queries.length; i++) {
            result[i] = calculate(map, queries[i][0], queries[i][1]);
        }
        return result;
    }
}

class EvaluateDivisionDriver{
    //        String[][] equations = {{"a", "b"}, {"b", "c"}};
//        double[] values = {2.0, 3.0};
//        String[][] queries = {{"a", "c"}, {"b", "a"}, {"a", "e"}, {"a", "a"}, {"x", "x"}};
    public static void main(String[] args) {
        String[][] equations = {{"x1", "x2"}, {"x2", "x3"}, {"x3", "x4"}, {"x4", "x5"}};
        double[] values = {3.0, 4.0, 5.0, 6.0};
        String[][] queries = {{"x1", "x5"}, {"x5", "x2"}, {"x2", "x4"}, {"x2", "x2"}, {"x2", "x9"}, {"x9", "x9"}};
        EvaluateDivision evaluateDivision = new EvaluateDivision();
        double[] result = evaluateDivision.calcEquation(equations, values, queries);
    }
}

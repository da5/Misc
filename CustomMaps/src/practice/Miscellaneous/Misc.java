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

class TNode{
    public Boolean value;
    public TNode left;
    public TNode right;

    public TNode(Boolean value, TNode left, TNode right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }
}

public class Misc {

    static TNode flipLeaf(TNode root, TNode leaf){
        if(root.left == null || root.right==null){
            if(root==leaf){
                root.value = !root.value;
            }
        }else {
            TNode left = flipLeaf(root.left, leaf);
            TNode right = flipLeaf(root.right, leaf);
            root.value = left.value & right.value;
        }
        return root;
    }

    static void printLevelOrder(TNode root){
        if(root == null){
            return;
        }
        Queue<TNode> queue = new LinkedList<>();
        queue.offer(root);
        queue.offer(new TNode(null, null, null));
        while(!queue.isEmpty()){
            TNode node = queue.poll();
            if(node.value == null){
                System.out.println("");
                if(!queue.isEmpty()){
                    queue.offer(node);
                }

            }else{
                System.out.print((node.value) + " ");
                if(node.left != null){
                    queue.offer(node.left);
                }
                if(node.right != null){
                    queue.offer(node.right);
                }
            }
        }
    }

    static List<String> generatePermutationsSansDuplicate(String str){
        List<String> permutations = new ArrayList<>();
        Map<Character, Integer> characterMap = new HashMap<>();
        for(int i=0; i< str.length(); i++){
            int value = (characterMap.containsKey(str.charAt(i)))? characterMap.get(str.charAt(i)):0;
            characterMap.put(str.charAt(i), value+1);
        }
        generatePermutationsSansDuplicate(permutations, characterMap, "", str);
        return permutations;
    }

    static void generatePermutationsSansDuplicate(List<String> permutations, Map<Character, Integer> characterMap, String prefix, String str){
        if(prefix.length() == str.length()){
            permutations.add(prefix);
            return;
        }
        for(Map.Entry<Character, Integer> entry: characterMap.entrySet()){
            int value = entry.getValue();
            if(value > 0){
                characterMap.put(entry.getKey(), value-1);
                generatePermutationsSansDuplicate(permutations, characterMap, prefix+entry.getKey(), str);
                characterMap.put(entry.getKey(), value);
            }
        }
    }

    static List<String> generatePermutations(String str){
        List<String> permutations = new ArrayList<>();
        generatePermutations(permutations, "", str);
        return permutations;
    }

    static void generatePermutations(List<String> permutations, String prefix, String str){
        if(str.length() == 0){
            permutations.add(prefix);
        }else{
            for(int i =0; i< str.length(); i++){
                String newStr = str.substring(0,i)+str.substring(i+1);
                generatePermutations(permutations, prefix+str.charAt(i), newStr);
            }
        }
    }

    static void flipLeafDemo(){
        TNode leaf1 = new TNode(false, null, null);
        TNode leaf2 = new TNode(true, null, null);
        TNode leaf3 = new TNode(true, null, null);
        TNode leaf4 = new TNode(true, null, null);
        TNode node1 = new TNode(false, leaf1, leaf2);
        TNode node2 = new TNode(true, leaf3, leaf4);
        TNode root = new TNode(false,node1,node2);
        TNode result = flipLeaf(root,leaf1);
        printLevelOrder(result);
    }

    public static void main(String args[]){
        flipLeafDemo();
        System.out.println(generatePermutationsSansDuplicate("aabab"));

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

    public static void printDiamond(int n){
        printDiamond(1,n);
    }

    public static void printDiamond(int k, int n){
        if(k>n){
            return;
        }
        String printLine = "";
        for(int i = 0; i< (n-k)/2; i++){
            printLine = printLine + " ";
        }
        for(int i = 0; i<k; i++){
            printLine = printLine + "*";
        }
        System.out.println(printLine);
        printDiamond(k+2, n);
        if(k<n){
            System.out.println(printLine);
        }
    }

}

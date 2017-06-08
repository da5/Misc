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

class LNode{
    public Integer value;

    public LNode(Integer value) {
        this.value = value;
    }
}
class TNode{
    public Boolean bool;
    public Integer value;
    public TNode left;
    public TNode right;

    public TNode(Boolean value, TNode left, TNode right) {
        this.bool = value;
        this.left = left;
        this.right = right;
    }

    public TNode(Integer value, Boolean bool, TNode left, TNode right) {
        this.value = value;
        this.left = left;
        this.right = right;
        this.bool = bool;
    }

    public TNode(int value) {
        this.value = value;
    }
}

public class Misc {

    static void treeToList(Queue<LNode> list, TNode root){
        if(root == null){
            list.offer(new LNode(null));
        }else{
            list.offer(new LNode(root.value));
            treeToList(list, root.left);
            treeToList(list, root.right);
        }
    }

    static TNode listToTree( Queue<LNode> list){
        LNode lNode = list.poll();
        TNode root = null;
        if(lNode.value != null){
            root = new TNode(lNode.value, false, null, null);
            root.left = listToTree(list);
            root.right = listToTree(list);
        }
        return root;
    }

    static TNode flipLeaf(TNode root, TNode leaf){
        if(root.left == null || root.right==null){
            if(root==leaf){
                root.bool = !root.bool;
            }
        }else {
            TNode left = flipLeaf(root.left, leaf);
            TNode right = flipLeaf(root.right, leaf);
            root.bool = left.bool & right.bool;
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
            if(node.bool == null){
                System.out.println("");
                if(!queue.isEmpty()){
                    queue.offer(node);
                }

            }else{
                System.out.print(String.format("[%d,%b]",node.value, node.bool) + " ");
                if(node.left != null){
                    queue.offer(node.left);
                }
                if(node.right != null){
                    queue.offer(node.right);
                }
            }
        }
    }

    static void printLevelOrder(TNode root, boolean zigZag){
        if(root == null){
            return;
        }
        Queue<TNode> queue = new LinkedList<>();
        queue.offer(root);
        queue.offer(new TNode(null, null, null));
        boolean reversed = false;
        String printLine = "";
        while(!queue.isEmpty()){
            TNode node = queue.poll();
            if(node.bool == null){
                System.out.println(printLine);
                printLine = "";
                if(!queue.isEmpty()){
                    queue.offer(node);
                    reversed = !reversed;
                }
            }else{
                if(zigZag && reversed){
                    printLine = " " + String.format("[%d,%b]",node.value, node.bool) + printLine;
                }else {
                    printLine = printLine + String.format("[%d,%b]",node.value, node.bool) + " ";
                }
                if(node.left != null){
                    queue.offer(node.left);
                }
                if(node.right != null){
                    queue.offer(node.right);
                }
            }
        }
    }

    static void printLevelOrderZigZag(TNode root){
        if(root==null){
            return;
        }
        LinkedList<TNode> list1 = new LinkedList<>();
        LinkedList<TNode> list2 = new LinkedList<>();
        boolean reversed = false;
        list1.addLast(root);
        while (!list1.isEmpty() || !list2.isEmpty()){
            if(reversed){
                while (!list2.isEmpty()){
                    TNode tNode = list2.pollLast();
                    System.out.print(String.format("[%d,%b]",tNode.value, tNode.bool) + " ");
                    if(tNode.right != null){
                        list1.addFirst(tNode.right);
                    }
                    if(tNode.left != null){
                        list1.addFirst(tNode.left);
                    }
                }
            }else{
                while (!list1.isEmpty()){
                    TNode tNode = list1.pollFirst();
                    System.out.print(String.format("[%d,%b]",tNode.value, tNode.bool) + " ");
                    if(tNode.left != null){
                        list2.addLast(tNode.left);
                    }
                    if(tNode.right != null){
                        list2.addLast(tNode.right);
                    }
                }
            }
            reversed = !reversed;
        }
        System.out.println("");
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

    static void levelOrderTraversalDemo(){
        TNode leaf11 = new TNode(-1, false, null, null);
        TNode leaf12 = new TNode(-2, false, null, null);
        TNode leaf1 = new TNode(1, false, leaf11, leaf12);
        TNode leaf2 = new TNode(2, false, null, null);

        TNode leaf31 = new TNode(-3, false, null, null);
        TNode leaf42 = new TNode(-4, false, null, null);


        TNode leaf3 = new TNode(3, false, leaf31, null);
        TNode leaf4 = new TNode(4, false, null, leaf42);
        TNode node12 = new TNode(12, false, leaf1, leaf2);
        TNode node34 = new TNode(34, false, leaf3, leaf4);
        TNode node1234 = new TNode(1234, false, node12, node34);
        printLevelOrder(node1234);
        printLevelOrderZigZag(node1234);
        printLevelOrder(node1234, true);
    }

    static void treeToListDemo(){
        TNode leaf1 = new TNode(1, false, null, null);
        TNode leaf2 = new TNode(2, false, null, null);
        TNode leaf3 = new TNode(3, false, null, null);
        TNode leaf4 = new TNode(4, false, null, null);
        TNode node12 = new TNode(12, false, leaf1, leaf2);
        TNode node34 = new TNode(34, false, leaf3, leaf4);
        TNode node1234 = new TNode(1234, false, node12, node34);
        printLevelOrder(node1234);
        Queue<LNode> list = new LinkedList<>();
        treeToList(list, node1234);
        for(LNode lNode : list){
            System.out.print(lNode.value+" -> ");
        }
        System.out.println("end");
        TNode root = listToTree(list);
        printLevelOrder(root);
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
//        flipLeafDemo();
//        System.out.println(generatePermutationsSansDuplicate("aabab"));
        levelOrderTraversalDemo();

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

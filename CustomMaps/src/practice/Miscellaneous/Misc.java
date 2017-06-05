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

    static TNode edit(TNode root, TNode leaf){
        if(root.left == null || root.right==null){
            if(root==leaf){
                root.value = !root.value;
            }
        }else {
            TNode left = edit(root.left, leaf);
            TNode right = edit(root.right, leaf);
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

    public static void main(String args[]){
//        generateSubsets(3, Arrays.asList(new Integer[]{2,3,6,7,4,4}));
        TNode leaf1 = new TNode(false, null, null);
        TNode leaf2 = new TNode(true, null, null);
        TNode leaf3 = new TNode(true, null, null);
        TNode leaf4 = new TNode(true, null, null);
        TNode node1 = new TNode(false, leaf1, leaf2);
        TNode node2 = new TNode(true, leaf3, leaf4);
        TNode root = new TNode(false,node1,node2);
        TNode result = edit(root,leaf2);
        printLevelOrder(result);

//        printDiamond(9);
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

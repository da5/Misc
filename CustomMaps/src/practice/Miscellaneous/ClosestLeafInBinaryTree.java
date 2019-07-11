package practice.Miscellaneous;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
//https://leetcode.com/problems/closest-leaf-in-a-binary-tree/submissions/
public class ClosestLeafInBinaryTree {
    static boolean found;
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    TreeNode root;

    ClosestLeafInBinaryTree(Integer[] arr){
        root = new TreeNode(arr[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int i = 1;
        while (!queue.isEmpty()){
            TreeNode left = null;
            TreeNode right = null;
            if(i<arr.length){
                if(arr[i]!=null){
                    left = new TreeNode(arr[i]);
                    queue.offer(left);
                }
                if(i<arr.length-1 && arr[i+1]!=null){
                    right = new TreeNode(arr[i+1]);
                    queue.offer(right);
                }
            }
            TreeNode curr = queue.poll();
            curr.left = left;
            curr.right = right;
            i += 2;
        }
    }


    void dfs(Deque<TreeNode> deque, TreeNode root, int k){
        if(root == null || found){
            return;
        }
        deque.offerLast(root);
        if(root.val != k){
            dfs(deque, root.left, k);
            dfs(deque, root.right, k);
        }else{
            found = true;
        }
        if(!found){
            deque.pollLast();
        }
    }

    void measureLeaves(Map<TreeNode, Integer> distanceMap, Set<TreeNode> seen, TreeNode node, int factor){
        if(node == null || seen.contains(node)){
            return;
        }
        if(node.left == null && node.right == null){
            distanceMap.put(node, factor);
        }else{
            measureLeaves(distanceMap, seen, node.left, factor+1);
            measureLeaves(distanceMap, seen, node.right, factor+1);
        }
        seen.add(node);
    }

    public int findClosestLeaf(TreeNode root, int k) {
        Deque<TreeNode> deque = new LinkedList<>();
        found = false;
        dfs(deque, root, k);
        Map<TreeNode, Integer> distanceMap = new HashMap<>();
        Set<TreeNode> seen = new HashSet<>();
        int factor = 1;
        while(!deque.isEmpty()){
            TreeNode node = deque.pollLast();
            measureLeaves(distanceMap, seen, node, factor);
            factor++;
        }

        Integer dist = null;
        TreeNode node = null;
        for(Map.Entry<TreeNode, Integer> entry: distanceMap.entrySet()){
            if(node == null || dist>entry.getValue()){
                node = entry.getKey();
                dist = entry.getValue();
            }
        }
        return node.val;
    }



}

class NearestLeafNodeDriver{
    public static void main(String[] args){
        Integer[] arr = {1,2,3,null,null,4,5,6,null,null,7,8,9,10};
        ClosestLeafInBinaryTree nearestLeafNode = new ClosestLeafInBinaryTree(arr);
        System.out.println(nearestLeafNode.findClosestLeaf(nearestLeafNode.root, 4));

    }
}

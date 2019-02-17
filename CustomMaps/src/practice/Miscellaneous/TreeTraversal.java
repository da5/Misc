package practice.Miscellaneous;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TreeTraversal {

    public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
    }

    public TreeNode init(){
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        return root;
    }

    public List<Integer> postorderTraversal1(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        Stack<Integer> orders = new Stack<>();

        while(root!=null){
            stack.push(root);
            orders.push(0);
            root = root.left;
        }
        while(!stack.isEmpty()){
            TreeNode node = stack.pop();
            int order = orders.pop();
            if(order == 0){
                stack.push(node);
                orders.push(1);
                node = node.right;
                while(node!=null){
                    stack.push(node);
                    orders.push(0);
                    node = node.left;
                }
            }else if(order == 1){
                result.add(node.val);
            }


        }
        return result;
    }

    public List<Integer> postorderTraversal2(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();

        if(root!=null){
            stack1.push(root);
        }
        while(!stack1.isEmpty()){
            TreeNode node = stack1.pop();
            stack2.push(node);
            if(node.left!=null){
                stack1.push(node.left);
            }
            if(node.right!=null){
                stack1.push(node.right);
            }
        }
        while(!stack2.isEmpty()){
            result.add(stack2.pop().val);
        }
        return result;
    }

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        while(root!=null){
            stack.push(root);
            root = root.left;
        }
        while(!stack.isEmpty()){
            TreeNode node = stack.pop();
            result.add(node.val);
            node = node.right;
            while(node!=null){
                stack.push(node);
                node = node.left;
            }
        }
        return result;
    }

    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        if(root!=null){
            stack.push(root);
        }
        while(!stack.isEmpty()){
            TreeNode node = stack.pop();
            result.add(node.val);
            if(node.right != null){
                stack.push(node.right);
            }
            if(node.left != null){
                stack.push(node.left);
            }

        }
        return result;
    }


}

class TreeTraversalDriver{
    public static void main(String[] args){
        TreeTraversal treeTraversal = new TreeTraversal();
        TreeTraversal.TreeNode root = treeTraversal.init();
        treeTraversal.postorderTraversal2(root);
    }
}

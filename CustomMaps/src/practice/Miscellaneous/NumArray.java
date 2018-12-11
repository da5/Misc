package practice.Miscellaneous;

/*
Range Sum query with mutation
 */
public class NumArray {
    static class Node{
        int s;
        int l;
        int r;
        Node left;
        Node right;
        Node(int s, int l, int r){
            this.s = s; this.l = l; this.r = r;  left = null; right = null;
        }
    }

    int[] nums;
    int[] sum;
    Node root;

    int getSubarraySum(int i, int j){
        int res = sum[j];
        if(i>0){
            res -= sum[i-1];
        }
        return res;
    }

    int[] getSumArray(int[] nums){
        int n = nums.length;
        int[] sum = new int[n];
        sum[0] = nums[0];
        for(int i =1; i<n; i++){
            sum[i] = nums[i] + sum[i-1];
        }
        return sum;
    }

    Node constructTree(int low, int high){
        Node r = new Node(getSubarraySum(low, high), low, high);
        if(low<high){
            int mid = (high-low)/2;
            r.left = constructTree(low, low+mid);
            r.right = constructTree(low+mid+1, high);
        }
        return r;
    }

    public NumArray(int[] nums) {
        if(nums.length==0){
            return;
        }
        this.nums = nums;
        sum =   getSumArray(nums);
        root = constructTree(0, nums.length-1);
    }

    public void update(int i, int val) {
        int delta = val-nums[i];
        nums[i] = val;
        Node curr = root;
        while(curr!=null){
            if(i>=curr.l && i<=curr.r){
                curr.s += delta;
                int mid = (curr.r-curr.l)/2;
                if(curr.l+mid>=i){
                    curr = curr.left;
                }else {
                    curr = curr.right;
                }
            }else{
                curr = null;
            }
        }
    }

    int sumRangeHelper(Node root, int i, int j) {
        if(root.l>=i && root.r<=j){
            return root.s;
        }
        if(root.l>j || root.r<i){
            return 0;
        }
        return sumRangeHelper(root.left, i, j) + sumRangeHelper(root.right, i, j);
    }

    public int sumRange(int i, int j) {
        if(root==null){
            return Integer.MIN_VALUE;
        }
        return sumRangeHelper(root, i, j);
    }
}

class NumArrayDriver{
//["NumArray","update","update","update","sumRange","update","sumRange","update","sumRange","sumRange","update"]
//        [[[7,2,7,2,0]],[4,6],[0,2],[0,9],[4,4],[3,8],[0,4],[4,1],[0,3],[0,4],[0,4]]
    public static void main(String[] args){
        int[] nums = {7,2,7,2,0};
        NumArray n = new NumArray(nums);

        n.update(4,6);
        n.update(0,2);
        n.update(0,9);
        System.out.println(n.sumRange(4,4));
        n.update(3,8);
        System.out.println(n.sumRange(0,4));
        n.update(4,1);
        System.out.println(n.sumRange(0,3));
        System.out.println(n.sumRange(0,4));
    }
}

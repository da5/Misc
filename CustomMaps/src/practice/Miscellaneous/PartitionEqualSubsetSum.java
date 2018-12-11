package practice.Miscellaneous;

public class PartitionEqualSubsetSum {
    int cumulative(int[] nums){
        int n = nums.length;
        int sum = 0;
        for(int i = 0 ;i<n; i++){
            sum += nums[i];
        }
        return sum;
    }

    boolean partition(int[] nums, int sum1, int sum2, int idx, int n){
        if(idx==n){
            return sum1==sum2;
        }

        int val = nums[idx];
        boolean result = partition(nums, sum1+val, sum2, idx+1, n);
        if(!result){
            result = partition(nums, sum1, sum2+val, idx+1, n);
        }
        return result;
    }

    boolean partition(int[] nums, int sum, int n){
        int k = sum/2;
        boolean[][] array = new boolean[n+1][k + 1];
        for(int i = 0; i<=n; i++ ){
            array[i][0] = true;
        }
        for(int i = 1; i<=k; i++){
            array[0][i] = false;
        }
        for(int i = 1; i<=n; i++ ){
            for(int j = 1; j<=k; j++){
                array[i][j] = array[i-1][j];
                if(nums[i-1] <= j ){
                    array[i][j] = array[i][j] || array[i-1][j-nums[i-1]];
                }
            }
        }
        return array[n][k];
    }

    public boolean canPartition(int[] nums) {
        int sum = cumulative(nums);
        return sum%2==0 && partition(nums, sum, nums.length);
    }
}

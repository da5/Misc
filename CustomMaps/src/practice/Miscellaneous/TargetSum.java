package practice.Miscellaneous;

public class TargetSum {

    static int findTargetSum(int[] arr, int sum){
        int[][] dp = new int[arr.length][2001];
        dp[0][1000 + arr[0]] = 1;
        dp[0][1000 - arr[0]] = 1;
        for(int i = 1; i< arr.length;i++){
            for(int s = -1000 ; s<= 1000; s++){
                if(dp[i-1][s+1000] > 0){
                    dp[i][s+1000+arr[i]] += dp[i-1][s+1000];
                    dp[i][s+1000-arr[i]] += dp[i-1][s+1000];
                }
            }
        }

        return dp[arr.length-1][1000+sum];
    }

    static int findTargetSum1DArray(int[] arr, int sum){
        int[] dp = new int[2001];
        dp[1000 + arr[0]] = 1;
        dp[1000 - arr[0]] = 1;
        for(int i = 1; i< arr.length;i++){
            int[] next = new int[2001];
            for(int s = -1000 ; s<= 1000; s++){
                if(dp[s+1000] > 0){
                    next[s+1000+arr[i]] += dp[s+1000];
                    next[s+1000-arr[i]] += dp[s+1000];
                }
            }
            dp = next;
        }

        return dp[1000+sum];
    }

    public static void main(String[] args){
        int[] arr = {1,1,1,1,1};
        System.out.println(findTargetSum(arr, 3));
        System.out.println(findTargetSum1DArray(arr, 3));

        int[] arr1 = {1,2,3,4};
        System.out.println(findTargetSum(arr1, 4));
        System.out.println(findTargetSum1DArray(arr1, 4));

    }
}

package practice.Miscellaneous;

//https://leetcode.com/problems/super-egg-drop/description/

public class SuperEggDrop {
    int[][] dp;

    public int superEggDrop(int K, int N) {
        dp = new int[N+1][K+1];
        return dynamicOptimized(N, K);
    }

    int recur(int floors, int eggs){
        if(dp[floors][eggs] == 0){
            int result = 1;
            if(floors <= 1){
                result = floors;
            }else if(eggs == 1){
                result = floors;
            }else{
                int min = Integer.MAX_VALUE;
                for(int i=1; i<=floors; i++){
                    min = Math.min(min, Math.max(recur(i-1, eggs-1), recur(floors-i, eggs)));
                }
                result = 1+min;
            }
            dp[floors][eggs] = result;
        }
        // System.out.println(k + ", " + n + " = " + dp[floors][eggs]);
        return dp[floors][eggs];
    }

    int dynamic(int floors, int eggs){
        for(int i=1; i<=floors; i++){
            for(int j=1; j<=eggs; j++){
                if(i==1){
                    dp[i][j] = 1;
                }else if(j==1){
                    dp[i][j] = i;
                }else{
                    int min = Integer.MAX_VALUE;
                    for(int x=1; x<i; x++){
                        min = Math.min(min, Math.max(dp[i-x][j], dp[x-1][j-1]));
                    }
                    dp[i][j] = min+1;
                }
            }
        }
        printDP(floors, eggs);
        return dp[floors][eggs];
    }

    int dynamicOptimized(int floors, int eggs){
        for(int i=1; i<=floors; i++){
            for(int j=1; j<=eggs; j++){
                if(i==1){
                    dp[i][j] = 1;
                }else if(j==1){
                    dp[i][j] = i;
                }else{
                    int low = 1, high = i;
                    int min = Integer.MAX_VALUE;
                    while(low+1 < high){
                        int x = (low+high)/2;
                        int t1 = dp[i-x][j];
                        int t2 = dp[x-1][j-1];
                        min = Math.min(min, Math.max(t1, t2));
                        if(t1<t2){
                            low = x;
                        }else if(t1>t2){
                            high = x;
                        }else{
                           low = high = x;
                        }
                    }
                    dp[i][j] = 1 + Math.min(min, Math.min(
                            Math.max(dp[i-low][j], dp[low-1][j-1]),
                            Math.max(dp[i-high][j], dp[high-1][j-1])
                    ));
                }
            }
        }
        printDP(floors, eggs);
        return dp[floors][eggs];
    }

    void printDP(int floors, int eggs){
        for(int i=1; i<=floors; i++) {
            for (int j = 1; j <= eggs; j++) {
                System.out.print(dp[i][j] + " ");
            }
            System.out.println("");
        }
    }
}

class SuperEggDropDriver {
    public static void main(String[] args){
        SuperEggDrop obj = new SuperEggDrop();
        System.out.println(obj.superEggDrop(2, 9));

    }
}

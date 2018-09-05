package practice.Miscellaneous;

public class LargestPlot {

    public static int[] largestSubArrayWithSumAtMostN(int[] a, int N){
        int sum = 0;
        int x =-1, y=-1;
        for(int size = a.length; size > 0; size--){
            sum = 0;
            x = 0; y = size-1;
            for(int i = x; i<=y; i++){
                sum+= a[i];
            }
            while(sum > N && y<a.length-1){
                sum = sum -a[x]+a[y+1];
                x++;
                y++;
            }
            if(sum <=N){
                break;
            }
        }

        int[] result = {x, y, sum};
        return result;
    }

    public static void largestPlotToBuy(int[][] area, int cols, int rows, int budget){
        int maxSum = 0, maxGrids = 0;
        int r1 = 0,r2 =0 , c1=0, c2=0;
        for(int leftCol = 0; leftCol<cols; leftCol++){
            int[] rowSum = new int[rows];
            for(int i =0; i< rows; i++){
                rowSum[i] = 0;
            }
            for(int rightCol = leftCol; rightCol<cols; rightCol++){
                for(int i =0; i< rows; i++){
                    rowSum[i] += area[i][rightCol];
                }
                int[] result = largestSubArrayWithSumAtMostN(rowSum, budget);
                int grids = (rightCol-leftCol+1)*Math.abs(result[1]-result[0]+1);
                if(maxGrids<grids && result[2] <= budget){
                    maxSum = result[2];
                    maxGrids = grids;
                    r1 = result[0];
                    r2 = result[1];
                    c1 = leftCol;
                    c2 = rightCol;
                }
            }
        }
        System.out.println(maxSum);
        System.out.println("("+r1 + ", " + c1+ ") to ("+r2+","+c2+")");
        for(int i =r1; i<=r2;i++){
            for(int j=c1;j<=c2;j++){
                System.out.print(area[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public static void main(String[] args){
        int[][] area = {
                {7,4,6},
                {8,3,5},
                {4,7,2}
        };
        largestPlotToBuy(area, 3, 3,  17);
    }
}

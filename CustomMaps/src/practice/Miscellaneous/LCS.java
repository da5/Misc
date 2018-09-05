package practice.Miscellaneous;

public class LCS {
    static int[][] getSquare(int[] a, int[] b){
        int[][] square = new int[a.length+1][b.length+1];
        for(int i = 0; i<=a.length;i++){
            square[i][0] = 0;
        }
        for(int i = 0; i<=b.length;i++){
            square[0][i] = 0;
        }
        return square;
    }

    static int[] constructSolution(int[] a, int[] b, int[][] decision, int length){

        int i = a.length;
        int j = b.length;

        int[] result = new int[length];
        int k = length-1 ;
        while(i > 0 && j > 0){
            if(decision[i][j] == 0){
                result[k--] = a[i-1];
                i--;
                j--;
//                k--;
            }else if(decision[i][j] == -1){
                i--;
            }else{
                j--;
            }
        }
        return result;

    }

    public static int[] longestCommomSubsequence (int[] a, int[] b){
        int[][] square = getSquare(a,b);
        int[][] decision = new int[a.length+1][b.length+1];
        for(int i = 1; i<=a.length; i++){
            for(int j = 1; j<=b.length; j++){
                int x = a[i-1];
                int y = b[j-1];
                if(x==y){
                    square[i][j] = 1+square[i-1][j-1];
                    decision[i][j] = 0;
                }else {
                    if(square[i][j-1] > square[i-1][j]){
                        square[i][j] = square[i][j-1];
                        decision[i][j] = 1;
                    }else{
                        square[i][j] = square[i-1][j];
                        decision[i][j] = -1;
                    }

                }
            }
        }

        return constructSolution(a,b,decision, square[a.length][b.length]);
    }

    static String printArray(int[] result){
        String str = "[ ";
        for(int i = 0; i<result.length; i++){
            str = str + result[i];
            if(i < result.length-1){
                str = str + ", ";
            }
        }
        str = str + " ] ";
        return str;
    }

    public static void main(String[] args){
        int[] a = {1,2,3,4,5,6};
        int[] b = {1,1,2,2,3,3, 4, 4,6,6};
        int[] result = longestCommomSubsequence(a,b);
        System.out.println("Length : " + result.length + " as " + printArray(result));

    }
}

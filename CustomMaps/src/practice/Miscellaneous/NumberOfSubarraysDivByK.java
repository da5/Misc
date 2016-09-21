package practice.Miscellaneous;

/**
 * Created by arindam.das on 21/09/16.
 */
public class NumberOfSubarraysDivByK {

    static int countBrute(int[] array, int k){
        int ctr = 0;
        int n = array.length;
        int[] cumArray = new int[n];
        cumArray[0] = array[0];
        for(int i = 1; i < n; i++){
            cumArray[i] = cumArray[i-1] + array[i];
        }

        for(int i = 0; i < n; i++){
            for(int j = i; j < n; j++){
                int sum = cumArray[j];
                if(i > 0){
                    sum -= cumArray[i-1];
                }
                if(sum%k == 0){
                    ctr++;
                }
            }
        }
        return ctr;
    }

    static int countOptimal(int[] array, int k){
        int ctr = 0;
        int n = array.length;
        int[] cumArray = new int[n];
        cumArray[0] = array[0];
        for(int i = 1; i < n; i++){
            cumArray[i] = cumArray[i-1] + array[i];
        }
        int[] modArray = new int[k];
        for(int i = 0; i < n; i++){
            modArray[cumArray[i]%k]++;
        }
        for(int i =0; i < k; i++){
            if(modArray[i] >= 2){
                ctr += modArray[i]*(modArray[i]-1)/2;
            }
        }
        return ctr+modArray[0];
    }

    public static void main(String[] args){
        int[] array = {9,4,1,5,4,1,2,9,8,6,3};
        int k = 6;
        System.out.println("countBrute " + countBrute(array, k));
        System.out.println("countOptimal " + countOptimal(array, k));
    }
}

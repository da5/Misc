package practice.Miscellaneous;

/**
 * Created by arindam.das on 22/09/16.
 */
public class NumberOfSubarraysInRange {
    static int nC2(int n){
        if(n<2){
            return 1;
        }
        return n*(n-1)/2;
    }

    static int[] getCumArray(int[] array){
        int[] cumArray = new int[array.length];
        cumArray[0] = array[0];
        for(int i = 1; i < array.length; i++){
            cumArray[i] = cumArray[i-1] + array[i];
        }
        return cumArray;
    }

    static int countBrute(int[] array, int k){
        int ctr = 0;
        int n = array.length;
        int[] cumArray = getCumArray(array);
        for(int i = 0; i < n; i++){
            for(int j = i; j < n; j++){
                int sum = cumArray[j];
                if(i > 0){
                    sum -= cumArray[i-1];
                }
                if(sum <= k){
                    ctr++;
                }
            }
        }
        return ctr;
    }

    static int countBrute(int[] array, int min, int max){
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
                if(sum >= min && sum <= max){
                    ctr++;
                }
            }
        }
        return ctr;
    }

    static int countOptimal(int[] array, int k){
        int ctr = 0;
        int n = array.length;
        int toSubtract;
        int[] cumArray = getCumArray(array);
        int left = -1;
        int right = -1;
        while(right < n-1){
            toSubtract = (left == -1)? 0: cumArray[left];
            while((cumArray[++right] - toSubtract) <= k && right < n-1){
//                right++;
                ctr++;
            }
//            ctr += (left == -1)? right: right - left - 1;
            while( left<right && left < n-1){
                if(cumArray[right]-cumArray[++left] > k){
                    ctr += right - left - 1;
                }else{
                    break;
                }
//                ctr++;
            }
        }

        ctr += (right - left - 1)*(right - left)/2;
        return ctr;
    }


    public static void main(String[] args){
        int[] array = {9,4,1,5,6};
        int k = 100;
        int min = 5;
        int max = 10;
        System.out.println("countBrute " + countBrute(array, k));
        System.out.println("countOptimal " + countOptimal(array, k));
//        System.out.println("================================");
//        System.out.println("countBrute " + countBrute(array, min, max));
    }
}

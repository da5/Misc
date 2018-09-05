package practice.Miscellaneous;

public class NextPermutation {
    public static void nextPermutation(int[] nums) {
        if(nums.length < 2){
            return;
        }
        int i,j;
        for(i = nums.length-1; i>0; i--){
            if(nums[i-1]<nums[i]){
                break;
            }
        }
        if(i==0){
            for(i=0, j=nums.length-1; i<j; i++,j--){
                int t = nums[i];
                nums[i] = nums[j];
                nums[j] = t;
            }
            return;
        }
        for(j = nums.length-1; j>=i; j--){
            if(nums[i-1]<nums[j]){
                break;
            }
        }
        if(i<=j){
            int t = nums[i-1];
            nums[i-1] = nums[j];
            nums[j] = t;
        }
        j = nums.length-1;
        while(i<j){
            int t = nums[i];
            nums[i] = nums[j];
            nums[j] = t;
            i++;
            j--;
        }
    }

    public static void main(String[] args){
        int arr[] = {3,2,1};
        nextPermutation(arr);
        for(int n: arr){
            System.out.println(n);
        }
    }
}

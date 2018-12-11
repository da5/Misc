package practice.Miscellaneous;

public class SearchRotatedArray {
    static int small(int[] nums, int target){
        if(nums.length == 0){
            return -1;
        }
        for(int i = 0; i<nums.length; i++){
            if(nums[i]==target){
                return i;
            }
        }
        return -1;
    }

    static int search(int[] nums, int target, int low, int high) {
        while(low<=high) {
            int mid = (low + high) / 2;
            if(target == nums[mid]){
                return mid;
            }
            if(target > nums[mid]){
                low = mid+1;
            }else{
                high = mid-1;
            }
        }
        return -1;
    }
    public static int search(int[] nums, int target) {
        if(nums.length < 3){
            return small(nums, target);
        }
        int low = 0, high = nums.length-1, mid = 0;
        if(nums[low]>nums[high]){
            while(low<high){
                mid = (low+high)/2;
                if(low<mid && nums[mid]<nums[mid-1]){
                    break;
                }
                if(mid<high && nums[mid]>nums[mid+1]){
                    mid = mid + 1;
                    break;
                }
                if(nums[mid]< nums[low]){
                    high = mid;
                }else if(nums[mid] > nums[low]){
                    low = mid;
                }
            }
            int n = nums.length-1;
            if(target <= nums[n]){
                low = mid;
                high = n;
            }else{
                low = 0;
                high = mid-1;
            }
        }
        return search(nums, target, low, high);
    }

    public static void main(String[] args){
        int[] array = {4,5,6,7,2};
        int p = search(array, 2);
    }
}

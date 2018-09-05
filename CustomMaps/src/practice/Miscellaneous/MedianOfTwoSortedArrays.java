package practice.Miscellaneous;

public class MedianOfTwoSortedArrays {
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        double result;
        int[] nums = new int[nums1.length+nums2.length];
        int i =0, j =0, k=0;
        while(i<nums1.length && j<nums2.length){
            if(nums1[i] < nums2[j]){
                nums[k++] = nums1[i++];
            }else{
                nums[k++] = nums2[j++];
            }
        }
        while (i<nums1.length){
            nums[k++] = nums1[i++];
        }
        while(j<nums2.length){
            nums[k++] = nums2[j++];
        }
        result = nums[(nums1.length+nums2.length)/2];
        if((nums1.length+nums2.length)%2 == 0){
            result = (result+nums[(nums1.length+nums2.length)/2 - 1 ])/2;
        }
        return result;
    }

    public static void main(String[] args){
        int[] arr1 = {1,3,5,7,9};
        int[] arr2 = {2,4,6,8,10};
        System.out.println(findMedianSortedArrays(arr1, arr2));

    }
}

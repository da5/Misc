package practice.Miscellaneous;
/*
    https://leetcode.com/problems/median-of-two-sorted-arrays/
 */
public class MedianOfTwoSortedArrays {
    public static double _findMedianSortedArrays(int[] nums1, int[] nums2) { //O(n+m)
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

    public static double findMedianSortedArrays(int[] A, int[] B) { //O(log n + log m)
        int n = A.length;
        int m = B.length;
        if (n > m)
            return findMedianSortedArrays(B, A); // Swapping to make A smaller

        int start = 0;
        int end = n;
        int realMidInMergedArray = (n + m + 1) / 2;

        while (start <= end) {
            int mid = (start + end) / 2;
            int leftASize = mid;
            int leftBSize = realMidInMergedArray - mid;

            int leftA
                    = (leftASize > 0) ? A[leftASize - 1] : Integer.MIN_VALUE; // checking overflow of indices
            int leftB
                    = (leftBSize > 0) ? B[leftBSize - 1] : Integer.MIN_VALUE;
            int rightA
                    = (leftASize < n) ? A[leftASize] : Integer.MAX_VALUE;
            int rightB
                    = (leftBSize < m) ? B[leftBSize] : Integer.MAX_VALUE;

            // if correct partition is done
            if (leftA <= rightB && leftB <= rightA) {
                if ((m + n) % 2 == 0)
                    return (Math.max(leftA, leftB) + Math.min(rightA, rightB)) / 2.0;
                else
                    return Math.max(leftA, leftB);
            }
            else if (leftA > rightB) {
                end = mid - 1;
            }
            else
                start = mid + 1;
        }
        return 0.0;
    }

    public static void main(String[] args){
        int[] arr1 = {1,3,5,7,9};
        int[] arr2 = {2,4,6,8,10};
        System.out.println(findMedianSortedArrays(arr1, arr2));

    }
}

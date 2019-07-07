package practice.Miscellaneous;

public class ReversePairs {

    int count;

    void merge(int[] arr, int l, int m, int r) {
        int n1 = m-l+1;
        int n2 = r-m;
        int[] arr1 = new int[n1];
        int[] arr2 = new int[n2];
        for(int i=0; i<n1; i++) {
            arr1[i] = arr[l+i];
        }
        for(int i=0; i<n2; i++) {
            arr2[i] = arr[m+1+i];
        }

        int i=0, j=0, k=l;
        while(i<n1 && j<n2) {
            if (arr1[i] < arr2[j]) {
                arr[k++] = arr1[i++];
            } else {
                for(int x=i; x<n1; x++) {
                    if((arr1[x]-arr2[j])>arr2[j]) {
                        count++;
                    }
                }
//                count += n1-i;
                arr[k++] = arr2[j++];
            }
        }

        while (i<n1) {
            arr[k++] = arr1[i++];
        }

        while (j<n2) {
            arr[k++] = arr2[j++];
        }
    }

    void mergeSort(int[] nums, int l, int r) {
        if(l<r) {
            int m = (l+r)/2;
            mergeSort(nums, l, m);
            mergeSort(nums, m+1, r);
            merge(nums, l,m,r);
        }
    }

    void compute(int[] nums) {
        count = 0;
        mergeSort(nums, 0, nums.length-1);
    }

    public int reversePairs(int[] nums) {
        if(nums.length <=1) {
            return 0;
        }
        compute(nums);
        return count;
    }
}

class ReversePairsDriver {
    public static void main(String[] args) {

        int[] arr = {Integer.MAX_VALUE,Integer.MIN_VALUE};
        ReversePairs reversePairs = new ReversePairs();
        System.out.println(reversePairs.reversePairs(arr));
    }
}
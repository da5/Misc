package practice.Miscellaneous;

public class HammerNails {
    public int solution(int[] A, int Y) {
        int n = A.length;
        int max = 0;
        int current = 0;
        for(int i=0; i<n; i++) {
            current++;
            if(i==n-1) {
                max = Math.max(max, current);
            } else if(A[i]!=A[i+1]) {
                max = Math.max(max, current+Math.min(Y, (n-i-1)));
                current = 0;
            }
        }
        return max;
    }
}

class HammerNailsDriver {
    public static void main(String[] args) {
        HammerNails hammerNails = new HammerNails();
        int[] A = {1,1,2,3,3,3};
        System.out.println(hammerNails.solution(A, 4));

        int[] B = {1,1,3,3,3,4,5,5,5,5};
        System.out.println(hammerNails.solution(B, 2));

        int[] C = {5,5,5,5,5};
        System.out.println(hammerNails.solution(C, 2));

    }
}

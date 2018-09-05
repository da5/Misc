package practice.Miscellaneous;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Solution3 {

    static List<Integer> rearrange(List<Integer> elements) {
        Collections.sort(elements, new Comparator<Integer>(){
            public int compare(Integer i1, Integer i2){
                int n1 = i1.intValue();
                int n2 = i2.intValue();
                int bits1 = 0;
                while(n1 > 0){
                    if(n1%2 == 1){
                        bits1++;
                    }
                    n1 = n1/2;
                }
                int bits2 = 0;
                while(n2 > 0){
                    if(n2%2 == 1){
                        bits2++;
                    }
                    n2 = n2/2;
                }
                if(bits1 == bits2){
                    return i1-i2;
                }else{
                    return bits1-bits2;
                }
            }
        });

        return elements;
    }

    static String smallWords(String word){
        if(word.length()<2){
            return "no answer";
        }
        if((int)word.charAt(0) >= (int)word.charAt(1)){
            return "no answer";
        }
        return ""+word.charAt(1)+word.charAt(0);
    }

    static String rearrangeWord(String word) {

        if(word.length()<3){
            return smallWords(word);
        }

        String result = "";
        int i;
        for(i = word.length()-1; i>0; i--){
            if((int)word.charAt(i) > (int)word.charAt(i-1)){
                break;
            }
        }
        if(i == 0){
            return "no answer";
        }
        if(i > 0 ){
            i--;
        }
        int j;
        for(j = word.length()-1; j>i; j--) {
            if((int)word.charAt(j) > (int)word.charAt(i)){
                break;
            }
        }
        if(j<=i){
            return "no answer";
        }
        for (int p = 0; p<word.length();p++){
            if(p==i){
                result = result + word.charAt(j);
            }else if(p==j){
                result = result + word.charAt(i);
            }else{
                result = result + word.charAt(p);
            }
        }
        return result.substring(0,i+1)+ (new StringBuilder(result.substring(i+1))).reverse().toString();

    }

    public int solution(int[] A) {
        int result = 1;
        Arrays.sort(A);
        for(int i = 0; i< A.length; i++){
            if(A[i] == result){
                result++;
            }else if(A[i] > result){
                break;
            }
        }
        return result;
    }

    public static void main(String[] args){
        System.out.println(rearrangeWord("hefg"));
        System.out.println(rearrangeWord("pp"));
        System.out.println(rearrangeWord("xy"));
        System.out.println(rearrangeWord("x"));
        System.out.println(rearrangeWord(""));
        System.out.println(rearrangeWord("abcghxtr"));

    }

}

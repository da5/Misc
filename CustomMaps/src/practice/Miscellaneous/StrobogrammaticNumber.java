package practice.Miscellaneous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StrobogrammaticNumber {
    static List<String> numbers;

    public static boolean isStrobogrammatic(String num) {
        char[] arr = num.toCharArray();
        boolean result = true;
        for(int i =0,  j = arr.length-1; i <= j; i++, j--){
            if(i==j && !(arr[i]=='8' || arr[i]=='0' || arr[i]=='1')){
                result = false;
                break;
            }else{
                if((arr[i]=='6'&&arr[j]=='9')||(arr[i]=='9'&&arr[j]=='6')||
                        (arr[i]==arr[j] && (arr[i]=='8' || arr[i]=='0' || arr[i]=='1'))){

                }else{
                    result = false;
                }
            }
        }
        return result;
    }

    public static int strNumberCompare(String a, String b){
        int result = 0;
        if(a.length() != b.length()){
            result =  a.length()-b.length();
        }else{
            result = a.compareTo(b);
        }
        return result;
    }

    public static void generate(String str, String low, String high){
        if(!str.isEmpty() && strNumberCompare(str, high)>0){
            return;
        }else if(!str.isEmpty() && strNumberCompare(str, low)>=0 && strNumberCompare(str, high)<=0){
            if(isStrobogrammatic(str)){
                numbers.add(str);
            }
        }
        if(str.length()>0){
            generate(str+"0", low, high);
        }
        if(high.length()-str.length()>0){
            generate(str+"6", low, high);
            generate(str+"9", low, high);
            generate(str+"1", low, high);
            generate(str+"8", low, high);
        }

    }

    public static List<String> generateI(int n){
        List<String> current, prev;
        if(n%2==0){
            prev = new ArrayList<>(Arrays.asList(""));
        }else{
            prev = new ArrayList<>(Arrays.asList("0","1","8"));
        }
        if(n<2){
            return prev;
        }
        for(int i = n%2; i<n; i+=2){
            current = prev;
            prev = new ArrayList<>();
            for(String str : current){
                if(n-i>3){
                    prev.add("0"+str+"0");
                }
                prev.add("1"+str+"1");
                prev.add("8"+str+"8");
                prev.add("6"+str+"9");
                prev.add("9"+str+"6");

            }
        }
        return prev;
    }

    public static int strobogrammaticInRange(String low, String high) {
        int ctr = 0;
        if(low.equals("0")){
            ctr++;
        }
        numbers = new ArrayList<>();
        generate("", low, high);
        ctr += numbers.size();
        return ctr;
    }

    public static int strobogrammaticInRangeI(String low, String high) {
        int ctr = 0;

        for(int i = low.length(); i<= high.length();i++){
            for(String str: generateI(i)){
                if(strNumberCompare(str, low)>=0 && strNumberCompare(str, high)<=0){
                    ctr++;
                }
            }
        }

        return ctr;
    }

    public static String convertToTitle(int n) {
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        String result = "";
        int r;
        while(n>0){
            r = (n-1)%26;
            result = alphabet[r]+result;
            n = (n-1)/26;
        }
        return result;
    }

    public static void main(String[] args){
//        System.out.println(strobogrammaticInRangeI("50", "100"));
//        System.out.println(strobogrammaticInRangeI("0", "0"));
        System.out.println(convertToTitle(703));
    }
}

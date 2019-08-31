package practice.Miscellaneous;

import java.util.HashMap;
import java.util.Map;

public class DecodeWays2 {
    Map<String, Integer> map;
    boolean isValid(String str) {
        boolean valid = false;

        int code = Integer.parseInt(str);
        if(code>=1 && code<=26) {
            if(!(str.length()==2 && code<10)) {
                valid = true;
            }
        }
        return valid;
    }

    int numWays(String str) {
        int n = 0;
        if(str.length()>1) {
            char ch1 = str.charAt(0);
            char ch2 = str.charAt(1);
            if(ch1=='*' || ch2=='*') {
                n = map.get(str);
            } else if(isValid(str)){
                n = 1;
            }
        } else if(str.length()>0) {
            char ch = str.charAt(0);
            if(ch>='1' && ch<='9') {
                n = 1;
            } else if(ch == '*') {
                n=9;
            }
        }
        return n;
    }

    long multiply(long a, long b) {
        long p = a*b;
        return p%1000000007;
    }

    public int numDecodings(String s) {
        map = new HashMap<String, Integer>(){{
            put("*1", 2);
            put("*2", 2);
            put("*3", 2);
            put("*4", 2);
            put("*5", 2);
            put("*6", 2);
            put("*7", 1);
            put("*8", 1);
            put("*9", 1);
            put("*0", 2);

            put("0*", 0);
            put("1*", 9);
            put("2*", 6);
            put("3*", 0);
            put("4*", 0);
            put("5*", 0);
            put("6*", 0);
            put("7*", 0);
            put("8*", 0);
            put("9*", 0);

            put("**", 15);

        }};
        int n = s.length();
        long[] dp = new long[n+1];
        dp[0] = 1;
        dp[1] = numWays(s.substring(0, 1));
        for(int i=2; i<=n; i++) {
            dp[i] = multiply(dp[i-1], numWays(s.substring(i-1, i)));
            dp[i] += multiply(dp[i-2], numWays(s.substring(i-2, i)));
        }
        return (int)(dp[n]%1000000007);
    }
}

class DecodeWays2Driver {
    public static void main(String[] args) {
        DecodeWays2 decodeWays = new DecodeWays2();
        System.out.println(decodeWays.numDecodings("*"));
        System.out.println(decodeWays.numDecodings("1*"));
        System.out.println(decodeWays.numDecodings("2*"));
        System.out.println(decodeWays.numDecodings("3*"));
        System.out.println(decodeWays.numDecodings("**"));
        System.out.println(decodeWays.numDecodings("**********1111111111"));

//        System.out.println(decodeWays.numDecodings("11"));
//        System.out.println(decodeWays.numDecodings("226"));
//        System.out.println(decodeWays.numDecodings("12"));

    }
}

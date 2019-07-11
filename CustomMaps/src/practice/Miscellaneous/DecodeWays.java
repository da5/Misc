package practice.Miscellaneous;

public class DecodeWays {
    boolean isValid(String str) {
        int code = Integer.parseInt(str);
        boolean valid = false;
        if(code>=1 && code<=26) {
            if(!(str.length()==2 && code<10)) {
                valid = true;
            }
        }
        return valid;
    }

    public int numDecodings(String s) {
        int n = s.length();
        int[] dp = new int[n+1];
        dp[0] = 0;
        dp[1] = isValid(s.substring(0, 1))? 1:0;
        if(dp[1]==0) {
            return 0;
        }
        if(n>=2) {
            dp[2] = isValid(s.substring(0, 2))? 1:0;
            if(isValid(s.substring(1,2))) {
                dp[2]+=1;
            }
        }
        for(int i=3; i<=n; i++) {
            dp[i] = 0;
            if(isValid(s.substring(i-1, i)) && dp[i-1]>0) {
                dp[i] += dp[i-1];
            }
            if(isValid(s.substring(i-2, i)) && dp[i-2]>0) {
                dp[i] += dp[i-2];
            }
        }
        return dp[n];
    }
}

class DecodeWaysDriver {
    public static void main(String[] args) {
        DecodeWays decodeWays = new DecodeWays();
        System.out.println(decodeWays.numDecodings("01"));
        System.out.println(decodeWays.numDecodings("0"));
        System.out.println(decodeWays.numDecodings("1"));
        System.out.println(decodeWays.numDecodings("11"));
        System.out.println(decodeWays.numDecodings("226"));
        System.out.println(decodeWays.numDecodings("12"));

    }
}
// opt(i) = opt(i-2) + opt(i-1)
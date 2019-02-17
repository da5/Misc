package practice.Miscellaneous;

//https://leetcode.com/problems/regular-expression-matching/description/

public class RegularExpressionMatching {
    Boolean[][] matrix;

    public boolean isMatch(String s, String p) {
        matrix = new Boolean[s.length()+1][p.length()+1];
        return _isMatch(0,0, s, p);
    }

    public boolean _isMatch(int i, int j, String s, String p){
        if(matrix[i][j] != null){
            return matrix[i][j];
        }
        boolean reply = false;

        if(j==p.length()){
            reply = i==s.length();
        }else if(i==s.length()){
            if(p.length()-j>=2 && p.charAt(j+1)=='*'){
                reply = _isMatch(i,j+2, s, p);
            }else{
                reply = false;
            }
        }else{
            boolean firstCharMatch = (s.charAt(i)==p.charAt(j)) || p.charAt(j)=='.';
            if(p.length()-j>1 && p.charAt(j+1)=='*'){
                reply = firstCharMatch && _isMatch(i+1, j, s, p) || _isMatch(i, j+2, s, p);
            }else{
                reply = firstCharMatch && _isMatch(i+1, j+1, s, p);
            }

        }
        matrix[i][j] = reply;
        return reply;
    }

    public boolean isMatchBU(String s, String p) {

        if (s == null || p == null) {
            return false;
        }
        boolean[][] dp = new boolean[s.length()+1][p.length()+1];
        dp[0][0] = true;
        for (int i = 0; i < p.length(); i++) {
            if (p.charAt(i) == '*' && dp[0][i-1]) {
                dp[0][i+1] = true;
            }
        }
        for (int i = 0 ; i < s.length(); i++) {
            for (int j = 0; j < p.length(); j++) {
                if (p.charAt(j) == '.') {
                    dp[i+1][j+1] = dp[i][j];
                }
                if (p.charAt(j) == s.charAt(i)) {
                    dp[i+1][j+1] = dp[i][j];
                }
                if (p.charAt(j) == '*') {
                    if (p.charAt(j-1) != s.charAt(i) && p.charAt(j-1) != '.') {
                        dp[i+1][j+1] = dp[i+1][j-1];
                    } else {
                        dp[i+1][j+1] = (dp[i+1][j] || dp[i][j+1] || dp[i+1][j-1]);
                    }
                }
            }
        }
        return dp[s.length()][p.length()];
    }

    public boolean isMatchBU2(String s, String p) {
        boolean[][] dp = new boolean[s.length()+1][p.length()+1];
        dp[0][0] = true;
        for(int j=0; j<p.length(); j++){
            dp[0][j+1] = (p.charAt(j)=='*' && dp[0][j-1]);
        }

        for(int i=0; i<s.length(); i++){
            for(int j=0; j<p.length(); j++){
                if(s.charAt(i)==p.charAt(j) || p.charAt(j)=='.'){
                    dp[i+1][j+1] = dp[i][j];
                }else if(p.charAt(j)=='*'){
                    if(p.charAt(j-1)==s.charAt(i) || p.charAt(j-1)=='.'){
                        dp[i+1][j+1] = dp[i+1][j] || dp[i][j+1] || dp[i+1][j-1];
                    }else{
                        dp[i+1][j+1] = dp[i+1][j-1];
                    }
                }
            }
        }

        return dp[s.length()][p.length()];
    }
}



class RegularExpressionMatchingDriver{
    public static void main(String[] args){
        RegularExpressionMatching obj = new RegularExpressionMatching();
        long start = System.currentTimeMillis();
        System.out.println(obj.isMatch("aaaaaaaaaaaaab", "a*a*a*a*a*a*a*a*a*a*c"));
        System.out.println("Top Down : " + (System.currentTimeMillis()-start));
        start = System.currentTimeMillis();
        System.out.println(obj.isMatchBU("aaaaaaaaaaaaab", "a*a*a*a*a*a*a*a*a*a*c"));
        System.out.println("Bottom Up : " + (System.currentTimeMillis()-start));
        System.out.println(obj.isMatchBU2("aaaaaaaaaaaaab", "a*a*a*a*a*a*a*a*a*a*c"));
    }
}
package practice.Miscellaneous;

// https://leetcode.com/problems/wildcard-matching/description/

public class WildcardMatching {
    Boolean[][] matrix;
    boolean isMatch(String s, String p, int si, int pi) {
        if(matrix[si][pi]!=null){
            return matrix[si][pi];
        }
        boolean answer = false;
        if(pi == p.length()){
            answer = (si == s.length());
        }else if(si == s.length()){
            answer = (p.charAt(pi)=='*' &&  isMatch(s, p, si, pi+1));
        }else{
            if(s.charAt(si)==p.charAt(pi) || p.charAt(pi)=='?'){
                answer = isMatch(s, p, si+1, pi+1);
            }else if(p.charAt(pi)=='*'){
                answer = isMatch(s, p, si, pi+1) || isMatch(s, p, si+1, pi);
            }
        }
        matrix[si][pi] = answer;
        return answer;
    }
    public boolean isMatch(String s, String p) {
        matrix = new Boolean[s.length()+1][p.length()+1];
        return isMatch(s, p, 0, 0);
    }

     public boolean isMatchBU(String s, String p) {
         boolean[][] dp = new boolean[s.length()+1][p.length()+1];
         dp[s.length()][p.length()] = true;
         for(int j=p.length()-1; j>=0 ; j--){
             if(p.charAt(j)!='*'){
                 break;
             }
             dp[s.length()][j] = true;
         }
         for(int i=s.length()-1; i>=0; i--){
             for(int j=p.length()-1; j>=0; j--){
                 if(s.charAt(i)==p.charAt(j) || p.charAt(j)=='?'){
                     dp[i][j] = dp[i+1][j+1];
                 }else if(p.charAt(j)=='*'){
                     dp[i][j] = dp[i+1][j]||dp[i][j+1];
                 }
             }
         }
         return dp[0][0];
     }

    public boolean isMatchBU2(String s, String p) {
        boolean[][] dp = new boolean[s.length()+1][p.length()+1];
        dp[0][0] = true;
        for(int j=0; j<p.length(); j++){
            dp[0][j+1] = (p.charAt(j)=='*' && dp[0][j]);
        }
        for(int i=0; i<s.length(); i++){
            for(int j=0; j<p.length(); j++){
                if(s.charAt(i)==p.charAt(j) || p.charAt(j)=='?'){
                    dp[i+1][j+1] = dp[i][j];
                }else if(p.charAt(j)=='*'){
                    dp[i+1][j+1] = dp[i+1][j] || dp[i][j+1];
                }
            }
        }
        return dp[s.length()][p.length()];
    }
}

class WildcardMatchingDriver{
    public static void main(String[] args){
        WildcardMatching obj = new WildcardMatching();
        long start = System.currentTimeMillis();
        System.out.println(obj.isMatch("ahghghgdhgdhgdhjsgdhjsfgjsdgfjhgfcxyb", "a**********c?b"));
        System.out.println("Top Down : " + (System.currentTimeMillis()-start));
        start = System.currentTimeMillis();
        System.out.println(obj.isMatchBU("ahghghgdhgdhgdhjsgdhjsfgjsdgfjhgfcxyb", "a**********c?b"));
        System.out.println("Bottom Up : " + (System.currentTimeMillis()-start));
        start = System.currentTimeMillis();
        System.out.println(obj.isMatchBU2("ahghghgdhgdhgdhjsgdhjsfgjsdgfjhgfcxyb", "a**********c??b"));
        System.out.println("Bottom Up : " + (System.currentTimeMillis()-start));
        System.out.println(obj.isMatchBU2("", ""));

    }
}
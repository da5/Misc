package practice.Miscellaneous;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WordBreak {
    Map<Integer, Boolean> idxMap;

    boolean isValid(String s, List<String> wordDict, int idx, int len) {
        if(idxMap.containsKey(idx)){
            return idxMap.get(idx);
        }
        boolean valid = false;
        if(idx >= len) {
            valid = true;
        }

        for(String word: wordDict) {
            int n = word.length();
            if(idx+n <= len && s.substring(idx, idx+n).equals(word)) {
                valid = isValid(s, wordDict, idx+n, len);
                if(valid){
                    break;
                }
            }
        }

        idxMap.put(idx, valid);
        return idxMap.get(idx);
    }

    public boolean wordBreakRec(String s, List<String> wordDict) {
        idxMap = new HashMap<>();
        return isValid(s, wordDict, 0, s.length());
    }

    public boolean wordBreakDPCubic(String s, List<String> wordDict) {
        int n = s.length();
        Set<String> wordSet = new HashSet<>(wordDict);
        if(wordSet.contains(s)){
            return true;
        }
        boolean dp[][] = new boolean[n][n];
        for(int i=0; i<n; i++){
            dp[i][i] = wordSet.contains(s.substring(i, i+1));
        }
        for(int i=1; i<=n-1; i++){
            for(int st=0; st+i<n; st++){
                int en = st+i;
                for(int j=st; j<en; j++){
                    boolean p1 = (wordSet.contains(s.substring(st, j+1)) || dp[st][j]);
                    boolean p2 = (wordSet.contains(s.substring(j+1,en+1)) || dp[j+1][en]);
                    dp[st][en] |= p1&&p2;
                }
            }
        }
        return dp[0][n-1];
    }

    public boolean wordBreakQuadratic(String s, List<String> wordDict) {
        int n = s.length();
        Set<String> wordSet = new HashSet<>(wordDict);
        boolean[] dp = new boolean[n];
        for(int i=0; i<n; i++) {
            String prefix = s.substring(0, i+1);
            if(!dp[i] && wordSet.contains(prefix)) {
                dp[i] = true;
            }
            if(dp[i]) {
                if(i==n-1) {
                    return true;
                }
                for(int j=i; j<n; j++) {
                    if(!dp[j] && wordSet.contains(s.substring(i+1, j+1))) {
                        dp[j] = true;
                    }
                    if(dp[j] && j==n-1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

class WordBreakDriver{
    public static void main(String[] args) {
        System.out.println("Hello World!");
        String s = "catsanddog";
        List<String> wordDict = Arrays.asList("cats", "dog", "sand", "and", "cat");
        WordBreak wordBreak = new WordBreak();
        System.out.println(wordBreak.wordBreakRec(s, wordDict));
        System.out.println(wordBreak.wordBreakDPCubic(s, wordDict));
        System.out.println(wordBreak.wordBreakQuadratic(s, wordDict));


    }

}

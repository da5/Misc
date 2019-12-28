package practice.Miscellaneous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class WordBreak {
    Map<Integer, Boolean> idxMap;
    Map<Integer, List<String>> idxStrMap;

    boolean _wordBreakRec(String s, List<String> wordDict, int idx, int len) {
        if(idxMap.containsKey(idx)){
            return idxMap.get(idx);
        }

        boolean valid = false;
        if(idx >= len) {
            valid = true;
        } else {
            for(String word: wordDict) {
                int n = word.length();
                if(idx+n <= len && s.substring(idx, idx+n).equals(word)) {
                    valid = _wordBreakRec(s, wordDict, idx+n, len);
                    if(valid){
                        break;
                    }
                }
            }
        }
        idxMap.put(idx, valid);
        return idxMap.get(idx);
    }

    public boolean wordBreakRec(String s, List<String> wordDict) {
        idxMap = new HashMap<>();
        return _wordBreakRec(s, wordDict, 0, s.length());
    }

    List<String> _wordBreak2Rec(String s, List<String> wordDict, int idx, int len) {
        if(!idxStrMap.containsKey(idx)){
            List<String> list = new ArrayList<>();
            if(idx >= len) {
                list.add("");
            } else {
                for(String word: wordDict) {
                    int n = word.length();
                    if(idx+n <= len && s.substring(idx, idx+n).equals(word)) {
                        List<String> subList = _wordBreak2Rec(s, wordDict, idx+n, len);
                        for(String subWord: subList) {
                            list.add( word + (subWord.isEmpty()?"":" ") + subWord);
                        }
                    }
                }
            }
            idxStrMap.put(idx, list);
        }
        return idxStrMap.get(idx);
    }

    public List<String> wordBreak2Rec(String s, List<String> wordDict) {
        idxStrMap = new HashMap<>();

        return _wordBreak2Rec(s, wordDict, 0, s.length());
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
            dp[i] |= wordSet.contains(prefix);
            if(dp[i]) {
                for(int j=i; j<n; j++) {
                    dp[j] |= wordSet.contains(s.substring(i+1, j+1));
                }
            }
        }
        return dp[n-1];
    }

    public boolean wordBreakQuadratic1(String s, List<String> wordDict) {
        int n = s.length();
        Set<String> wordSet = new HashSet<>(wordDict);
        boolean[] dp = new boolean[n+1];
        dp[0] = true;
        for(int i=1; i<=n; i++) {
            for(int j=0; j<i; j++) {
                if( dp[j] && wordSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                }
            }
        }
        return dp[n];
    }

    public List<String> wordBreak2Quadratic(String s, List<String> wordDict) {
        int n = s.length();
        LinkedList<String>[] lists = new LinkedList[n+1];

        Set<String> wordSet = new HashSet<>(wordDict);
        lists[0] = new LinkedList<>();
        lists[0].add("");
        for(int i=1; i<=n; i++) {
            lists[i] = new LinkedList<>();
            for (int j = 0; j < i; j++) {
                String subStr = s.substring(j, i);
                if(lists[j].size()>0 && wordSet.contains(subStr)) {
                    for(String word: lists[j]) {
                        lists[i].add(word + (word.isEmpty()?"":" ") + subStr);
                    }
                }
            }
        }
        return lists[n];
    }
}

class WordBreakDriver{
    public static void main(String[] args) {
        System.out.println("Hello World!");
        String s = "catsanddog";
        List<String> wordDict = Arrays.asList("cats", "dog", "sand", "and", "cat", "anddog");
        WordBreak wordBreak = new WordBreak();
        System.out.println("Memoized");
        System.out.println(wordBreak.wordBreakRec(s, wordDict));
        System.out.println(wordBreak.wordBreak2Rec(s, wordDict));

        System.out.println("DP");
        System.out.println("Cubic: " + wordBreak.wordBreakDPCubic(s, wordDict));
        System.out.println("Quadratic: " + wordBreak.wordBreakQuadratic(s, wordDict));
        System.out.println("Quadratic1: " + wordBreak.wordBreakQuadratic1(s, wordDict));
        System.out.println(wordBreak.wordBreak2Quadratic(s, wordDict));
        System.out.println(wordBreak.wordBreak2Rec(s, wordDict));


        String expr = "(1+(4+55+2  ) -3)+(61   +8)";
        StringTokenizer tokenizer = new StringTokenizer(expr,"+-*/()", true);
        System.out.println(expr);
        while (tokenizer.hasMoreTokens()) {
            System.out.println(tokenizer.nextToken());
        }
    }

}

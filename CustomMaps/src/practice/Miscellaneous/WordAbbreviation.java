package practice.Miscellaneous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Given an array of n distinct non-empty strings, you need to generate minimal possible abbreviations for every word following rules below.

    Begin with the first character and then the number of characters abbreviated, which followed by the last character.
    If there are any conflict, that is more than one words share the same abbreviation, a longer prefix is used instead of only the first character until making the map from word to abbreviation become unique. In other words, a final abbreviation cannot map to more than one original words.
    If the abbreviation doesn't make the word shorter, then keep it as original.

Example:

Input: ["like", "god", "internal", "me", "internet", "interval", "intension", "face", "intrusion"]
Output: ["l2e","god","internal","me","i6t","interval","inte4n","f2e","intr4n"]

Note:

    Both n and the length of each word will not exceed 400.
    The length of each word is greater than 1.
    The words consist of lowercase English letters only.
    The return answers should be in the same order as the original array.

 */
public class WordAbbreviation {
    static class Trie{
        static class TrieEntry{
            int ctr;
            String sentence;
            TrieEntry[] children;
            TrieEntry(){
                this.children = new TrieEntry[27];
                ctr = 1;
            }
        }

        int getIdx(char c){
            if(c == ' '){
                return 26;
            }
            return (c-'a');
        }

        TrieEntry root;
        Map<String, String> groupMap;

        Trie(List<String> words){
            root = new TrieEntry();
            root.children = new TrieEntry[27];
            root.sentence = "";
            for(String word: words){
                traverse(word);
            }
            groupMap = new HashMap<>();
        }

        void traverse(String str){
            TrieEntry entry = root;
            int idx = 0;
            while(idx < str.length()){
                TrieEntry child = entry.children[getIdx(str.charAt(idx))];
                if(child == null) {
                    child = new TrieEntry();
                    entry.children[getIdx(str.charAt(idx))] = child;
                    if (idx == str.length() - 1) {
                        child.sentence = str;
                    }
                }else{
                    child.ctr++;
                }
                entry = child;
                idx++;
            }
        }

        void helper(TrieEntry root, String str, int count, boolean countStarted){
            if(root.sentence!=null && root.sentence.length()>0){
                if(count-1>0){
                    str = str+(count-1);
                }
                int n = root.sentence.length();
                str += root.sentence.charAt(n-1);
                if(str.length()<n){
                    groupMap.put(root.sentence, str);
                }
                else{
                    groupMap.put(root.sentence, root.sentence);
                }
                return;
            }
            for(int i=0; i<27; i++){
                if(root.children[i]!=null){
                    if(str.length()==0){
                        helper(root.children[i], str+(char)('a'+i), count, root.children[i].ctr==1);
                    }else if(root.children[i].ctr>1){
                        helper(root.children[i], str+(char)('a'+i), count, countStarted);
                    }else if(countStarted){
                        helper(root.children[i], str, count+1, countStarted);
                    }else{
                        helper(root.children[i], str+(char)('a'+i), count, !countStarted);
                    }
                }
            }
        }

        void collect(){
            helper(root, "", 0, false);
        }

    }

    String getGroup(String str){
        int n = str.length();
        return str.substring(0,1) + (n-2) + str.substring(n-1);
    }

    Map<String, String> getGroupAbbr(List<String> group){
        Trie trie = new Trie(group);
        trie.collect();
        return new HashMap<>(trie.groupMap);
    }

    public List<String> wordsAbbreviation(List<String> dict) {
        Map<String, List<String>> map = new HashMap<>();
        for(String str: dict){
            String group = getGroup(str);
            if(!map.containsKey(group)){
                map.put(group, new ArrayList<>());
            }
            map.get(group).add(str);
        }
        Map<String, String> resultMap = new HashMap<>();
        List<String> result = new ArrayList<>();
        for(String key: map.keySet()){
            List<String> group = map.get(key);
            resultMap.putAll(getGroupAbbr(group));
        }


        for(String word: dict){
            result.add(resultMap.get(word));
        }
        return result;
    }
}

class WordAbbreviationDriver{
    public static void main(String[] args){
        WordAbbreviation wordAbbreviation = new WordAbbreviation();
        List<String> input = Arrays.asList("like", "god", "internal", "me", "internet", "interval", "intension", "face", "intrusion");
        List<String> result = wordAbbreviation.wordsAbbreviation(input);
        int n = input.size();
        for(int i =0; i<n ; i++){
            System.out.println(input.get(i) + " -> " + result.get(i));
        }
    }

}

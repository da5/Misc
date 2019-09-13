package practice.Miscellaneous;

import java.util.HashMap;
import java.util.Map;

public class MinimumWindowSubstring {
    Map<Character, Integer> stringMap;
    Map<Character, Integer> patternMap;

    void addToMap(char c) {
        stringMap.putIfAbsent(c, 0);
        int count = stringMap.get(c);
        stringMap.put(c, count+1);
    }

    void removeFromMap(char c) {
        int count = stringMap.get(c)-1;
        if(count==0) {
            stringMap.remove(c);
        }else{
            stringMap.put(c, count);
        }
    }

    boolean compareMaps() {
        for(Character c: patternMap.keySet()) {
            if(!(stringMap.containsKey(c) && stringMap.get(c)>=patternMap.get(c))) {
                return false;
            }
        }
        return true;
    }

    int initialize(String s, String t) {
        stringMap = new HashMap<>();
        patternMap = new HashMap<>();
        for(int i=0; i<t.length(); i++) {
            patternMap.putIfAbsent(t.charAt(i), 0);
            int count = patternMap.get(t.charAt(i));
            patternMap.put(t.charAt(i), count+1);
        }
        int n = s.length();
        int i=0;
        for(; i<n && !compareMaps(); i++) {
            char c = s.charAt(i);
            if(patternMap.containsKey(c)) {
                addToMap(c);
            }
        }
        return i;
    }

    public String minWindow(String s, String t) {
        stringMap = new HashMap<>();
        int minLength = initialize(s, t);
        if(!compareMaps()) {
            return "";
        }
        int mini=0, minj=minLength;
        int i=0, j=minLength, n=s.length();
        while(j<n) {
            while(i<j) {
                char c = s.charAt(i);
                if(!patternMap.containsKey(c)) {
                    i++;
                } else if(stringMap.get(c)>patternMap.get(c)) {
                    removeFromMap(c);
                    i++;
                } else {
                    break;
                }
                if(j-i < minj-mini) {
                    minj=j;
                    mini=i;
                }
            }


            char c = s.charAt(j++);
            if(patternMap.containsKey(c)) {
                addToMap(c);
            }

        }
        while(i<n) {
            char c = s.charAt(i);
            if(!patternMap.containsKey(c)) {
                i++;
            } else if(stringMap.get(c)>patternMap.get(c)) {
                removeFromMap(c);
                i++;
            } else {
                break;
            }
            if(j-i < minj-mini) {
                minj=j;
                mini=i;
            }
        }
        return s.substring(mini, minj);
    }
}

class MinimumWindowSubstringDriver {
    public static void main(String[] args){
        MinimumWindowSubstring minimumWindowSubstring = new MinimumWindowSubstring();
        System.out.println(minimumWindowSubstring.minWindow("ADOBECODEBANC", "OEC"));
        System.out.println(minimumWindowSubstring.minWindow("ADOBECODEBANC", "EOEC"));
        System.out.println(minimumWindowSubstring.minWindow("A", "AA"));
        System.out.println(minimumWindowSubstring.minWindow("AA", "AA"));
    }
}

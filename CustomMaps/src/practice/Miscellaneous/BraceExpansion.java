package practice.Miscellaneous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
https://leetcode.com/problems/brace-expansion/
 */
public class BraceExpansion {

    List<String> getList(String str) {
        List<String> list = new ArrayList<>();
        for(String s: str.split(",")) {
            list.add(s);
        }
        Collections.sort(list);
        return list;
    }

    List<List<String>> fragment(String str) {
        List<List<String>> fragments = new ArrayList<>();
        while(str.indexOf('}') >= 0) {
            int idx = str.indexOf('{');
            if(idx>0) {
                fragments.add(getList(str.substring(0, idx)));
            }
            int endIdx = str.indexOf('}');
            fragments.add(getList(str.substring(idx+1, endIdx)));
            str = str.substring(endIdx+1);
        }
        if(!str.isEmpty()) {
            fragments.add(getList(str));
        }
        return fragments;
    }

    void helper(List<String> result, String temp, List<List<String>> fragments, int idx) {
        if(idx == fragments.size()) {
            result.add(temp);
        } else {
            List<String> fragment = fragments.get(idx);
            for(String s: fragment) {
                helper(result, temp+s, fragments, idx+1);
            }
        }

    }

    public String[] expand(String S) {
        List<String> result = new ArrayList<>();
        helper(result, "", fragment(S), 0);
        return result.stream().toArray(String[]::new);
    }
}

class BraceExpansionDriver {
    public static void main(String[] args) {
        BraceExpansion braceExpansion = new BraceExpansion();
        for(String str: braceExpansion.expand("{a,b,c}d{e,f}")) {
            System.out.println(str);
        }

    }
}

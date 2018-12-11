package practice.Miscellaneous;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
Given a string which contains only lowercase letters, remove duplicate letters so that every letter appear once and only once.
You must make sure your result is the smallest in lexicographical order among all possible results.

Example 1:

Input: "bcabc"
Output: "abc"

Example 2:

Input: "cbacdcbc"
Output: "acdb"


 */
public class RemoveDuplicateLetters {
    static class CharIndex{
        char ch;
        int idx;
        CharIndex(char c, int i){
            ch =c; idx=i;
        }
    }

    CharIndex getLowestCharTillIndex(Map<Character, Integer> map1, CharIndex cidx, String s, CharIndex lastIdx){
        char res = cidx.ch;
        int idx = cidx.idx;
        for(int i = (lastIdx==null)?0:lastIdx.idx+1; i<cidx.idx; i++){
            if(s.charAt(i)<res && map1.containsKey(s.charAt(i))){
                res =  s.charAt(i);
                idx = i;
            }else if(s.charAt(i)==res && i<idx){
                idx = i;
            }
        }
        map1.remove(res);
        return new CharIndex(res, idx);
    }

    public String removeDuplicateLetters(String s) {
        Map<Character, Integer> map1 = new HashMap<>();
        for(int i=0; i<s.length(); i++){
            map1.put(s.charAt(i), i);
        }

        CharIndex[] idxArr = new CharIndex[map1.size()];
        int i =0;
        for(Map.Entry<Character, Integer> entry: map1.entrySet()){
            idxArr[i++] = new CharIndex(entry.getKey(), entry.getValue());
        }

        Arrays.sort(idxArr,
                (CharIndex i1, CharIndex i2) ->  ((i1.idx==i2.idx)?i1.ch-i2.ch:i1.idx-i2.idx)
        );

        char[] arr = new char[idxArr.length];
        int j = 0;
        CharIndex ci = null;
        for(i=0; i<idxArr.length; i++){
            if(map1.containsKey(idxArr[i].ch)){

                do{
                    ci = getLowestCharTillIndex(map1, idxArr[i], s, ci);
//                    System.out.println(ci.idx);
                    arr[j++] = ci.ch;
                }while(ci.ch!=idxArr[i].ch);
            }

        }
        String str = new String(arr);
        return str;
    }
}

class RemoveDuplicateLettersDriver{
    public static void main(String[] args){
        RemoveDuplicateLetters obj = new RemoveDuplicateLetters();
        System.out.println(obj.removeDuplicateLetters("eywdgenmcnzhztolafcfnirfpuxmfcenlppegrcalgxjlajxmphwidqqtrqnmmbssotoywfrtylm"));
    }
}
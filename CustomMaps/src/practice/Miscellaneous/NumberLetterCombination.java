package practice.Miscellaneous;

import java.util.*;

public class NumberLetterCombination {
    static List<String> result;
    static Map<Character, String> digitMap;

    static void generate(String digits, String builder){
        if(digits.isEmpty()){
            result.add(builder);
            return;
        }
        Character c = digits.charAt(0);
        String letters = digitMap.get(c);
        for(int i =0; i< letters.length(); i++){
            generate(digits.substring(1), builder + letters.charAt(i));
        }
    }

    static void initialize(){
        result = new ArrayList<>();
        digitMap = new HashMap<>();
        digitMap.put('2', "abc");
        digitMap.put('3', "def");
        digitMap.put('4', "ghi");
        digitMap.put('5', "jkl");
        digitMap.put('6', "mno");
        digitMap.put('7', "pqrs");
        digitMap.put('8', "tuv");
        digitMap.put('9', "wxyz");
    }

    public static List<String> letterCombinations(String digits) {
        initialize();
        generate(digits, "");
        return result;
    }

    public static void main(String[] args){
        List<String> strings = letterCombinations("234");
        System.out.println(strings);

    }
}

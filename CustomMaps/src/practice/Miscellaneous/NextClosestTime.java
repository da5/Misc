package practice.Miscellaneous;

import java.util.*;

public class NextClosestTime {
    static List<String> strings;

    static void generate(int i, Set<Character> digits, String str){
        if(i == 4){
            if(isValid(str)) {
                strings.add(str);
            }
            return;
        }
        for(Character c: digits){
            generate(i+1, digits, str+c);
        }
    }

    static boolean isValid(String string){
        int h = Integer.parseInt(string.substring(0,2));
        int m = Integer.parseInt(string.substring(2));
        return (h>=0 && h<=23) && (m>=0 && m<=59);
    }

    public static String nextClosestTime(String time) {
        Set<Character> digits = new HashSet<>();
        strings = new ArrayList<>();
        String timeStr = "";
        for(char c: time.toCharArray()){
            if(c != ':'){
                digits.add(c);
                timeStr += c;
            }
        }
        generate(0, digits, "");
        Collections.sort(strings);
        String[] strArray = strings.toArray(new String[strings.size()]);
        String result = null;
        if(strArray[0].equals(timeStr) && strArray.length==1){
            result = strArray[0];
        }else if(strArray[0].equals(timeStr) && strArray.length>1){
            result = strArray[1];
        }else if(strArray[strArray.length-1].equals(timeStr) && strArray.length>1){
            result = strArray[0];
        }else{
            for(int i =1; i<strArray.length-1;i++){
                if(strArray[i].equals(timeStr)) {
                        result = strArray[i + 1];
                }
            }
        }
        return result.substring(0,2)+":"+result.substring(2);
    }

    public static void main(String[] args){
        System.out.println(nextClosestTime("23:59"));
    }
}

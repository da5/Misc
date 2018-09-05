package practice.Miscellaneous;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class FullJustify {

    private static class Sum{
        public char s;
        public char c;

        public Sum(char s, char c) {
            this.s = s;
            this.c = c;
        }
    }

    private static Sum add(char a, char b, char c){
        int ones = 0;
        if(a == '1'){
            ones++;
        }
        if(b == '1'){
            ones++;
        }
        if(c == '1'){
            ones++;
        }
        char s = (ones==1 || ones ==3)?'1':'0';
        char ca = (ones>1)?'1':'0';
//        System.out.println(a + " " +b + " " +c+ " " +s+ " " +ca);
        return new Sum(s,ca);
    }

    private static String reverse(String o){
        return new StringBuilder(o).reverse().toString();
    }

    public static String addBinary(String a, String b) {
        int l1 = a.length();
        int l2 = b.length();
        int min = (l1<l2)?l1:l2;
        String s1 = reverse(a);
        String s2 = reverse(b);
        char carry = '0';
        String result = "";
        for(int i=0; i<min; i++){
            Sum sum = add(s1.charAt(i), s2.charAt(i), carry);
            result += sum.s;
            carry = sum.c;
        }
        for(int i = min; i<l1;i++){
                Sum sum = add(s1.charAt(i), '0', carry);
                result += sum.s;
                carry = sum.c;
        }
        for(int i = min; i<l2;i++){
                Sum sum = add(s2.charAt(i), '0', carry);
                result += sum.s;
                carry = sum.c;
        }
        if(carry == '1'){
            result+=carry;
        }
        return reverse(result);
    }

    public static String createLineString(List<String> words, int maxWidth, int wordLength, int wordCount){
        String str = "";

        if(wordCount == 1){
            str = words.get(0);

        }else {
            int perWordSpace = (maxWidth - wordLength) / (wordCount - 1);
            int extraSpaces = (maxWidth - wordLength) % (wordCount - 1);
            int gapCount = wordCount - 1;
            String gap = "";
            for (int i = 0; i < perWordSpace; i++) {
                gap += " ";
            }
            for (String word : words) {
                str += word;
                if (gapCount > 0) {
                    str += gap;
                    gapCount--;
                    if (extraSpaces > 0) {
                        str += " ";
                        extraSpaces--;
                    }
                }

            }
        }
//        int pad = maxWidth-str.length();
//        for(int i =0; i< pad; i++){
//            str += " ";
//        }
        return padRight(str, maxWidth);
    }

    public static List<String> fullJustify(String[] words, int maxWidth) {
        List<String> lines = new ArrayList<>();
        int wordCount = 0;
        int wordLength = 0;
        List<String> line = new ArrayList<>();
        for(String word: words){
            if(wordLength+word.length()+wordCount <=maxWidth ){
                wordCount++;
                wordLength+=word.length();
                line.add(word);
            }else{
                lines.add(createLineString(line, maxWidth, wordLength, wordCount));
                line = new ArrayList<>();
                line.add(word);
                wordCount = 1;
                wordLength = word.length();
            }
        }

        if(line.size()>0){
            String str = "";
            for(String word: line){
                str+= word+" ";
            }
            str = str.trim();
            lines.add(padRight(str, maxWidth));
        }

        return lines;
    }

    public static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }

    public static void main(String[] args){
//        String[] words = {"Science","is","what","we","understand","well","enough","to","explain",
//                "to","a","computer.","Art","is","everything","else","we","do"};
//        int maxWidth = 20;
//        List<String> lines  = fullJustify(words, maxWidth);
//        for(String line : lines){
//            System.out.println(line);
//        }

        String[] words = {"ask","not","what","your","country","can","do","for","you","ask","what","you","can","do","for","your","country"};
        int maxWidth = 16;
        List<String> lines  = fullJustify(words, maxWidth);
        for(String line : lines){
            System.out.println(line+"|");
        }

    }
}

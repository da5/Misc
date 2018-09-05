package practice.Miscellaneous;

import java.util.HashMap;
import java.util.Map;
/*
* Given two string check if they can be made equivalent by performing some operations on one or both string.

swapEven:swap a character at an even-numbered index with a character at another even-numbered index

swapOdd:swap a character at an odd-numbered index with a character at another odd-numbered index
* */
public class EvenOddPositionSwap {

    private static boolean check(String a, String b) {
        if (a.length() != b.length()) {
            return false;
        }
        if (a.equals(b)) {
            return true;
        }
        Map<Character, Integer> oddMap = new HashMap<>();
        Map<Character, Integer> evenMap = new HashMap<>();
        for (int i = 0; i < a.length(); i++) {
            Character c = a.charAt(i);
            if (i % 2 == 0) {
                evenMap.putIfAbsent(c, 0);
                int count = evenMap.get(c);
                evenMap.put(c, count + 1);
            } else {
                oddMap.putIfAbsent(c, 0);
                int count = oddMap.get(c);
                oddMap.put(c, count + 1);
            }
        }
        for (int i = 0; i < b.length(); i++) {
            Character c = b.charAt(i);

            if (i % 2 == 0) {
                Integer count = evenMap.get(c);
                if (count == null) {
                    break;
                }
                count--;
                if (count == 0) {
                    evenMap.remove(c);
                } else {
                    evenMap.put(c, count);
                }
            } else {
                Integer count = oddMap.get(c);
                if (count == null) {
                    break;
                }
                count--;
                if (count == 0) {
                    oddMap.remove(c);
                } else {
                    oddMap.put(c, count);
                }
            }

        }
        return evenMap.isEmpty() && oddMap.isEmpty();
    }

    public static void main(String[] args) {
        System.out.println(check("cabd", "bdca"));
        System.out.println(check("dcba", "abcd"));
        System.out.println(check("abcd", "abcdcd"));
        System.out.println(check("cdba", "cdba"));



    }

}

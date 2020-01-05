package practice.Miscellaneous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/*
    https://leetcode.com/problems/expression-add-operators
     */
public class ExpressionAddOperator {

    private void _helper(String prefix, String suffix, Set<String> result, long partial, long previous, char prevOperator, int target) {
        if(partial==target && suffix.length()==0) {
            result.add(prefix);
            return;
        }
        for(int i=1; i<=suffix.length(); i++) {
            String str = suffix.substring(0,i);
            if(str.length()>1 && str.charAt(0)=='0') {
                break;
            }
            int num;
            try {
                num = Integer.parseInt(str);
            } catch (Exception e) {
                continue;
            }
            if(prefix.equals("")) {
                _helper(str, suffix.substring(i), result, partial+num, num, '+',target);
            } else {
                _helper(prefix + "+" +str, suffix.substring(i), result, partial+num, num, '+', target);
                _helper(prefix + "-" +str, suffix.substring(i), result, partial-num, -1*num, '-',target);
                _helper(prefix + "*" +str, suffix.substring(i), result,
                        (prevOperator=='*')? previous*num: partial-previous+previous*num,
                        num, '*',target);
            }
        }
    }

    private String buildExpression(List<String> components) {
        StringBuilder builder = new StringBuilder();
        components.forEach(c -> builder.append(c));
        return builder.toString();
    }

    private void _helper1(Set<String> result, List<String> components, int idx, String num, long partial, long previous, char prevOperator, int target ) {
        if(idx==num.length() && partial==target) {
            result.add(buildExpression(components));
        }
        String strN = num.substring(idx, idx+1);
        int n = Integer.valueOf(strN);
        if(components.size()==0) {
            components.add(strN);
            _helper1(result, components, idx+1, num, partial+n, n, '+', target);
        } else {
            if(prevOperator=='+') {
                partial -= previous;
                components.remove(components.size()-1);
                components.remove(components.size()-1);
                _helper1(result, components, idx+1, num, partial, previous*10+n, '+', target);
            }
        }
    }

    public Set<String> _addOperators(String num, int target) {
        Set<String> set = new HashSet<>();
        _helper("", num, set, 0, 0, '+',target);
        return set;
    }

    public List<String> addOperators(String num, int target) {
        return new ArrayList<>(_addOperators(num, target));
    }
}

class ExpressionAddOperatorDriver {
    public static void main(String[] args) {
        ExpressionAddOperator calculator = new ExpressionAddOperator();

        Set<String> expected = new HashSet<>(
                Arrays.asList("1*2*3*4*5-6-78+9","1*2*3*4+5+6-7+8+9","1*2*3+4+5+6+7+8+9","1*2*3+4+5-6*7+8*9","1*2*3+4-5*6+7*8+9","1*2*3+4-5*6-7+8*9","1*2*3-4*5+6*7+8+9","1*2*3-4*5-6+7*8+9",
                        "1*2*3-4*5-6-7+8*9","1*2*3-45+67+8+9","1*2*34+56-7-8*9","1*2*34-5+6-7-8-9","1*2+3*4-56+78+9","1*2+3+4+5*6+7+8-9","1*2+3+4-5+6*7+8-9","1*2+3+4-5-6+7*8-9","1*2+3+45+67-8*9",
                        "1*2+3-45+6+7+8*9","1*2+34+5-6-7+8+9","1*2+34+56-7*8+9","1*2+34-5+6+7-8+9","1*2+34-56+7*8+9","1*2+34-56-7+8*9","1*2-3*4+5+67-8-9","1*2-3+4-5-6*7+89","1*2-3-4*5+67+8-9",
                        "1*2-3-4+56-7-8+9","1*2-34+5*6+7*8-9","1*23+4*5-6+7-8+9","1*23-4-56-7+89","1+2*3*4*5+6+7-89","1+2*3*4+5*6+7-8-9","1+2*3*4-5+6*7-8-9","1+2*3+4*5*6+7-89","1+2*3+4*5-6+7+8+9",
                        "1+2*3-4-5-6*7+89","1+2*34-5*6+7+8-9","1+2+3*4*5+6-7-8-9","1+2+3*4+5+6*7-8-9","1+2+3*45-6-78-9","1+2+3+4+5+6+7+8+9","1+2+3+4+5-6*7+8*9","1+2+3+4-5*6+7*8+9","1+2+3+4-5*6-7+8*9",
                        "1+2+3-4*5+6*7+8+9","1+2+3-4*5-6+7*8+9","1+2+3-4*5-6-7+8*9","1+2+3-45+67+8+9","1+2-3*4*5+6+7+89","1+2-3*4+5*6+7+8+9","1+2-3*4-5+6*7+8+9","1+2-3*4-5-6+7*8+9","1+2-3*4-5-6-7+8*9",
                        "1+2-3+4*5+6*7-8-9","1+2-3+45+6-7-8+9","1+2-3+45-6+7+8-9","1+2-3-4-5*6+7+8*9","1+2-3-45-6+7+89","1+2-34+5+6+7*8+9","1+2-34+5+6-7+8*9","1+2-34-5-6+78+9","1+23*4+5-6-7*8+9",
                        "1+23*4-5-6*7+8-9","1+23*4-56+7-8+9","1+23+4+5+6+7+8-9","1+23+4-5*6+7*8-9","1+23+4-5-67+89","1+23-4*5+6*7+8-9","1+23-4*5-6+7*8-9","1+23-4-5+6+7+8+9","1+23-4-5-6*7+8*9",
                        "1+23-45+67+8-9","1-2*3*4+5-6+78-9","1-2*3*4-5-6+7+8*9","1-2*3+4*5+6+7+8+9","1-2*3+4*5-6*7+8*9","1-2*3+4+5+6*7+8-9","1-2*3+4+5-6+7*8-9","1-2*3+4+56+7-8-9","1-2*3+45-67+8*9",
                        "1-2*3-4+5*6+7+8+9","1-2*3-4-5+6*7+8+9","1-2*3-4-5-6+7*8+9","1-2*3-4-5-6-7+8*9","1-2*34+5*6-7+89","1-2+3*4*5-6-7+8-9","1-2+3+4-5*6+78-9","1-2+3+45+6-7+8-9","1-2+3-4*5-6+78-9",
                        "1-2+3-45+6-7+89","1-2-3*4+5+6+7*8-9","1-2-3*4-5-6+78-9","1-2-3+4-5+67-8-9","1-2-3+45-6-7+8+9","1-2-34+5+6+78-9","1-2-34+56+7+8+9","1-2-34-5+6+7+8*9","1-23*4+5+6*7+89","1-23+4*5-6*7+89",
                        "1-23+4-5+67-8+9","1-23+45-67+89","1-23-4+5+67+8-9","1-23-4-5-6-7+89","12*3*4-5*6-78+9","12*3+4+5+6-7-8+9","12*3+4+5-6+7+8-9","12*3-4-5-6+7+8+9","12*3-4-56+78-9","12+3*4+5+6-7+8+9",
                        "12+3*45-6-7-89","12+3+4-56-7+89","12+3-4*5+67-8-9","12+3-45+6+78-9","12+34-5-6-7+8+9","12-3*4*5+6+78+9","12-3*4-5+67-8-9","12-3+4*5+6-7+8+9","12-3+4+56-7-8-9","12-3-4+5*6-7+8+9",
                        "12-3-4-56+7+89","12-3-45-6+78+9"));

        Set<String> actual = calculator._addOperators("123456789", 45);

        expected.removeAll(actual);

        System.out.println(expected);

//        System.out.println(calculator.addOperators("123456789", 45));
    }
}


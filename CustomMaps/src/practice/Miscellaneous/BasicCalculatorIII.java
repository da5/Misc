package practice.Miscellaneous;


import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/*
    https://leetcode.com/problems/basic-calculator-iii/
 */
public class BasicCalculatorIII {
    static class Token {
        boolean isOperand;
        int value;
        char sign;
        String str;

        Token(String s) {
            try {
                value = Integer.parseInt(s);
                isOperand = true;
            } catch (Exception e) {
                sign = s.charAt(0);
                isOperand = false;
            }
        }

        int precedence() {
            if(!isOperand) {
                if(sign=='/' || sign=='*') {
                    return 3;
                } else if(sign=='+' || sign=='-') {
                    return 2;
                }
            }
            return -1;
        }
    }

    private List<Token> getTokens(String s) {
        StringTokenizer tokenizer = new StringTokenizer(s, "+-*/()", true);
        List<Token> tokens = new ArrayList<>();
        while(tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();
            if(!token.isEmpty() && !" ".equals(token)) {
                tokens.add(new Token(token));
            }
        }
        return tokens;
    }

    private List<Token> normalizeTokens(List<Token> tokens) {
        List<Token> normalizedTokens = new ArrayList<>();
        Token prev = null;
        boolean nextOperandNegative = false;
        for(Token token: tokens) {
            boolean isUnaryMinus = false;
            if(token.isOperand) {
                if(nextOperandNegative) {
                    token.value *= -1;
                }
            } else {
                if(token.sign=='-') {
                    if(prev==null || !prev.isOperand) {
                        isUnaryMinus = true;
                    }
                }
            }
            if(isUnaryMinus) {
                nextOperandNegative = true;
            } else {
                normalizedTokens.add(token);
                nextOperandNegative = false;
            }
            prev = token;
        }

        return normalizedTokens;
    }


    private List<Token> preparePostFix(List<Token> tokens) {
        Deque<Token> stack = new LinkedList<>();
        List<Token> postfix = new ArrayList<>();
        for(Token token: tokens) {
            if(token.isOperand) {
                postfix.add(token);
            } else {
                if(token.sign=='(') {
                    stack.push(token);
                } else if(token.sign==')') {
                    while(!stack.isEmpty() && stack.peek().sign!='(') {
                        postfix.add(stack.pop());
                    }
                    stack.pop();
                } else {
                    if(stack.isEmpty() || stack.peek().precedence()<token.precedence()) {
                        stack.push(token);
                    } else {
                        while(!stack.isEmpty() && stack.peek().precedence()>=token.precedence()) {
                            postfix.add(stack.pop());
                        }
                        stack.push(token);
                    }
                }
            }
        }
        while(!stack.isEmpty()) {
            postfix.add(stack.pop());
        }
        return postfix;
    }

    private String print(List<Token> postfix) {
        StringBuilder builder = new StringBuilder();
        for(Token token: postfix) {
            if(token.isOperand) {
                builder.append(token.value);
            } else {
                builder.append(token.sign);
            }
            builder.append(" ");
        }
        return builder.toString();
    }

    private int evaluate(List<Token> postfix) {
        Deque<Integer> stack = new LinkedList<>();
        for(Token token: postfix) {
            if(token.isOperand) {
                stack.push(token.value);
            } else {
                int op2 = stack.pop();
                int result = stack.pop();
                if(token.sign=='+') {
                    result += op2;
                } else if(token.sign=='-') {
                    result -= op2;
                } else if(token.sign=='*') {
                    result *= op2;
                } else if(token.sign=='/') {
                    result /= op2;
                }
                stack.push(result);
            }
        }
        return stack.pop();
    }

    public int calculate(String s) {
        List<Token> tokens = normalizeTokens(getTokens(s));
//        System.out.println(print(tokens));
        List<Token> postfix = preparePostFix(tokens);
//        System.out.println(print(tokens));
        return evaluate(postfix);
    }
}

class BasicCalculatorIIIDriver {
    public static void main(String[] args) {
        BasicCalculatorIII calculator = new BasicCalculatorIII();
//        System.out.println(calculator.calculate("2*(5+5*2)/3+(6/2+8)"));
//        System.out.println(calculator.calculate("(2+6* 3+5- (3*14/7+2)*5)+3"));
        System.out.println(calculator.calculate("-1+4*3/3/3"));
    }
}

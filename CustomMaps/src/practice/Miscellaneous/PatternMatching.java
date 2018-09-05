package practice.Miscellaneous;

public class PatternMatching {

    static Boolean[][] matrix;

    public static boolean isMatchRecursive(String s, String p) {
        if(p.isEmpty()){
            return s.isEmpty();
        }else if(s.isEmpty()){
            if(p.length() >= 2 && p.charAt(1)=='*'){
                return isMatchRecursive(s, p.substring(2));
            }else{
                return false;
            }
        }

        boolean firstCharMatch = (s.charAt(0)==p.charAt(0)) || p.charAt(0)=='.';
        if(p.length()>1 && p.charAt(1)=='*'){
            return firstCharMatch && isMatchRecursive(s.substring(1), p) || isMatchRecursive(s, p.substring(2));
        }
        return firstCharMatch && isMatchRecursive(s.substring(1), p.substring(1));
    }

    public static boolean isMatch(String s, String p) {
        matrix = new Boolean[s.length()+1][p.length()+1];
        return _isMatch(0,0, s, p);
    }

    public static boolean _isMatch(int i, int j, String s, String p){
        if(matrix[i][j] != null){
            return matrix[i][j];
        }
        boolean reply = false;

        if(j==p.length()){
            reply = i==s.length();
        }else if(i==s.length()){
            if(p.length()-j>=2 && p.charAt(j+1)=='*'){
                reply = _isMatch(i,j+2, s, p);
            }else{
                reply = false;
            }
        }else{
            boolean firstCharMatch = (s.charAt(i)==p.charAt(j)) || p.charAt(j)=='.';
            if(p.length()-j>1 && p.charAt(j+1)=='*'){
                return firstCharMatch && _isMatch(i+1, j, s, p) || _isMatch(i, j+2, s, p);
            }
            return firstCharMatch && _isMatch(i+1, j+1, s, p);
        }
        matrix[i][j] = reply;
        return reply;
    }

    public static void main(String[] args){
//        System.out.println(isMatchRecursive("aa", "a"));
//        System.out.println(isMatchRecursive("ab", ".*"));
//        System.out.println(isMatchRecursive("aa", "a*"));
//        System.out.println(isMatchRecursive("a", "ab*"));
//        System.out.println(isMatchRecursive("aab", "c*a*b"));
//        System.out.println(isMatchRecursive("bbbba", ".*a*a"));
//        System.out.println(isMatchRecursive("mississippi", "mis*is*p*."));

        System.out.println(isMatch("aa", "a"));
        System.out.println(isMatch("ab", ".*"));
        System.out.println(isMatch("aa", "a*"));
        System.out.println(isMatch("a", "ab*"));
        System.out.println(isMatch("aab", "c*a*b"));
        System.out.println(isMatch("bbbba", ".*a*a"));
        System.out.println(isMatch("mississippi", "mis*is*p*."));

    }
}


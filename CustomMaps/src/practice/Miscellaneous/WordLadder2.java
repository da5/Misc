package practice.Miscellaneous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class WordLadder2 {
    static class Node{
        String word;
        Set<Node> children;
        int length;
        Set<String> path;
        public Node(String w){
            word = w;
            children = new HashSet<>();
        }
        public Node(Node n){
            this.word = n.word;
            this.children = n.children;
        }
    }

    Map<String, Node> nodeMap;
    Map<String, Boolean> visited;
    Map<String, Set<String>> predecessor;

    List<String> getPossibleConversions(String key){
        int n = key.length();
        List<String> list = new ArrayList<>();
        for(int i=0; i<n; i++){
            for(int j=0; j<26; j++){
                char c = (char)('a'+j);
                String conv = (i==0)? c + key.substring(i+1):
                        ((i==n-1)? key.substring(0, i)+c:
                                key.substring(0, i)+c+key.substring(i+1)
                        );
                list.add(conv);
            }
        }
        return list;
    }

    void linkEdges(String key){
        Node keyNode = nodeMap.get(key);
        for(String conv: getPossibleConversions(key)){
            if(!conv.equals(key) && nodeMap.containsKey(conv)){
                keyNode.children.add(nodeMap.get(conv));
            }
        }
    }

    boolean createGraph(String beginWord, String endWord, List<String> wordList){
        nodeMap = new HashMap<>();
        visited = new HashMap<>();
        for(String word: wordList){
            nodeMap.put(word, new Node(word));
            visited.put(word, false);
        }
        if(!nodeMap.containsKey(endWord)){
            return false;
        }
        nodeMap.put(beginWord, new Node(beginWord));

        for(String key: nodeMap.keySet()){
            linkEdges(key);
        }
        return true;
    }

    int findShortestConversionLength(String beginWord, String endWord){
        visited.put(beginWord, true);
        Queue<Node> queue = new LinkedList<>();
        Node node = nodeMap.get(beginWord);
        node.length = 1;
        queue.offer(nodeMap.get(beginWord));
        while(!queue.isEmpty()){
            node = queue.poll();
            if(node.word.equals(endWord)){
                return node.length;
            }
            for(Node child: node.children){
                if(!visited.get(child.word)){
                    visited.put(child.word, true);
                    child.length = node.length+1;
                    queue.offer(child);
                }
            }
        }
        return 0;
    }

    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> result = new LinkedList<>();
        if (!createGraph(beginWord, endWord, wordList)) {
            return result;
        }
        int length = findShortestConversionLength(beginWord, endWord);
        if (length == 0) {
            return result;
        }
        System.out.println(length);
        traverse(beginWord, endWord, length, result);
        return result;
    }

    void traverse(String beginWord, String endWord, int length, List<List<String>> result){
        predecessor = new HashMap<>();
        for(String key: visited.keySet()){
            visited.put(key, false);
            predecessor.put(key, new HashSet<>());
        }

        Queue<Node> queue = new LinkedList<>();
        Node node = nodeMap.get(beginWord);
        queue.offer(node);
        while(!queue.isEmpty()){
            node = queue.poll();
            visited.put(node.word, true);
            for(Node child: node.children){
                if(!visited.get(child.word)){
                    predecessor.get(child.word).add(node.word);
                    queue.offer(child);
                }
            }
        }
        List<String> path = new ArrayList<>();
        path.add(endWord);
        constructPaths(length, beginWord, endWord, path, result);
    }

    void constructPaths(int length, String start, String word, List<String> path, List<List<String>> result){
        if(start.equals(word)) {
            result.add(path);
            return;
        }
        if(path.size()<length){
            for(String parent: predecessor.get(word)){
                List<String> newPath = new ArrayList<>(path);
                newPath.add(0, parent);
                constructPaths(length, start, parent, newPath, result);
            }
        }

    }
}




class WordLadder2Driver{
    public static void main(String[] args){
        WordLadder2 wordLadder2 = new WordLadder2();
        String b = "cet";
        String e = "ism";
        String[] arr ={"kid","tag","pup","ail","tun","woo","erg","luz","brr","gay","sip","kay","per","val","mes","ohs","now","boa","cet","pal","bar","die","war","hay","eco","pub","lob","rue","fry","lit","rex","jan","cot","bid","ali","pay","col","gum","ger","row","won","dan","rum","fad","tut","sag","yip","sui","ark","has","zip","fez","own","ump","dis","ads","max","jaw","out","btu","ana","gap","cry","led","abe","box","ore","pig","fie","toy","fat","cal","lie","noh","sew","ono","tam","flu","mgm","ply","awe","pry","tit","tie","yet","too","tax","jim","san","pan","map","ski","ova","wed","non","wac","nut","why","bye","lye","oct","old","fin","feb","chi","sap","owl","log","tod","dot","bow","fob","for","joe","ivy","fan","age","fax","hip","jib","mel","hus","sob","ifs","tab","ara","dab","jag","jar","arm","lot","tom","sax","tex","yum","pei","wen","wry","ire","irk","far","mew","wit","doe","gas","rte","ian","pot","ask","wag","hag","amy","nag","ron","soy","gin","don","tug","fay","vic","boo","nam","ave","buy","sop","but","orb","fen","paw","his","sub","bob","yea","oft","inn","rod","yam","pew","web","hod","hun","gyp","wei","wis","rob","gad","pie","mon","dog","bib","rub","ere","dig","era","cat","fox","bee","mod","day","apr","vie","nev","jam","pam","new","aye","ani","and","ibm","yap","can","pyx","tar","kin","fog","hum","pip","cup","dye","lyx","jog","nun","par","wan","fey","bus","oak","bad","ats","set","qom","vat","eat","pus","rev","axe","ion","six","ila","lao","mom","mas","pro","few","opt","poe","art","ash","oar","cap","lop","may","shy","rid","bat","sum","rim","fee","bmw","sky","maj","hue","thy","ava","rap","den","fla","auk","cox","ibo","hey","saw","vim","sec","ltd","you","its","tat","dew","eva","tog","ram","let","see","zit","maw","nix","ate","gig","rep","owe","ind","hog","eve","sam","zoo","any","dow","cod","bed","vet","ham","sis","hex","via","fir","nod","mao","aug","mum","hoe","bah","hal","keg","hew","zed","tow","gog","ass","dem","who","bet","gos","son","ear","spy","kit","boy","due","sen","oaf","mix","hep","fur","ada","bin","nil","mia","ewe","hit","fix","sad","rib","eye","hop","haw","wax","mid","tad","ken","wad","rye","pap","bog","gut","ito","woe","our","ado","sin","mad","ray","hon","roy","dip","hen","iva","lug","asp","hui","yak","bay","poi","yep","bun","try","lad","elm","nat","wyo","gym","dug","toe","dee","wig","sly","rip","geo","cog","pas","zen","odd","nan","lay","pod","fit","hem","joy","bum","rio","yon","dec","leg","put","sue","dim","pet","yaw","nub","bit","bur","sid","sun","oil","red","doc","moe","caw","eel","dix","cub","end","gem","off","yew","hug","pop","tub","sgt","lid","pun","ton","sol","din","yup","jab","pea","bug","gag","mil","jig","hub","low","did","tin","get","gte","sox","lei","mig","fig","lon","use","ban","flo","nov","jut","bag","mir","sty","lap","two","ins","con","ant","net","tux","ode","stu","mug","cad","nap","gun","fop","tot","sow","sal","sic","ted","wot","del","imp","cob","way","ann","tan","mci","job","wet","ism","err","him","all","pad","hah","hie","aim","ike","jed","ego","mac","baa","min","com","ill","was","cab","ago","ina","big","ilk","gal","tap","duh","ola","ran","lab","top","gob","hot","ora","tia","kip","han","met","hut","she","sac","fed","goo","tee","ell","not","act","gil","rut","ala","ape","rig","cid","god","duo","lin","aid","gel","awl","lag","elf","liz","ref","aha","fib","oho","tho","her","nor","ace","adz","fun","ned","coo","win","tao","coy","van","man","pit","guy","foe","hid","mai","sup","jay","hob","mow","jot","are","pol","arc","lax","aft","alb","len","air","pug","pox","vow","got","meg","zoe","amp","ale","bud","gee","pin","dun","pat","ten","mob"};
//        String b = "hit";
//        String e = "cog";
//        String[] arr = {"hot","dot","dog","lot","log","cog"};
        List<List<String>> result = wordLadder2.findLadders(b, e, new ArrayList<>(Arrays.asList(arr)));
        for(List<String> list: result){
            for(String word: list){
                System.out.print(word + " ");
            }
            System.out.println("");
        }
    }
}


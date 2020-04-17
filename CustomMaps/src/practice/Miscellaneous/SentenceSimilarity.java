package practice.Miscellaneous;

import java.util.HashMap;
import java.util.Map;

public class SentenceSimilarity {
    static class UnionRank {

        Node[] nodes;

        public static class Node{
            public Node parent;
            public int rank;

            public Node(){ }
        }

        public UnionRank(int n){
            nodes = new Node[n];
            for(int i =0; i< n ; i++){
                Node node = new Node();
                node.rank = 1;
                node.parent = node;
                nodes[i] = node;
            }
        }

        public Node find(Node x){
            if(x.parent != x){
                x.parent = find(x.parent);
            }
            return x.parent;
        }

        public void union(Node x, Node y){
            Node parentX = find(x);
            Node parentY = find(y);
            if(parentX == parentY){
                return;
            }
            if(parentX.rank > parentY.rank){
                y.parent = parentX;
            }else{
                x.parent = parentY;
                if(parentX.rank == parentY.rank){
                    parentY.rank += 1;
                }

            }
        }
    }
    public static boolean areSentencesSimilar(String[] words1, String[] words2, String[][] pairs) {
        boolean result  = true;
        if(words1.length==words2.length){
            UnionRank unionRank = new UnionRank(2*pairs.length);
            int counter = 0;
            Map<String, Integer> wordMap = new HashMap<>();
            for(String[] pair: pairs){
                if(!wordMap.containsKey(pair[0])){
                    wordMap.put(pair[0], counter++);
                }
                if(!wordMap.containsKey(pair[1])){
                    wordMap.put(pair[1], counter++);
                }
                UnionRank.Node x = unionRank.nodes[wordMap.get(pair[0])];
                UnionRank.Node y = unionRank.nodes[wordMap.get(pair[1])];
                unionRank.union(x, y);
            }
            for(int i = 0; i<words1.length; i++){
                if(!words1[i].equals(words2[i])){
                    if (!wordMap.containsKey(words1[i]) || !wordMap.containsKey(words2[i])) {
                        result = false;
                        break;
                    }
                    UnionRank.Node x = unionRank.nodes[wordMap.get(words1[i])];
                    UnionRank.Node y = unionRank.nodes[wordMap.get(words2[i])];
                    if (x.parent != y.parent) {
                        result = false;
                        break;
                    }
                }
            }
        }else {
            result = false;
        }
        return result;
    }

    public static void main(String[] args){

        String[] words1 = {"this","summer","thomas","get","really","very","rich","and","have","any","actually","wonderful","and","good","truck","every","morning","he","drives","an","extraordinary","truck","around","the","nice","city","to","eat","some","extremely","extraordinary","food","as","his","meal","but","he","only","entertain","an","few","well","fruits","as","single","lunch","he","wants","to","eat","single","single","and","really","healthy","life"};
        String[] words2 = {"this","summer","thomas","get","very","extremely","rich","and","possess","the","actually","great","and","wonderful","vehicle","every","morning","he","drives","unique","extraordinary","automobile","around","unique","fine","city","to","drink","single","extremely","nice","meal","as","his","super","but","he","only","entertain","a","few","extraordinary","food","as","some","brunch","he","wants","to","take","any","some","and","really","healthy","life"};
        String[][] pairs = {{"good","nice"},{"good","excellent"},{"good","well"},{"good","great"},{"fine","nice"},{"fine","excellent"},{"fine","well"},{"fine","great"},{"wonderful","nice"},{"wonderful","excellent"},{"wonderful","well"},{"wonderful","great"},{"extraordinary","nice"},{"extraordinary","excellent"},{"extraordinary","well"},{"extraordinary","great"},{"one","a"},{"one","an"},{"one","unique"},{"one","any"},{"single","a"},{"single","an"},{"single","unique"},{"single","any"},{"the","a"},{"the","an"},{"the","unique"},{"the","any"},{"some","a"},{"some","an"},{"some","unique"},{"some","any"},{"car","vehicle"},{"car","automobile"},{"car","truck"},{"auto","vehicle"},{"auto","automobile"},{"auto","truck"},{"wagon","vehicle"},{"wagon","automobile"},{"wagon","truck"},{"have","take"},{"have","drink"},{"eat","take"},{"eat","drink"},{"entertain","take"},{"entertain","drink"},{"meal","lunch"},{"meal","dinner"},{"meal","breakfast"},{"meal","fruits"},{"super","lunch"},{"super","dinner"},{"super","breakfast"},{"super","fruits"},{"food","lunch"},{"food","dinner"},{"food","breakfast"},{"food","fruits"},{"brunch","lunch"},{"brunch","dinner"},{"brunch","breakfast"},{"brunch","fruits"},{"own","have"},{"own","possess"},{"keep","have"},{"keep","possess"},{"very","super"},{"very","actually"},{"really","super"},{"really","actually"},{"extremely","super"},{"extremely","actually"}};

        System.out.print(areSentencesSimilar(words1, words2, pairs));


    }
}

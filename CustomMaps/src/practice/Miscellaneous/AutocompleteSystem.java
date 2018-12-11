package practice.Miscellaneous;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class AutocompleteSystem {

    static class TrieEntry{
        int hits;
        String sentence;
        TrieEntry[] children;
        TrieEntry(){
            this.children = new TrieEntry[27];
        }
    }

    int getIdx(char c){
        if(c == ' '){
            return 26;
        }
        return (c-'a');
    }

    TrieEntry root;
    TrieEntry currentSearch;
    String currentSentence;

    TrieEntry traverse(String str, boolean construct, int hits){
        TrieEntry entry = (construct )? root: currentSearch;
        int idx = 0;
        while(idx < str.length() && entry!=null){
            TrieEntry child = entry.children[getIdx(str.charAt(idx))];
            if(construct){
                if(child == null) {
                    child = new TrieEntry();
                    entry.children[getIdx(str.charAt(idx))] = child;
                    if (idx == str.length() - 1) {
                        child.sentence = str;
                        child.hits = hits;
                        return child;
                    }
                }else if(idx == str.length()-1){
                    if(child.sentence == null){
                        child.sentence = str;
                        child.hits = hits;
                    }else{
                        child.hits += 1;
                    }
                }
            }
            entry = child;
            idx++;
        }
        return entry;
    }

    TrieEntry addToTrie(String sentence, int hits){
        return traverse(sentence, true, hits);
    }

    void traverseAndCollect(TrieEntry entry, Queue<TrieEntry> queue){
        if(entry==null){
            return;
        }
        if(entry.sentence != null){
            queue.offer(entry);
        }
        for(TrieEntry child: entry.children){
            if(child != null){
                traverseAndCollect(child, queue);
            }
        }
    }

    Queue<TrieEntry> getSentences(TrieEntry entry){
        Queue<TrieEntry> queue = new PriorityQueue<>( new Comparator<TrieEntry>() {
            @Override
            public int compare(TrieEntry o1, TrieEntry o2) {
                if(o1.hits!=o2.hits){
                    return o2.hits-o1.hits;
                }
                return o1.sentence.compareTo(o2.sentence);
            }
        });
        traverseAndCollect(entry, queue);
        return queue;
    }

    public AutocompleteSystem(String[] sentences, int[] times) {
        root = new TrieEntry();
        root.children = new TrieEntry[27];
        root.sentence = "";
        for(int i=0; i<sentences.length; i++){
            addToTrie(sentences[i], times[i]);
        }
        resetCurrent();
    }

    private void resetCurrent(){
        currentSearch = root;
        currentSentence = "";
    }

    public List<String> input(char c) {
        List<String> strings = new ArrayList<>();
        if(c == '#'){
            if(currentSearch!=null){
                if(currentSearch.sentence == null){
                    currentSearch.sentence = currentSentence;
                    currentSearch.hits = 1;
                }else{
                    currentSearch.hits += 1;
                }
            }else{
                addToTrie(currentSentence, 1);
            }
            resetCurrent();
        }else {
            currentSearch = traverse("" + c, false, 0);
            currentSentence += c;
            Queue<TrieEntry> queue = getSentences(currentSearch);
            int top = 3;
            while (!queue.isEmpty() && top>0) {
                strings.add(queue.poll().sentence);
                top--;
            }
        }

        return strings;
    }
}

class Driver{
    static class Interval {
      int start;
      int end;
      Interval() { start = 0; end = 0; }
      Interval(int s, int e) { start = s; end = e; }
  }

    public List<Interval> merge(List<Interval> intervals) {
        Interval[] result = new Interval[intervals.size()];
        Collections.sort(intervals, new Comparator<Interval>() {
            @Override
            public int compare(Interval o1, Interval o2) {
                return (o1.start == o2.start)?(o1.end-o2.end):(o1.start-o2.start);
            }
        });
        int i = 0;
        for(Interval interval: intervals){
            if(i == 0){
                result[i++] = interval;
            }else{
                if(result[i-1].end > interval.start){
                    result[i-1].end = Math.max(result[i-1].end, interval.end);
                }else{
                    result[i++] = interval;
                }

            }
        }
        intervals.clear();
        for(int j =0; j<i; j++){
            intervals.add(result[j]);
        }
        return intervals;
    }

    public static void main(String[] args){
//        String[] sentences = {"i love you","island","iroman","i love leetcode"};
//        int[] times = {5,3,2,2};
//        AutocompleteSystem system = new AutocompleteSystem(sentences, times);
//        List<String> strings = system.input('i');
//        strings = system.input(' ');
//        strings = system.input('a');
//        strings = system.input('#');
//        strings = system.input('i');

        String[] sentences = {"abc","abbc","a"};
        int[] times = {3,3,3};
        AutocompleteSystem system = new AutocompleteSystem(sentences, times);
        List<String> strings = system.input('b');
        strings = system.input('a');
        strings = system.input('#');
        strings = system.input('b');
        strings = system.input('a');
        strings = system.input('#');

        strings = system.input('a');
        strings = system.input('b');
        strings = system.input('c');
        strings = system.input('#');

        strings = system.input('a');
        strings = system.input('b');
        strings = system.input('c');
        strings = system.input('#');


    }
//["AutocompleteSystem","input","input","input","input","input","input","input","input","input","input","input","input","input","input"]
//        [[["abc","abbc","a"],[3,3,3]],["b"],["c"],["#"],["b"],["c"],["#"],["a"],["b"],["c"],["#"],["a"],["b"],["c"],["#"]]
//    Output: [null,[],[],[],["bc"],["bc"],[],["abbc","abc"],["abbc","abc"],["abc"],[],["abc","abbc"],["abc","abbc"],["abc"],[]]
//    Expected: [null,[],[],[],["bc"],["bc"],[],["a","abbc","abc"],["abbc","abc"],["abc"],[],["abc","a","abbc"],["abc","abbc"],["abc"],[]]
}

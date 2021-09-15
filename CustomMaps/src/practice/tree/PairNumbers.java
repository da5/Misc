package practice.tree;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PairNumbers {

    public List<List<Integer>> findPairs(TreePointer leftIterator, TreePointer rightIterator, int k) {
        List<List<Integer>> pairs = new ArrayList<List<Integer>>();
        while (!leftIterator.isEmpty() && !rightIterator.isEmpty()
                && leftIterator.peek().getValue() < rightIterator.peek().getValue()) {
            if (leftIterator.peek().getValue() + rightIterator.peek().getValue() == k) {
                pairs.add(Arrays.asList(leftIterator.peek().getValue(), rightIterator.peek().getValue()));
                leftIterator.iterate();
                rightIterator.iterate();
            } else if (leftIterator.peek().getValue() + rightIterator.peek().getValue() < k) {
                leftIterator.iterate();
            } else {
                rightIterator.iterate();
            }
        }
        return pairs;
    }

    public List<List<Integer>> findPairs(Node root, int k) {
        return findPairs(new LeftPointer(root), new RightPointer(root), k);
    }

}

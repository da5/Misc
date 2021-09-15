package practice.tree;

import java.util.Stack;

/*
eranti.raj@walmart.com

 */
public abstract class TreePointer {
    protected Stack<Node> stack;

    public TreePointer() {
        this.stack = new Stack<>();
    }

    public abstract void iterate();

    public Node peek() {
        return stack.peek();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }
}

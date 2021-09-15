package practice.tree;

public class LeftPointer extends TreePointer {

    public LeftPointer(Node root) {
        super();
        while (root != null) {
            stack.push(root);
            root = root.getLeft();
        }
    }

    public void iterate() {
        Node node = stack.peek();
        if (node.getRight() != null) {
            stack.pop();
            Node child = node.getRight();
            while (child != null) {
                stack.push(child);
                child = child.getLeft();
            }
        } else {
            stack.pop();
        }
    }
}

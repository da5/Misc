package practice.tree;

public class RightPointer extends TreePointer {
    public RightPointer(Node root) {
        super();
        while (root != null) {
            stack.push(root);
            root = root.getRight();
        }
    }

    public void iterate() {
        Node node = stack.peek();
        if (node.getLeft() != null) {
            stack.pop();
            Node child = node.getLeft();
            while (child != null) {
                stack.push(child);
                child = child.getRight();
            }
        } else {
            stack.pop();
        }
    }

}

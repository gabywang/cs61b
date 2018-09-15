import java.util.List;

/**
 * Implementation of a BST based String Set.
 * @author Xiaowen Wang
 */
public class BSTStringSet implements StringSet {
    /** Creates a new empty set. */
    public BSTStringSet() {
        root = null;
    }

    @Override
    public void put(String s) {
        if (root == null){
            root = new Node(s);
        } else if (root.left.s != s && root.left == null){
            root.left = new Node(s);
        } else if (root.right.s != s && root.right == null) {
            root.right = new Node(s);
        }
    }

    @Override
    public boolean contains(String s) {
        if (root.s == s){
            return true;
        } else if (root.left.s == s|| root.right.s == s ){
            return true;
        }
        return false;
    }

    @Override
    public List<String> asList() {
        return null; // FIXME
    }

    /** Represents a single Node of the tree. */
    private static class Node {
        /** String stored in this Node. */
        private String s;
        /** Left child of this Node. */
        private Node left;
        /** Right child of this Node. */
        private Node right;

        /** Creates a Node containing SP. */
        public Node(String sp) {
            s = sp;
        }
    }

    /** Root node of the tree. */
    private Node root;
}

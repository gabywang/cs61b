import java.util.*;
import java.lang.*;

public class BST {
    BSTNode root;

    public BST(LinkedList list) {
        root = linkedListToTree(list.iterator(), list.size());
    }

    /**
     * Provide a descriptive comment for this method here
     */
    private BSTNode linkedListToTree (Iterator iter, int n) {
        if (n == 0) {
            return null;
        }
        else if (n == 1) {
            return new BSTNode(iter.next());
        }
        return new BSTNode(linkedListToTree(iter, n/2), iter.next(), linkedListToTree(iter, n - n / 2 - 1));
    }

    /**
     * Prints the tree to the console.
     */
    private void print() {
        print(root, 0);
    }

    private void print(BSTNode node, int d) {
        if (node == null) {
            return;
        }
        for (int i = 0; i < d; i++) {
            System.out.print("  ");
        }
        System.out.println(node.item);
        print(node.left, d + 1);
        print(node.right, d + 1);
    }

    /**
     * Node for BST.
     */
    static class BSTNode {

        /** Item. */
        Object item;

        /** Left child. */
        BSTNode left;

        /** Right child. */
        BSTNode right;

        public BSTNode () {

        }

        public BSTNode (Object obj) {
            this.item = obj;
        }

        public BSTNode(BSTNode left, Object obj, BSTNode right) {
            this.left = left;
            this.item = obj;
            this.right = right;
        }
    }

    public static void main(String[] args) {
        LinkedList list = new LinkedList();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);
        list.add(10);
        list.add(11);
        list.add(12);
        list.add(13);
        BST tree= new BST(list);
        tree.print();
    }
}

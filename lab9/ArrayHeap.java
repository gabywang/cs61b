import java.util.ArrayList;

/** A Generic heap class. Unlike Java's priority queue, this heap doesn't just
  * store Comparable objects. Instead, it can store any type of object
  * (represented by type T) and an associated priority value.
  * @author CS 61BL Staff*/
public class ArrayHeap<T> {

    /* DO NOT CHANGE THESE METHODS. */

    /* An ArrayList that stores the nodes in this binary heap. */
    private ArrayList<Node> contents;

    /* A constructor that initializes an empty ArrayHeap. */
    public ArrayHeap() {
        contents = new ArrayList<>();
        contents.add(null);
    }

    /* Returns the number of elments in the priority queue. */
    public int size() {
        return contents.size() - 1;
    }

    /* Returns the node at index INDEX. */
    private Node getNode(int index) {
        if (index >= contents.size()) {
            return null;
        } else {
            return contents.get(index);
        }
    }

    /* Sets the node at INDEX to N */
    private void setNode(int index, Node n) {
        // In the case that the ArrayList is not big enough
        // add null elements until it is the right size
        while (index + 1 > contents.size()) {
            contents.add(null);
        }
        contents.set(index, n);
    }

    /* Returns and removes the node located at INDEX. */
    private Node removeNode(int index) {
        if (index >= contents.size()) {
            return null;
        } else {
            return contents.remove(index);
        }
    }

    /* Swap the nodes at the two indices. */
    private void swap(int index1, int index2) {
        Node node1 = getNode(index1);
        Node node2 = getNode(index2);
        this.contents.set(index1, node2);
        this.contents.set(index2, node1);
    }

    /* Prints out the heap sideways. Use for debugging. */
    @Override
    public String toString() {
        return toStringHelper(1, "");
    }

    /* Recursive helper method for toString. */
    private String toStringHelper(int index, String soFar) {
        if (getNode(index) == null) {
            return "";
        } else {
            String toReturn = "";
            int rightChild = getRightOf(index);
            toReturn += toStringHelper(rightChild, "        " + soFar);
            if (getNode(rightChild) != null) {
                toReturn += soFar + "    /";
            }
            toReturn += "\n" + soFar + getNode(index) + "\n";
            int leftChild = getLeftOf(index);
            if (getNode(leftChild) != null) {
                toReturn += soFar + "    \\";
            }
            toReturn += toStringHelper(leftChild, "        " + soFar);
            return toReturn;
        }
    }

    /* A Node class that stores items and their associated priorities. */
    public class Node {
        private T item;
        private double priority;

        private Node(T item, double priority) {
            this.item = item;
            this.priority = priority;
        }

        public T item() {
            return this.item;
        }

        public double priority() {
            return this.priority;
        }

        public void setPriority(double priority) {
            this.priority = priority;
        }

        @Override
        public String toString() {
            return this.item.toString() + ", " + this.priority;
        }
    }



    /* FILL IN THE METHODS BELOW. */

    /* Returns the index of the node to the left of the node at i. */
    private int getLeftOf(int i) {
        return i * 2;
    }

    /* Returns the index of the node to the right of the node at i. */
    private int getRightOf(int i) {
        return i * 2 + 1;
    }

    /* Returns the index of the node that is the parent of the node at i. */
    private int getParentOf(int i) {
        return i / 2;
    }

    /* Adds the given node as a left child of the node at the given index. */
    private void setLeft(int index, Node n) {
        setNode(getLeftOf(index), n);
    }

    /* Adds the given node as the right child of the node at the given index. */
    private void setRight(int index, Node n) {
        setNode(getRightOf(index), n);
    }

    /** Returns the index of the node with smaller priority. Precondition: not
      * both nodes are null. */
    private int min(int index1, int index2) {
        if (contents.get(index1) != null && contents.get(index2) != null) {
            double prior1 = contents.get(index1).priority();
            double prior2 = contents.get(index2).priority();
            if (prior1 < prior2) {
                return index1;
            } else {
                return index2;
            }
        }
        return 0;
    }

    /* Returns the Node with the smallest priority value, but does not remove it
     * from the heap. */
    public Node peek() {
        double smallest = contents.get(1).priority();
        Node small = new Node(contents.get(1).item(), smallest);
        for (int i = 1; i < contents.size(); i++) {
            if (contents.get(i) != null) {
                if (smallest > contents.get(i).priority()) {
                    smallest = contents.get(i).priority();
                    small.item = contents.get(i).item();
                    small.priority = contents.get(i).priority();
                }
            }
        }
        return small;
    }

    /* Bubbles up the node currently at the given index. */
    private void bubbleUp(int index) {
        if (contents.get(index) != null) {
            Node check = new Node(contents.get(index).item(), contents.get(index).priority());
            if (contents.get(getParentOf(index)) != null) {
                if (contents.get(getParentOf(index)).priority() < check.priority()) { //NULL
                    swap(index, getParentOf(index));
                    bubbleUp(getParentOf(index));
                }
            }
        } else {
            if (getParentOf(index) > 0)
                bubbleUp(getParentOf(index));
        }
    }

    /* Bubbles down the node currently at the given index. */
    private void bubbleDown(int index) {
        if (contents.size() > index * 2) {
            Node check = new Node(contents.get(index).item(), contents.get(index).priority());
            if (contents.get(getLeftOf(index)) != null) {
                if (contents.get(getLeftOf(index)).priority() > check.priority()) {
                    if (contents.size() > ((index * 2) + 1) && contents.get(getRightOf(index)) != null) {
                        if (contents.get(getRightOf(index)).priority() > contents.get(getLeftOf(index)).priority()) {
                            swap(index, getRightOf(index));
                            bubbleUp(getRightOf(index));
                        }
                    }
                    swap(index, getLeftOf(index));
                    bubbleUp(getLeftOf(index));
                }
            } else if (contents.size() > ((index * 2) + 1) && contents.get(getRightOf(index)) != null) {
                if (contents.get(getRightOf(index)).priority() > check.priority()) {
                    swap(index, getRightOf(index));
                    bubbleUp(getRightOf(index));
                }
            } else {
                return;
            }
        }
    }

    /* Inserts an item with the given priority value. Same as enqueue, or offer. */
    public void insert(T item, double priority) {
        Node insert = new Node(item, priority);
        int index = 1;
        for (int i = 1; i < contents.size(); i++) {
            if (contents.get(i) == null) {
                index = i;
            }
        }
        setNode(index, insert);
        bubbleUp(index);
    }

    /* Returns the element with the smallest priority value, and removes it from
     * the heap. Same as dequeue, or poll. */
    public T removeMin() {
        /*T remove = peek();
        int index = 1;
        for (int i = 1; i < contents.size(); i++) {
            if (contents.get(i) != null) {
                if (contents.get(i).item().equals(remove.item())) {
                    index = i;
                }
            }
        }
        contents.remove(index);
        bubbleUp(index);
        return remove;*/
        return null;
    }

    /* Changes the node in this heap with the given item to have the given
     * priority. You can assume the heap will not have two nodes with the same
     * item. Check for item equality with .equals(), not == */
    public void changePriority(T item, double priority) {
        for (int i = 1 ; i < contents.size(); i++) {
            if (contents.get(i) != null) {
                if (contents.get(i).item().equals(item)) {
                    if (priority > contents.get(i).priority()) {
                        contents.get(i).priority = priority;
                        bubbleUp(i);
                    } else {
                        contents.get(i).priority = priority;
                        bubbleDown(i);
                    }
                }
            }
        }
    }

}

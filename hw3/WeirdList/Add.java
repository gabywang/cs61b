/* Add */

public class Add implements IntUnaryFunction {
    public int toAdd;
    public Add(int x) {
        toAdd = x;
    }
    public int apply(int head) {
        head += toAdd;
        return head;
    }
}
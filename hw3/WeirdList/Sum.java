/* Sum */

public class Sum implements IntUnaryFunction {
    public int total;
    public int apply(int y) {
        total += y;
        return y;
    }
}
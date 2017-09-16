/**
 * Created by joshuazeitsoff on 9/2/17.
 */

public class BuggyIntDList extends IntDList {

    public BuggyIntDList(Integer... values) {
        super(values);
    }

    /** Removes value #I in this list, where item 0 is the first, 1 is the
     *  second, ...., -1 is the last, -2 the second to last.... */
    public void remove (int i) {
        DNode p;
        if (i >= 0) {
            p = _front;
            while (i > 0) {
                i -= 1;
                p = p._next;
            }
        } else {
            p = _back;
            while (i < -1) {
                i += 1;
                p = p._prev;
            }
        }
        if (p._next != null) {
            p._next._prev = p._prev;
        } else {
            _back = p._prev;
        }
        p._prev._next = p._next;
    }
}

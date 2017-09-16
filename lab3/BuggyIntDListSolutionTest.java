import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by joshuazeitsoff on 9/2/17.
 */

public class BuggyIntDListSolutionTest {

    /* Tests remove. */
    @Test
    public void testRemove() {
        BuggyIntDListSolution d = new BuggyIntDListSolution(1, 2, 3, 2, 1);

        d.remove(0);
        assertEquals(".size() after remove(0)",4, d.size());
        assertEquals(".get(0)", 2, d.get(0));
        assertEquals(".get(1)", 3, d.get(1));
        assertEquals(".get(2)", 2, d.get(2));
        assertEquals(".get(3)", 1, d.get(3));

        d.remove(3);
        assertEquals(".size() after remove(3)",3, d.size());
        assertEquals(".get(0)", 2, d.get(0));
        assertEquals(".get(1)", 3, d.get(1));
        assertEquals(".get(2)", 2, d.get(2));

        d.remove(-3);
        assertEquals(".size() after remove(-3)",2, d.size());
        assertEquals(".get(0)", 3, d.get(0));
        assertEquals(".get(1)", 2, d.get(1));

        d.remove(-1);
        assertEquals(".size() after remove(-1)",1, d.size());
        assertEquals(".get(0)", 3, d.get(0));
    }
}

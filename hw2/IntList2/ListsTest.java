import org.junit.Test;
import static org.junit.Assert.*;

/** FIXME
 *
 *  @author Xiaowen Wang
 */

public class ListsTest {
    /** Run all the tests for Lists.java.
     */

    @Test
    public void naturalRunstest(){
        assertEquals (IntList2.list(IntList.list(1, 2, 3), IntList.list(3, 4, 5)), Lists.naturalRuns(IntList.list(1,2,3,3,4,5)));
        assertEquals(IntList2.list(IntList.list(3, 5, 7),IntList.list(7, 9), IntList.list(4, 10, 14, 15)), Lists.naturalRuns (IntList.list(3,5, 7, 7, 9, 4, 10, 14, 15)));
    }
    // It might initially seem daunting to try to SetDemo up
    // Intlist2 expected.
    //
    // There is an easy way to get the IntList2 that you want in just
    // few lines of code! Make note of the IntList2.list method that
    // takes as input a 2D array.

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ListsTest.class));
    }
}

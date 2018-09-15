import org.junit.Test;
import static org.junit.Assert.*;

/** FIXME
 *  @author Xiaowen Wang
 */

public class ArraysTest {
    /** Run the tests for arrays.java.
     */
    int[] tested_a= {1,2,3,3,4,5};
    int[] tested_b= {4, 5, 6, 7, 10, 21, 24, 30};

    int[] test_a= {1, 2, 3};
    int[] test_b= {3, 4, 5};
    int[] test_c= {1, 2, 5};
    int[] test_d= { 4, 5 ,6, 24, 30};

    /*int[][] test_e= {{1, 2, 3} , {3, 4, 5}, {1, 2, 5} };
    int[] tested_e={1, 2, 3, 3, 4, 5, 1, 2, 5};
    int[][] test_f= {{1, 2, 3} , {3, 4, 5}};
    int[] tested_f={1, 2, 3, 3, 4, 5, 1, 2, 5};*/

    @Test
    public void catenateTest() {
        assertTrue (Utils.equals( tested_a , Arrays.catenate(test_a , test_b)));
    }

    @Test
    public void testRemove() {
        assertTrue (Utils.equals(test_c , Arrays.remove(tested_a, 2, 3)));
    }

    /*@Test
    public void testnaturalRuns() {
        assertTrue (Utils.equals (test_e , Arrays.naturalRuns(tested_e)));
        assertTrue (Utils.equals (test_f , Arrays.naturalRuns(tested_f)));
    }*/

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ArraysTest.class));
    }
}

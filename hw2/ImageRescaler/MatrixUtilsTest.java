import org.junit.Test;
import static org.junit.Assert.*;

/** FIXME
 *  @author Xiaowen Wang
 */

public class MatrixUtilsTest {
    /** FIXME
     */
    double[][] testA = new double[][] {{10, 10, 10, 10},
            {10, 7,  3,   10},
            {10, 3,  10, 10},
            {10, 2,  3,   10},
            {10, 7,  10,  10}};

    double[][] resultA = new double[][] {{10, 10, 10, 10},
            {20, 17, 13, 20},
            {27, 16, 23, 23},
            {26, 18, 19, 33},
            {28, 25, 28, 29}};

    double[][] resultB= new double [][] {{10, 20, 27, 26},
            {10, 17, 16, 26},
            {10, 13, 22, 25},
            {10, 12, 15, 25},
            {10, 17, 22, 25}};
    @Test
    public void testaccumulateVertical() {
        assertArrayEquals (resultA, MatrixUtils.accumulateVertical(testA));
    }

    @Test
    public void testAccumulate() {
        assertArrayEquals (resultB, MatrixUtils.accumulate(testA, MatrixUtils.Orientation.HORIZONTAL));
    }


    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(MatrixUtilsTest.class));
    }
}

package timing;

import org.junit.Test;

import static org.junit.Assert.*;

public class MySortTester {
    public static int[][] getTwoArrays(int arrayLength) {
        int[] array1 = Sorter.getRandomArray(arrayLength);
        int[] array2 = new int[arrayLength];
        System.arraycopy(array1, 0, array2, 0, arrayLength);
        int[][] both = {array1, array2};
        return both;
    }

    @Test
    public void testBubbleSort() {
        for(int i = 0; i < 10; i++) {
            int[][] arrays = getTwoArrays(10);
            (new JavaSorter()).sort(arrays[0]);
            (new BubbleSorter()).sort(arrays[1]);
            assertArrayEquals(arrays[0], arrays[1]);
        }
    }

    @Test
    public void testWipingBubbleSort() {
        for(int i = 0; i < 10; i++) {
            int[][] arrays = getTwoArrays(10);
            (new JavaSorter()).sort(arrays[0]);
            (new WipingBubbleSorter()).sort(arrays[1]);
            assertArrayEquals(arrays[0], arrays[1]);
        }
    }

    @Test
    public void testInsertionSort() {
        for(int i = 0; i < 10; i++) {
            int[][] arrays = getTwoArrays(10);
            (new JavaSorter()).sort(arrays[0]);
            (new InsertionSorter()).sort(arrays[1]);
            assertArrayEquals(arrays[0], arrays[1]);
        }
    }

}

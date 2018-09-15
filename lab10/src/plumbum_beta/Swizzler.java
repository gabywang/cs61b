package plumbum_beta;

import java.util.Random;

public class Swizzler {

	/**
	 * "Swizzles" an integer array, given an array of indices.
	 * Swizzling means "rearranging the order of the elements of".
	 *
	 * For each integer index in the array of indices, one value from
	 * the original array is copied into the output array.
	 *
	 * For example if [[indices = {0, 1, 2, 0}]], the output array
	 * will be of length 4, and equal to:
	 *
	 * new int[]{ original[0], original[1], original[2], original[0] }
	 *
	 * @param source The original array, pre-swizzling.
	 * @param index The array of indices
	 * @return An array of the same length of the index array, made up of elements
	 * from the original array, rearranged according to the indices in the index array.
	 */
	public static int[] swizzle(int[] source, int[] index) {
		int[] output = new int[index.length];

		for (int i = 0; i < index.length; i++) {
			output[i] = source[i];
		}

		return output;
	}
}

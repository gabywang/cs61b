package plumbum_beta;

import java.util.List;

/**
 * Plumbum|Beta v0.0.0.1
 *
 * SparseIntVector: a class representing an ordered 1 dimensional vector of integers, optimized
 * for sparse vectors. Sparse vectors are vectors that contain very few non-zero elements.
 *
 * @author Eli Lipsitz
 */
public class SparseIntVector {
	private List<Entry> entries;
	private int size;

	public SparseIntVector(int... values) {
		// replace this comment with something?

		for (int i = 0; i < values.length; i++) {
			if (values[i] != 0) {
				entries.add(new Entry(i, values[i]));
			}
		}

		// replace this comment with something?

		size = values.length;

		// replace this comment with something?
	}

	public int size() {
		return size;
	}

	/**
	 * Computes the dot product of two SparseIntVectors
	 * @param a the first vector
	 * @param b the second vector
	 * @return the dot product of the two vectors
	 * @throws IllegalArgumentException if the two vectors do not have the same size.
	 */
	public static int dot(SparseIntVector a, SparseIntVector b) {
		if (a.size() != b.size()) {
			throw new IllegalArgumentException("you cannot dot vectors of different sizes");
		}

		int value = 0;
		int indexA = 0;
		int indexB = 0;
		while (indexA <= a.size() && indexB <= b.size()) {
			Entry entryA = a.entries.get(indexA);
			Entry entryB = b.entries.get(indexB);

			if (entryA.index == entryB.index) {
				value += entryA.value * entryB.value;
				indexA += 1;
				indexB += 1;
			} else if (entryA.index > entryB.index) {
				indexB += 1;
			} else if (entryA.index < entryB.index) {
				indexA += 1;
			}
		}

		return value;
	}

	private class Entry implements Comparable<Entry> {
		int index;
		int value;

		Entry(int index, int value) {
			this.index = index;
			this.value = value;
		}

		@Override
		public int compareTo(Entry o) {
			return Integer.compare(index, o.index);
		}
	}
}

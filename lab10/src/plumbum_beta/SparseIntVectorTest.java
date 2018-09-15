package plumbum_beta;

import org.junit.Test;
import static org.junit.Assert.*;

public class SparseIntVectorTest {
	@Test
	public void testDot() {
		SparseIntVector a = new SparseIntVector(0, 2, 0, 1, 0, 0, -10, 6, 0, 0, 0, 40);
		SparseIntVector b = new SparseIntVector(0, 0, 0, 4, 0, 3, 18, 0, 0, 0, 9, 10);
		SparseIntVector c = new SparseIntVector(0, 0, 43, 0, 0, 23, 0, 0, 0, -14, 0, 0);

		assertEquals(224, SparseIntVector.dot(a, b));
		assertEquals(69, SparseIntVector.dot(b, c));
		assertEquals(0, SparseIntVector.dot(a, c));

		assertTrue(SparseIntVector.dot(a, b) == SparseIntVector.dot(b, a));
	}
}

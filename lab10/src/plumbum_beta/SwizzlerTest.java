package plumbum_beta;

import org.junit.Test;
import static org.junit.Assert.*;

public class SwizzlerTest {

	@Test
	public void testSwizzler() {
		int[] src = {2, 4, 6, 8, 10, 12, 14};

		assertArrayEquals(new int[] {2, 4, 6, 2}, Swizzler.swizzle(src, new int[] {0, 1, 2, 0}));

		assertArrayEquals(new int[] {10, 8, 6, 4}, Swizzler.swizzle(src, new int[] {4, 3, 2, 1}));

		assertArrayEquals(new int[] {14, 14, 14, 14, 14, 14}, Swizzler.swizzle(src, new int[] {6, 6, 6, 6, 6, 6}));

		assertArrayEquals(new int[] {}, Swizzler.swizzle(src, new int[] {}));
	}
}

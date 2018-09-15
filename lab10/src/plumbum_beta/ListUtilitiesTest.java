package plumbum_beta;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListUtilitiesTest {
	@Test
	public void testFilter() {
		List<String> source = Arrays.asList("help!", "I'M", "stuck", "in", "A", "software", "factory!!!");

		assertEquals(Arrays.asList("I'M", "in", "A"), ListUtilities.filter(copy(source), e -> e.length() < 4));
		assertEquals(Arrays.asList("help!", "factory!!!"), ListUtilities.filter(copy(source), e -> e.indexOf('!') >= 0));
		assertEquals(Arrays.asList("help!", "stuck", "in", "software", "factory!!!"), ListUtilities.filter(copy(source), e -> e.equals(e.toLowerCase())));
	}

	private <T> List<T> copy(List<T> a) {
		ArrayList<T> l = new ArrayList<>();
		l.addAll(a);
		return l;
	}
}

package plumbum_beta;

import java.util.List;
import java.util.function.Predicate;

public class ListUtilities {

	/**
	 * Filters the given list according to the predicate function.
	 * Returns the input list for easy chaining.
	 *
	 * @param input the list to filter
	 * @param predicate a predicate function, returns 'true' if an element should be kept
	 */
	public static <T> List<T> filter(List<T> input, Predicate<T> predicate) {
		for (int i = 0; i < input.size(); i++) {
			if (!predicate.test(input.get(i))) {
				input.remove(i);
			}
		}

		return input;
	}
}

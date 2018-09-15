import utils.Filter;
import utils.Predicate;
import java.util.List;
import java.util.Arrays;
import java.util.Iterator;

/** Exercises for Lab 6.
 *  @author You.
 */
public class FilterClient {

    /** A couple of test cases. */
    private static final Integer[][] TESTS = {
        { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 },
        { 1, 2, 3, 0, 7, 8, 6, 9, 10, 1 }
    };

    /** Print out the items returned by L. */
    static void printAll(Filter<Integer> L) {
        System.out.print("[");
        String sep;
        sep = "";
        for (Integer i : L) {
            System.out.print(sep + i);
            sep = ", ";
        }
        System.out.println("]");
    }

    /** A sample space where you can experiment with your filter.
      * ARGS is unused. */
    public static void main(String[] args) {
        for (Integer[] data: TESTS) {
            List<Integer> L = Arrays.asList(data);
            System.out.println(L);
            Filter<Integer> f1 = new TrivialFilter<Integer>(L.iterator());
            printAll(f1);
            //printAll(everyFourth(L.iterator()));
            //printAll(everyEven(L.iterator()));
        }
    }

    /* Extra Challenges that you should complete without creating
       any new Filter implementations (i.e. you can create them
       using Trivial, Alternating, Monotonic, and/or PredicateFilter)
       1. Create a filter everyFourth that prints very fourth
       item.
       2. Create a filter that prints only even valued items. You
       may find the Even class provided below to be helpful. */

    /** Returns a filter that delivers every fourth item of INPUT,
     *  starting with the first.  You should not need to define a new
     *  class. */
    static Filter<Integer> everyFourth(Iterator<Integer> input) {
        Filter<Integer> evother = new AlternatingFilter<Integer>(input);
        Filter<Integer> evfourth = new AlternatingFilter<Integer>(evother);

        return evfourth;
    }

    /** Returns a filter that delivers every even valued integer of
     *  INPUT. You should not need to define a new class. */
    static Filter<Integer> evenNumberFilter(Iterator<Integer> input) {

        return null; // FIXME
    }

    /** A class whose instances represent the test for eveness. */
    static class Even implements Predicate<Integer> {
        @Override
        public boolean test(Integer x) {
            return null; // FIXME
        }
    }
}

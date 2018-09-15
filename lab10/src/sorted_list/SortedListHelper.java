package sorted_list;

import java.util.List;

/**
 * SortedList(tm) Utilities
 *
 * COPYRIGHT 2003 FLIK ENTERPRISES CORP.
 * ALL RIGHTS RESERVED
 *
 * PROPOSITION 65 WARNING:
 * WARNING: This Java file MAY CONTAIN chemicals known to the State of California to cause...
 */
public class SortedListHelper {
    /**
     * Inserts `item` into the sorted list `list`, maintaining its sorted...ness.
     */
    public static <T extends Comparable<T>> void insertIntoSortedList(List<T> list, T item) {
        boolean inserted = false;

        for (int i = 0; i < list.size(); i++) {
            if (item.compareTo(list.get(i)) < 0) {
                list.add(i, item);
                inserted = true;
            }
        }

        if (!inserted) {
            list.add(item);
        }
    }

    /**
     * Checks if the provided list is sorted.
     *
     * @return true, iff the list is sorted from smallest to largest.
     */
    public static <T extends Comparable<T>> boolean isListSorted(List<T> list) {
        T lastItem = null;
        for (T item : list) {
            if (lastItem != null && lastItem.compareTo(item) > 0) {
                return false;
            }
            lastItem = item;
        }
        return true;
    }
}

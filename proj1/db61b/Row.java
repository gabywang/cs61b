package db61b;

import java.util.Arrays;
import java.util.List;

import static db61b.Utils.*;

/** A row of a database.
 *  @author Xiaowen Wang
 */

class Row {
    /** A Row whose column values are DATA. The array DATA must not be altered
     *  subsequently. */
    Row(String[] data) {
        _data = data;
    }

    /** Given M COLUMNS that were created from a sequence of Tables
     *  [t0,...,tn] as well as ROWS [r0,...,rn] that were drawn from those
     *  same tables [t0,...,tn], constructs a new Row containing M values,
     *  where the ith value of this new Row is taken from the location given
     *  by the ith COLUMN (for each i from 0 to M-1).*/

    Row(List<Column> columns, Row... rows) {
        _data = new String[columns.size()];
        for (int i = 0; i < columns.size(); i++) {
            _data[i] = columns.get(i).getFrom(rows);
        }
    }

    /** Return the length of each row. */
    int size() {
        return _data.length;
    }

    /** Return the value of my Kth column.  Requires that 0 <= K < size(). */
    String get(int k) {
        if (k < size() && k >= 0) {
            return _data[k];
        } else {
            throw error("wrong column number");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Row) {
            Row obj1 = (Row) obj;
            String [] objdata = obj1._data;
            return Arrays.equals(_data, objdata);
        } else {
            throw error("wrong obj input");
        }
    }

    /** NOTE: Whenever you override the .equals() method for a class,
     *  also override hashCode so as to insure that if two objects
     *  are supposed to be equal, they also return the same .hashCode() value.*/

    @Override
    public int hashCode() {
        return Arrays.hashCode(_data);
    }

    /** Contents of this row. */
    private String[] _data;
}
